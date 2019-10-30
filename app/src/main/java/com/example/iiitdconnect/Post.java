package com.example.iiitdconnect;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
    private String createdBy, title, body;
    private String timestamp;
    private categories category;
    private String date;
    private String time;
    private String venue;
    private interested interested;

    public categories getCategory() {
        return category;
    }

    public void setCategory(categories category) {
        this.category = category;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Post(String createdBy, String title, String body, ArrayList<String> tags, String date, String time, String venue){
        this.interested = new interested();
        this.date = date;
        this.time = time;
        Date date_d = new Date();
        long time_d = date_d.getTime();
        Timestamp t = new Timestamp(time_d);
        this.timestamp = t.toString();
        this.createdBy = createdBy;
        this.title = title;
        this.body = body;
        this.venue = venue;
        Map<String, String> temp = new HashMap<>();
        for(String cat: tags){
            temp.put(cat, "");
        }
        this.category = new categories(temp);
    }


    public Post(){}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public categories getCategories() {
        return category;
    }

    public void setCategories(categories tags) {
        this.category = tags;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
