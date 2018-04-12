package com.example.vlad.a_fishka.Model;

/**
 * Created by Vlad on 11.04.2018.
 */

public class News {
    private String title;
    private String content;
    private String imageUrl;
    private String date;

    public News(String title, String content, String imageUrl, String date) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
