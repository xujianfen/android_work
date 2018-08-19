package com.attention;

public class MyComics {


    private String id;//id 1
    private String name;//漫画名 2
    private String cover;//封面 7

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public MyComics(String id, String name, String cover) {
        super();
        this.id = id;
        this.name = name;
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "MyComics [id=" + id + ", name=" + name + ", cover=" + cover + "]";
    }

}
