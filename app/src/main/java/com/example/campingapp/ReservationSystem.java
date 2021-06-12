package com.example.campingapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;

public class ReservationSystem {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    DatabaseReference firebaseDb = FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();


    public void reserveCamp(CampingEntity camp, String startDay, String endDay, int people){
        String userId = user.getUid();
        int price = (camp.getPrice() + camp.getAddPrice()*(people-camp.getMinPeople()));
        ReservationEntity reservation = new ReservationEntity(userId,camp.getId(),startDay,endDay,people,price);
        firebaseDb.child("reservation").child(camp.getId()).setValue(reservation);
    }
}
