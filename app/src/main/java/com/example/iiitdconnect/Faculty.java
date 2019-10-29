package com.example.iiitdconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Faculty {
    private String name;
    private String department;
    private String expertise;
    private String linkedIn;
    private String webpage;
    private categories category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }
    public categories getCategory() {
        return category;
    }

    public void setCategory(categories category) {
        this.category = category;
    }

    public Faculty(String name, String department, String expertise, String webpage, String linkedIn, ArrayList<String> tags){
        this.name = name;
        this.department = department;
        this.expertise = expertise;
        this.webpage=webpage;
        this.linkedIn=linkedIn;
        Map<String, String> temp = new HashMap<>();
        for(String cat: tags){
            temp.put(cat, "");
        }
        this.category = new categories(temp);
    }

    public Faculty(){}
}
