package com.example.diary;

public class diary {
    private int diaryId;    //存在数据库里对应记录的id，后面查看日记内容就是通过id去数据库取出日记内容
    private String time;
    private String title;

    public diary(int diaryId,String time, String title){
        this.diaryId = diaryId;
        this.time = time;
        this.title = title;
    }

    public int getDiaryId(){
        return diaryId;
    }

    public String getTime(){
        return time;
    }

    public String getTitle(){
        return title;
    }

}
