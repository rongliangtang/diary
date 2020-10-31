package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyActivity extends AppCompatActivity {

    String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        //从SharedPreferences中读取作者名字到editText中
        SharedPreferences pref = getSharedPreferences("author",MODE_PRIVATE);
        String author = pref.getString("name","佚名");

        final EditText editText_author = findViewById(R.id.author);
        editText_author.setEnabled(false);  //不可编辑
        editText_author.setBackground(null);    //不显示下划线
        editText_author.setTextColor(Color.DKGRAY); //设置文本颜色
        editText_author.setText(author);
        editText_author.setSelection(editText_author.getText().toString().length());    //设置光标在最后

        Button button_save = findViewById(R.id.Button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新author信息sharedPreferences中
                String author = editText_author.getText().toString();
                Log.d(TAG, "onClick: " + author);
                //更新作者名字到sharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences("author",MODE_PRIVATE).edit();
                editor.putString("name",author);
                editor.apply();
                editText_author.setEnabled(false);  //不可编辑
            }
        });

        Button button_edit = findViewById(R.id.Button_edit);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String author = editText_author.getText().toString();
                editText_author.setEnabled(true);  //可编辑
                editText_author.setSelection(author.length());
            }
        });



    }
}