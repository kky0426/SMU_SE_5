package com.example.campingapp;

import android.app.Application;
import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadViewModel extends AndroidViewModel {
    private DatabaseReference firebaseDb;
    //AppDatabase db;
    CampingEntity camp;
    public UploadViewModel(@NonNull Application application) {
        super(application);
        //db = AppDatabase.getInstance(application);
        firebaseDb = FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
    }

    public void uploadDB(String name,String addr,String phone,int price,int addPrice ,int minPeople,int maxPeople,
                         boolean bbq,boolean parking,boolean pickup,boolean water,boolean wifi){
        camp=new CampingEntity();
        camp.setCampName(name);
        camp.setPrice(price);
        camp.setCampPhone(phone);
        camp.setCampAddr(addr);
        camp.setAddPrice(addPrice);
        camp.setMinPeople(minPeople);
        camp.setMaxPeople(maxPeople);
        camp.setDetailBbq(bbq);
        camp.setDetailParking(parking);
        camp.setDetailPickup(pickup);
        camp.setDetailWater(water);
        camp.setDetailWifi(wifi);
        camp.setAddPrice(addPrice);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                firebaseDb.child("camp").push().setValue(camp);;
            }
        });
        th.start();
    }

}
