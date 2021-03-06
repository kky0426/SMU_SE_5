package com.example.campingapp.reservation;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class ReservationEntity {
    private String userId;
    private String campId;
    private String campName;
    private String startDay;
    private String endDay;
    private int people;
    private int price;
    /*
    public ReservationEntity(String userId, String campId, String startDay, String endDay, int people, int price) {
        this.userId = userId;
        this.campId = campId;
        this.startDay = startDay;
        this.endDay = endDay;
        this.people = people;
        this.price = price;
    }*/
    public String getCampName(){return campName;}
    public void setCampName(String name){this.campName=name;}
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
