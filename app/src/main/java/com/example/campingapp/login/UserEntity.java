package com.example.campingapp.login;

public class UserEntity {
    public String uid;
    public String email;
    public String name;
    public String businessNum;

    public UserEntity(String uid, String email, String name, String businessNum) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.businessNum = businessNum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessNum(){return businessNum;}

    public void  setBusinessNum(){this.businessNum = businessNum;}
}
