package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    private String TAG = "WriteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        //获取到intent传过来的数据
        Intent intent = getIntent();
        boolean isNew = intent.getBooleanExtra("isNew",false);

        //根据isNew的值来判断是否为新建日记
        if (isNew){
            Log.d(TAG, "isNew is true");

            //获取当前的日期和星期几（待实现）
            Calendar calendar = Calendar.getInstance(); // get current instance of the calendar
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            //System.out.println(formatter.format(calendar.getTime()) + "-" + getWeek(week));


            //新建日记的日期为当前日期
            TextView textView_time = findViewById(R.id.time);
            textView_time.setText(formatter.format(calendar.getTime()) + " " + getWeek(week));

        }else {
            Log.d(TAG, "isNew is false");

            int diaryId = intent.getIntExtra("diaryId",0);
            Log.d(TAG, "diaryId is " + diaryId);
            //根据diaryId显示日记内容
            //编辑日记的日期为创建时候的日期
            EditText editText_title = findViewById(R.id.title);
            TextView textView_time = findViewById(R.id.time);
            EditText editText_content = findViewById(R.id.content);

            //此处根据diaryId从数据库中取出日记内容（待实现）





            //设置显示
            editText_title.setText("title id " + diaryId);
            editText_title.setSelection(("title id " + diaryId).length());
            textView_time.setText("此处为日记创建的日期");
            editText_content.setText("content id " + diaryId);
            editText_content.setSelection(("content id " + diaryId).length());  //设置光标显示在内容的最后（待实现）


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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

        //如果title和content有一个为空则不进行更新和保存，思考这样的友好性，如果输入很多不小心错碰返回怎么办（待实现）
        if (title.equals("") || content.equals("")){
            Log.d(TAG, "onDestroy: return");
            Toast.makeText(WriteActivity.this, "此次编辑不保存(标题和内容不能为空)", Toast.LENGTH_SHORT).show();
            return;
        }

        //退出当前活动的时候保存内容
        if (isNew){
            //如果是新日记则在数据库新建记录（待实现）
            Log.d(TAG, "onDestroy: save");




        }else {
            Log.d(TAG, "onDestroy: update");
            //不是新日记则根据diaryId更新数据库里的记录（待实现）
            int diaryId = intent.getIntExtra("diaryId",0);;



        }


    }

    //匹配
    public static String getWeek(int week) {
        //制作表：
        String[] arr = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        return arr[week - 1];
    }

}