package com.attention;

import java.util.List;

public class Attention {

    private int id;//id 1
    private String name;//漫画名 2
    private String work;//作者名 3
    private int work_id;//作者ID 4
    private String attention_num;//章节Id 5
    private  String introduce;//介绍 6
    private String cover;//封面 7

    @Override
    public String toString() {
        return "Attention [id=" + id + ", name=" + name + ", work=" + work + ", work_id=" + work_id + ", attention_num="
                + attention_num + ", introduce=" + introduce + ", cover=" + cover + "]";
    }
    public Attention(int id, String name, String work, int work_id, String attention_num, String introduce,
                     String cover) {
        super();
        this.id = id;
        this.name = name;
        this.work = work;
        this.work_id = work_id;
        this.attention_num = attention_num;
        this.introduce = introduce;
        this.cover = cover;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public int getWork_id() {
        return work_id;
    }
    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }
    public String getAttention_num() {
        return attention_num;
    }
    public void setAttention_num(String attention_num) {
        this.attention_num = attention_num;
    }
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }


}
