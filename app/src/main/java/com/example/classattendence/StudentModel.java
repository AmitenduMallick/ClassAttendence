package com.example.classattendence;

public class StudentModel {

    private String EnrollmentNo;
    private String Mail;
    private String Name;
    private String StudentImage;

    public StudentModel() {
    }

    public StudentModel(String enrollmentNo, String mail, String name, String studentImage) {
        EnrollmentNo = enrollmentNo;
        Mail = mail;
        Name = name;
        StudentImage = studentImage;
    }

    public String getEnrollmentNo() {
        return EnrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        EnrollmentNo = enrollmentNo;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStudentImage() {
        return StudentImage;
    }

    public void setStudentImage(String studentImage) {
        StudentImage = studentImage;
    }
}
