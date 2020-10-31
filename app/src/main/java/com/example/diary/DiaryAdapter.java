package com.example.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DiaryAdapter extends ArrayAdapter {
    private int resourceId;

    public DiaryAdapter(Context context, int textViewResourceId, List<diary> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        diary diary = (diary) getItem(position);    //获取当前项的Fruit实例

        //提升ListView的运行效率：不会重复加载布局，对控件的实例进行缓存。
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.diaryTimeWeek = (TextView) view.findViewById(R.id.diary_time_week);
            viewHolder.diaryTime = (TextView) view.findViewById(R.id.diary_time);
            viewHolder.diaryTitle = (TextView) view.findViewById(R.id.diary_title);
            view.setTag(viewHolder);    //将ViewHolder存储在view中

        }else {
            view = convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        //将从数据库获取的alltime分割为week和time
        String allTime = diary.getTime();
        String timeWeek = allTime.substring(allTime.length()-5,allTime.length());
        String time = allTime.substring(0,allTime.length()-6);

        viewHolder.diaryTimeWeek.setText(timeWeek);
        viewHolder.diaryTime.setText(time);
        viewHolder.diaryTitle.setText(diary.getTitle());
        return view;
    }

    class ViewHolder{
        TextView diaryTimeWeek;
        TextView diaryTime;
        TextView diaryTitle;
    }

}
