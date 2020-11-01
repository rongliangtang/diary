package com.example.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_DIARYS = "create table Diarys ("
            + "diaryId integer PRIMARY KEY autoincrement,"
            + "title VARHAR(30) NOT NULL,"
            + "content TEXT NOT NULL,"
            + "author VARHAR(30) NOT NULL,"
            + "showTime VARHAR(30) NOT NULL,"
            + "createTime TIMESTAMP NOT NULL DEFAULT current_timestamp)";



    private Context mContext;

    public MyDatabaseHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARYS);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists Book");
//        db.execSQL("drop table if exists Category");
//        onCreate(db);
    }

}