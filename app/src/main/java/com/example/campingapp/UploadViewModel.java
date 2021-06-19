package com.example.campingapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URI;
import java.util.Random;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class UploadViewModel extends AndroidViewModel {
    private DatabaseReference firebaseDb;
    CampingEntity camp;
    private StorageReference storage;
    public UploadViewModel(@NonNull Application application) {
        super(application);
        firebaseDb = FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://smu-se5-camping.appspot.com");
    }

    public void uploadDB(String name,String owner, String addr, String phone, int price, int addPrice , int minPeople, int maxPeople,
                         boolean bbq, boolean parking, boolean pickup, boolean water, boolean wifi, Uri photoUri){
        camp=new CampingEntity();
        camp.setCampName(name);
        camp.setPrice(price);
        camp.setCampPhone(phone);
        camp.setCampAddr(addr);
        camp.setOwner(owner);
        camp.setAddPrice(addPrice);
        camp.setMinPeople(minPeople);
        camp.setMaxPeople(maxPeople);
        camp.setDetailBbq(bbq);
        camp.setDetailParking(parking);
        camp.setDetailPickup(pickup);
        camp.setDetailWater(water);
        camp.setDetailWifi(wifi);
        camp.setAddPrice(addPrice);
        camp.setId(creatRandomId());
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                firebaseDb.child("camp").child(camp.getId()).setValue(camp);
                storage.child("campImage").child(camp.getId()).putFile(photoUri);
            }
        });
        th.start();
    }




    private String creatRandomId(){
        char[] temp = new char[10];
        for(int i=0 ; i<temp.length; i++){
            int div= (int) Math.floor(Math.random()*3);
            switch (div){
                case 0:
                    temp[i]=(char)(Math.random()*10+'0');
                    break;
                case 1:
                    temp[i]=(char)(Math.random()*26+'a');
                case 2:
                    temp[i]=(char)(Math.random()*26+'A');
            }
        }
        return new String(temp);
    }

}

