package com.example.attendanceapp;

public class ClassItem {

    private long cid;

    public ClassItem(long cid, String className, String subjectName) {
        this.cid = cid;
        this.className = className;
        this.subjectName = subjectName;
    }

    private String className;

    public ClassItem() {

    }

    String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    private String subjectName;

    public ClassItem(String className, String subjectName) {
        this.className = className;
        this.subjectName = subjectName;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
