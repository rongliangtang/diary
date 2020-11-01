package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.aware.DiscoverySession;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    private String TAG = "WriteActivity";
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        //获取到intent传过来的数据
        Intent intent = getIntent();
        boolean isNew = intent.getBooleanExtra("isNew",false);

        //获取sqlite实例
        dbHelper = new MyDatabaseHelper(this, "Diarys1.db", null, 1);

        //根据isNew的值来判断是否为新建日记
        if (isNew){
            Log.d(TAG, "isNew is true");

            //获取当前的日期和星期几
            Calendar calendar = Calendar.getInstance(); // get current instance of the calendar
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            //System.out.println(formatter.format(calendar.getTime()) + "-" + getWeek(week));


            //新建日记的日期为当前日期
            TextView textView_time = findViewById(R.id.time);
            textView_time.setText(formatter.format(calendar.getTime()) + " " + getWeek(week));

        }else {
            Log.d(TAG, "isNew is false");

            Integer diaryId = intent.getIntExtra("diaryId",0);
            Log.d(TAG, "diaryId is " + diaryId);
            //根据diaryId显示日记内容
            //编辑日记的日期为创建时候的日期
            EditText editText_title = findViewById(R.id.title);
            TextView textView_time = findViewById(R.id.time);
            EditText editText_content = findViewById(R.id.content);

            //此处根据diaryId从数据库中取出日记内容
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor=db.rawQuery("select * from Diarys where diaryId = ?",new String[]{diaryId.toString()});
            if(cursor.moveToFirst())//Move the cursor to the first row. This method will return false if the cursor is empty.
            {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String showTime = cursor.getString(cursor.getColumnIndex("showTime"));

                //设置显示
                editText_title.setText(title);
                editText_title.setSelection(title.length());
                textView_time.setText(showTime);
                editText_content.setText(content);
                editText_content.setSelection(content.length());  //设置光标显示在内容的最后（待实现）
            }



        }

    }


    //注意活动的生命周期问题，在write这个活动没有销毁前，上一个main活动已经开始创建
    //所以退出存数据的时候不能在destroy的时候存，这样的话上一个main活动加载不到新存的数据
    //应该在pause的时候存
    @Override
    protected void onPause() {
        super.onPause();

        //获取到intent传过来的数据
        Intent intent = getIntent();
        boolean isNew = intent.getBooleanExtra("isNew",false);

        //获取到当前内容
        EditText editText_title = findViewById(R.id.title);
        TextView textView_time = findViewById(R.id.time);
        EditText editText_content = findViewById(R.id.content);
        String title = editText_title.getText().toString();
        String time = textView_time.getText().toString();
        String content = editText_content.getText().toString();
        SharedPreferences pref = getSharedPreferences("author",MODE_PRIVATE);
        String author = pref.getString("name","佚名");

        //如果title和content有一个为空则不进行更新和保存，思考这样的友好性，如果输入很多不小心错碰返回怎么办（待实现）
        if (title.equals("") || content.equals("")){
            Log.d(TAG, "onDestroy: return");
            Toast.makeText(WriteActivity.this, "此次编辑不保存(标题和内容不能为空)", Toast.LENGTH_SHORT).show();
            return;
        }

        //退出当前活动的时候保存内容
        if (isNew){
            //如果是新日记则在数据库新建记录
            Log.d(TAG, "onDestroy: save");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            // 开始组装数据
            values.put("title", title);
            values.put("content", content);
            values.put("author", author);
            values.put("showTime", time);
            db.insert("Diarys", null, values); // 插入第一条数据

            //在这个活动没有完全退出前，上一个活动已经开始执行，导致initDiarys里面数据不刷新
            Log.d(TAG, "onStop: have saved");

        }else {
            Log.d(TAG, "onDestroy: update");
            //不是新日记则根据diaryId更新数据库里的记录（待实现）
            Integer diaryId = intent.getIntExtra("diaryId",0);;
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("content", content);
            values.put("author", author);
            db.update("Diarys", values, "diaryId = ?", new String[] { diaryId.toString() });

        }


    }

    //匹配
    public static String getWeek(int week) {
        //制作表：
        String[] arr = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        return arr[week - 1];
    }

}