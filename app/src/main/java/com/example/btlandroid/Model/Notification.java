package com.example.btlandroid.Model;

import java.io.Serializable;

public class Notification implements Serializable {
    String title, category, time, date, description;

    public Notification() {
    }

    public Notification(String title, String category, String time, String date, String description) {
        this.title = title;
        this.category = category;
        this.time = time;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
