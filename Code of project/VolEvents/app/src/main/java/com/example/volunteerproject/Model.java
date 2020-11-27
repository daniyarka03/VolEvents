package com.example.volunteerproject;

public class Model {
    /** Declaring variables  */
    String id, name, date, time, userId;

    public Model() {

    }

    public Model(String id, String name, String date, String time, String userId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.userId = userId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
