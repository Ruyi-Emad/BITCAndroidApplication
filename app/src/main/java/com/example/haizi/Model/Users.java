package com.example.haizi.Model;

public class Users {
    private String name, phone, password, image, studentId, classNum, major;

    public Users() {
    }

    public Users(String name, String phone, String password, String image, String studentId, String classNum, String major) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.studentId = studentId;
        this.classNum = classNum;
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
