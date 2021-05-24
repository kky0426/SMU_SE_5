package com.example.campingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class DetailEntity {
    @PrimaryKey
    private int id;

    private int pickUp;
    private int water;
    private int barbeque;
    private int parking;
    private int wifi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPickUp() {
        return pickUp;
    }

    public void setPickUp(int pickUp) {
        this.pickUp = pickUp;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getBarbeque() {
        return barbeque;
    }

    public void setBarbeque(int barbeque) {
        this.barbeque = barbeque;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int wifi) {
        this.wifi = wifi;
    }
}
