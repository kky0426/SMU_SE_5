package com.example.campingapp;

public class writingReviewEntity {

    private String cid;

    private String cont;

    private String uid;

    private String userName;


    private float rating;

   /* public writingReviewEntity(String cid,String cont , String uid,  String userName){
        this.cid=cid;

        this.cont=cont;

        this.uid=uid;

        this.userName=userName;
    }*/

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


}
