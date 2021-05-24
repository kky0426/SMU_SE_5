package com.example.campingapp;

//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.reflect.Array;

//@Entity
public class CampingEntity implements Serializable{
    //@PrimaryKey(autoGenerate = true)

    private String id;

    private String campName;
    private String campAddr;
    private String campPhone;

    //@ColumnInfo(defaultValue = "0")
    private int review;

    //@ColumnInfo(defaultValue = "0.0")
    private double rating;
    private int maxPeople;
    private int minPeople;

    //@ColumnInfo(defaultValue = "0")
    private int price;
    private int addPrice;

    //@ColumnInfo(defaultValue = "false")
    private boolean detailBbq;
    //@ColumnInfo(defaultValue = "false")
    private boolean detailParking;
    //@ColumnInfo(defaultValue = "false")
    private boolean detailPickup;
    //@ColumnInfo(defaultValue = "false")
    private boolean detailWater;
    //@ColumnInfo(defaultValue = "false")
    private boolean detailWifi;

    public boolean isDetailBbq() {
        return detailBbq;
    }

    public void setDetailBbq(boolean detailBbq) {
        this.detailBbq = detailBbq;
    }

    public boolean isDetailParking() {
        return detailParking;
    }

    public void setDetailParking(boolean detailParking) {
        this.detailParking = detailParking;
    }

    public boolean isDetailPickup() {
        return detailPickup;
    }

    public void setDetailPickup(boolean detailPickup) {
        this.detailPickup = detailPickup;
    }

    public boolean isDetailWater() {
        return detailWater;
    }

    public void setDetailWater(boolean detailWater) {
        this.detailWater = detailWater;
    }

    public boolean isDetailWifi() {
        return detailWifi;
    }

    public void setDetailWifi(boolean detailWifi) {
        this.detailWifi = detailWifi;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getCampAddr() {
        return campAddr;
    }

    public void setCampAddr(String campAddr) {
        this.campAddr = campAddr;
    }

    public String getCampPhone() {
        return campPhone;
    }

    public void setCampPhone(String campPhone) {
        this.campPhone = campPhone;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public int getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(int minPeople) {
        this.minPeople = minPeople;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(int addPrice) {
        this.addPrice = addPrice;
    }

    public int getReview() { return review; }

    public void setReview(int review) { this.review = review; }
}




