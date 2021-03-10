package com.example.haizi.Model;

public class classesModel {
    String classId , classBoss ,firstSemImage ,classNum ,FirstSemesterNotes;
    String    secSemImage,SecondSemesterNotes;
    String FirstDate ,FirstTime ,SecondDate,SecondTime ;




    public classesModel() {
    }

    public classesModel(String classId, String classBoss, String firstSemImage, String classNum, String firstSemesterNotes, String secSemImage,
                        String secondSemesterNotes, String firstDate,
                        String firstTime, String secondDate, String secondTime) {
        this.classId = classId;
        this.classBoss = classBoss;
        this.firstSemImage = firstSemImage;
        this.classNum = classNum;
        FirstSemesterNotes = firstSemesterNotes;
        this.secSemImage = secSemImage;
        SecondSemesterNotes = secondSemesterNotes;
        FirstDate = firstDate;
        FirstTime = firstTime;
        SecondDate = secondDate;
        SecondTime = secondTime;
    }

    public String getFirstDate() {
        return FirstDate;
    }

    public void setFirstDate(String firstDate) {
        FirstDate = firstDate;
    }

    public String getFirstTime() {
        return FirstTime;
    }

    public void setFirstTime(String firstTime) {
        FirstTime = firstTime;
    }

    public String getSecondDate() {
        return SecondDate;
    }

    public void setSecondDate(String secondDate) {
        SecondDate = secondDate;
    }

    public String getSecondTime() {
        return SecondTime;
    }

    public void setSecondTime(String secondTime) {
        SecondTime = secondTime;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassBoss() {
        return classBoss;
    }

    public void setClassBoss(String classBoss) {
        this.classBoss = classBoss;
    }

    public String getFirstSemImage() {
        return firstSemImage;
    }

    public void setFirstSemImage(String firstSemImage) {
        this.firstSemImage = firstSemImage;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getFirstSemesterNotes() {
        return FirstSemesterNotes;
    }

    public void setFirstSemesterNotes(String firstSemesterNotes) {
        FirstSemesterNotes = firstSemesterNotes;
    }

    public String getSecSemImage() {
        return secSemImage;
    }

    public void setSecSemImage(String secSemImage) {
        this.secSemImage = secSemImage;
    }

    public String getSecondSemesterNotes() {
        return SecondSemesterNotes;
    }

    public void setSecondSemesterNotes(String secondSemesterNotes) {
        SecondSemesterNotes = secondSemesterNotes;
    }
}
