package com.example.iiitdconnect;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")

public class Post implements Serializable {
    private String createdBy, title, body;
    private String timestamp;
    private categories category;
    private String date;
    private String time;
    private String venue;
    public interested interestedpeople;



    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public interested getInterestedpeople() {
        return interestedpeople;
    }

    public void setInterestedpeople(interested interestedpeople) {
        this.interestedpeople = interestedpeople;
    }

    public Post(String createdBy, String title, String body, ArrayList<String> tags, String date, String time, String venue){
        this.interestedpeople = new interested(new HashMap<String, String>());
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
        temp.put("tempPost", "tempPost");
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

    public categories getCategory() {
        return category;
    }

    public void setCategory(categories category) {
        this.category = category;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
