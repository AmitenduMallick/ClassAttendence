package com.example.classattendence;

public class Admin {
    private String AdminEnrollment;
    private String AdminImage;
    private String AdminMail;
    private String AdminName;
    private String ID;

    public Admin() {
    }

    public Admin(String adminEnrollment, String adminImage, String adminMail, String adminName, String ID) {
        AdminEnrollment = adminEnrollment;
        AdminImage = adminImage;
        AdminMail = adminMail;
        AdminName = adminName;
        this.ID = ID;
    }

    public String getAdminEnrollment() {
        return AdminEnrollment;
    }

    public void setAdminEnrollment(String adminEnrollment) {
        AdminEnrollment = adminEnrollment;
    }

    public String getAdminImage() {
        return AdminImage;
    }

    public void setAdminImage(String adminImage) {
        AdminImage = adminImage;
    }

    public String getAdminMail() {
        return AdminMail;
    }

    public void setAdminMail(String adminMail) {
        AdminMail = adminMail;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
