package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private List<diary> diaryList = new ArrayList<>();
    private Boolean footOnly = false;   //用来判断是否为listView添加过foot，我们只在listView中foot添加一次TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //应用程序启动时读取SharedPreferences中的作者信息，若不存在作者信息，则设置一个默认的作者姓名
        SharedPreferences pref = getSharedPreferences("author",MODE_PRIVATE);
        String author = pref.getString("name","不存在作者信息");
        Log.d(TAG, "onCreate: " + author);
        if (author.equals("不存在作者信息")){
            //设置作者默认名字到sharedPreferences，默认名字为佚名
            SharedPreferences.Editor editor = getSharedPreferences("author",MODE_PRIVATE).edit();
            editor.putString("name","佚名");
            editor.apply();
        }

    }

    //这是使用onStar()来加载listView，因为要让编写日记界面返回主界面能刷新
    @Override
    protected void onStart() {
        super.onStart();
        //        //使用ArrayAdapter适配器，泛型适用String，android.R.layout.simple_list_item_1作为ListView的子项布局。
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,android.R.layout.simple_list_item_1,data);
//        ListView listView = (ListView) findViewById(R.id.list_view);
//        listView.setAdapter(adapter);

        diaryList.clear();  //清空list，否则每次返回到这个活动list内容会重新堆一次
        initDiarys();   //初始化日记数据
        DiaryAdapter adapter = new DiaryAdapter(MainActivity.this,R.layout.diary, diaryList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //如果footOnly为ture则不在添加foot
        if (!footOnly){
            //在listView底部添加TextView
            TextView bootomText = new TextView(this);
            bootomText.setText(R.string.tip);
            bootomText.setGravity(Gravity.CENTER_HORIZONTAL);
            bootomText.setPadding(0,80,0,120);
            listView.addFooterView(bootomText);
            footOnly = true;
        }

        //为每个item设置监听点击时间，获取到点击item的position
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diary diary = diaryList.get(position);
                Log.d(TAG, "onItemClick: " + position);
                //Toast.makeText(MainActivity.this, diary.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent_edit = new Intent(MainActivity.this,WriteActivity.class);
                intent_edit.putExtra("isNew",false);    //isNew为false代表这是要查看和编辑文章，会根据diaryId从数据库中取出内容来显示
                intent_edit.putExtra("diaryId",diary.getDiaryId());
                startActivity(intent_edit);
            }
        });

    }

    //此方法的作用是创建一个选项菜单，我们要重写这个方法
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //在点击这个菜单的时候，会做对应的事，类似于侦听事件，这里我们也要重写它
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //这里是一个switch语句,主要通过menu文件中的id值来确定点了哪个菜单，然后做对应的操作，这里的menu是指你加载的那个菜单文件
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent_add = new Intent(MainActivity.this,WriteActivity.class);
                intent_add.putExtra("isNew",true);  //isNew为true代表这是一篇新文章
                startActivity(intent_add);
                break;
            case R.id.my:
                Intent intent_my = new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent_my);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDiarys() {
        Log.d(TAG, "initDiarys have refreshed");

        for (int i = 0;i < 1;i++){
            diary diary1 = new diary(1,"2020-10-24 周六","title1");
            diaryList.add(diary1);

            diary diary2 = new diary(2,"2020-10-24 周六","title2");
            diaryList.add(diary2);

            diary diary3 = new diary(3,"2020-10-24 周六","title3");
            diaryList.add(diary3);

            diary diary4 = new diary(4,"2020-10-24 周六","title4");
            diaryList.add(diary4);

            diary diary5 = new diary(5,"2020-10-24 周六","title5");
            diaryList.add(diary5);
        }
    }

}