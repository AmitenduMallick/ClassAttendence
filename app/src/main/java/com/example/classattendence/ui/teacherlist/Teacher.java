package com.example.classattendence.ui.teacherlist;

public class Teacher {
    private String EnrollmentNo;
    private String Mail;
    private String Name;
    private String TeacherImage;

    public Teacher() {
    }

    public Teacher(String enrollmentNo, String mail, String name, String teacherImage) {
        EnrollmentNo = enrollmentNo;
        Mail = mail;
        Name = name;
        TeacherImage = teacherImage;
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

    public String getTeacherImage() {
        return TeacherImage;
    }

    public void setTeacherImage(String teacherImage) {
        TeacherImage = teacherImage;
    }
}
