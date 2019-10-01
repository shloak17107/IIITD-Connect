package com.example.iiitdconnect;

import java.sql.Timestamp;
import java.util.Date;

public class Post {
    private String createdBy, title, body, tags;
    private String timestamp;

    public Post(String createdBy, String title, String body, String tags){
        Date date = new Date();
        long time = date.getTime();
        Timestamp t = new Timestamp(time);
        this.timestamp = t.toString();
        this.createdBy = createdBy;
        this.title = title;
        this.body = body;
        this.tags = tags;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
