package com.attention;



import java.util.List;

import com.google.gson.Gson;

/**
 * Created by 枫 on 2018/8/8.
 */

public class AttentionChapter {
    public AttentionChapter(int id, int this_chapter, String view_list, String danmak,
                            String danmak_X, String danmak_Y, String danmak_position) {
        super();
        this.id = id;
        this.this_chapter = this_chapter;
        this.view_list = view_list;
        this.danmak = danmak;
        this.danmak_X = danmak_X;
        this.danmak_Y = danmak_Y;
        this.danmak_position = danmak_position;
    }
    private int id; //id 1
    private int this_chapter; //当前话数 2
    private String view_list;//图片内容 3
    private String danmak;//弹幕内容 4
    private String danmak_X;//弹幕X轴 5
    private String danmak_Y;//弹幕Y轴 6
    private String danmak_position;//弹幕位置 7
    private int attention_id;//漫画Id 8



    public AttentionChapter(int id, int this_chapter, String view_list, String danmak, String danmak_X, String danmak_Y,
                            String danmak_position, int attention_id) {
        super();
        this.id = id;
        this.this_chapter = this_chapter;
        this.view_list = view_list;
        this.danmak = danmak;
        this.danmak_X = danmak_X;
        this.danmak_Y = danmak_Y;
        this.danmak_position = danmak_position;
        this.attention_id = attention_id;
    }
    public int getAttention_id() {
        return attention_id;
    }
    public void setAttention_id(int attention_id) {
        this.attention_id = attention_id;
    }
    @Override
    public String toString() {
        return "AttentionChapter [id=" + id + ", this_chapter=" + this_chapter + ", view_list=" + view_list
                + ", danmak=" + danmak + ", danmak_X=" + danmak_X + ", danmak_Y=" + danmak_Y + ", danmak_position="
                + danmak_position + ", attention_id=" + attention_id + "]";
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getThis_chapter() {
        return this_chapter;
    }
    public void setThis_chapter(int this_chapter) {
        this.this_chapter = this_chapter;
    }
    public String getView_list() {
        return view_list;
    }
    public void setView_list(String view_list) {
        this.view_list = view_list;
    }
    public String getDanmak() {
        return danmak;
    }
    public void setDanmak(String danmak) {
        this.danmak = danmak;
    }
    public String getDanmak_X() {
        return danmak_X;
    }
    public void setDanmak_X(String danmak_X) {
        this.danmak_X = danmak_X;
    }
    public String getDanmak_Y() {
        return danmak_Y;
    }
    public void setDanmak_Y(String danmak_Y) {
        this.danmak_Y = danmak_Y;
    }
    public String getDanmak_position() {
        return danmak_position;
    }
    public void setDanmak_position(String danmak_position) {
        this.danmak_position = danmak_position;
    }


}
