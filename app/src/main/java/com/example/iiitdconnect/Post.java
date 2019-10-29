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

    public Post(String createdBy, String title, String body, ArrayList<String> tags){
        Date date = new Date();
        long time = date.getTime();
        Timestamp t = new Timestamp(time);
        this.timestamp = t.toString();
        this.createdBy = createdBy;
        this.title = title;
        this.body = body;
        Map<String, String> temp = new HashMap<>();
        for(String cat: tags){
            temp.put(cat, "");
        }
        this.category = new categories(temp);
    }
    public Post(){}

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
