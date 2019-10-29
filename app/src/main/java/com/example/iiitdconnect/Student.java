package com.example.iiitdconnect;

public class Student {
    private String name;
    private String branch;
    private String contactNumber;
    private String dateOfBirth;
    private String linkedIn;
    private String webpage;
    private String yearOfPassing;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(String yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public Student(String name, String branch, String contactNumber, String dateOfBirth, String linkedIn, String webpage, String yearOfPassing){
        this.name = name;
        this.contactNumber = contactNumber;
        this.branch = branch;
        this.dateOfBirth = dateOfBirth;
        this.webpage=webpage;
        this.yearOfPassing=yearOfPassing;
        this.linkedIn=linkedIn;
    }
}
