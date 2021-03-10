package com.example.haizi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Notifications  {
    // this class where it will appear in the firebase
    private String lines ;
    private String image;
    private String date;
    private String time;
    private String id;

    public Notifications() {
    }

    public Notifications(String lines, String image, String date, String time, String id) {
        this.lines = lines;
        this.image = image;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
