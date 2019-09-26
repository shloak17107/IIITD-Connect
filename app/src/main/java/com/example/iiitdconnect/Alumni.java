package com.example.iiitdconnect;

public class Alumni {
    private String name;
    private String branch;
    private String contactNumber;
    private String dateOfBirth;
    private String linkedIn;
    private String webpage;
    private String yearOfPassing;
    private String currentStatus;
    private String instituteCompany;

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

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getInstituteCompany() {
        return instituteCompany;
    }

    public void setInstituteCompany(String instituteCompany) {
        this.instituteCompany = instituteCompany;
    }
//, String contactNumber, String dateOfBirth, String linkedIn, String webpage, String yearOfPassing, String currentStatus, String instituteCompany
    public Alumni(String name, String branch){
        this.name = name;
        this.branch = branch;
//        this.contactNumber = contactNumber;
//        this.branch = branch;
//        this.dateOfBirth = dateOfBirth;
//        this.webpage=webpage;
//        this.yearOfPassing=yearOfPassing;
//        this.linkedIn=linkedIn;
//        this.currentStatus=currentStatus;
//        this.instituteCompany=instituteCompany;
    }
}
