package com.example.classattendence.ui.subjectlist;

public class SubjectModel {
    private String SubjectCode;
    private String SubjectName;

    public SubjectModel() {
    }

    public SubjectModel(String subjectCode, String subjectName) {
        SubjectCode = subjectCode;
        SubjectName = subjectName;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
}
