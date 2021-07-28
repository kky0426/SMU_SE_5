package com.example.campingapp.reservation;



import android.net.Uri;

import java.io.Serializable;
import java.lang.reflect.Array;


public class CampingEntity implements Serializable{


    private String id;
    private String photoUri;
    private String campName;
    private String campAddr;
    private String campPhone;
    private String owner;

    private int review;
    private double rating;
    private int maxPeople;
    private int minPeople;
    private int price;
    private int addPrice;


    private boolean detailBbq;
    private boolean detailParking;
    private boolean detailPickup;
    private boolean detailWater;
    private boolean detailWifi;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
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




