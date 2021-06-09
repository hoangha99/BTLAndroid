package com.example.btlandroid.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String fullName, birthOfDate, email, gender;

    public User(String fullName, String birthOfDate, String email, String gender) {
        this.fullName = fullName;
        this.birthOfDate = birthOfDate;
        this.email = email;
        this.gender = gender;
    }

    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(String birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
