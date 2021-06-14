package com.example.campingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.campingapp.databinding.ActivityCampInformationBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;

public class CampInformationActivity extends AppCompatActivity {

    ActivityCampInformationBinding binding;
    CampingEntity camp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camp_information);
        Dialog dialog = new Dialog(CampInformationActivity.this);
        Intent intent = getIntent();
        camp = (CampingEntity) intent.getSerializableExtra("CampingEntity");
        binding.setCamp(camp);
        if (camp == null) {

        } else {
            Uri uri = Uri.parse(camp.getPhotoUri());
            Glide.with(this)
                    .load(uri)
                    .into(binding.campInfoImage);
        }


        Button reserve_btn = (Button) binding.reserveButton;
        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                intent.putExtra("CampingEntity", camp);
                startActivity(intent);
            }
        });

        Button detail_btn = (Button) binding.campDetailButton;
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CampInformationActivity.this);
                dialog.setTitle(camp.getCampName());

                dialog.setMessage("가격 : "+camp.getPrice()+"\n"+
                                "인원 : "+camp.getMinPeople()+"~"+camp.getMaxPeople()+"\n"+
                                "추가 비용 : "+camp.getAddPrice()+"\n"+
                                "바베큐 : "+boolToString(camp.isDetailBbq())+"\n"+
                                "온수 : "+boolToString(camp.isDetailWater())+"\n"+
                                "Wifi : "+boolToString(camp.isDetailWifi())+"\n"+
                                "주차장 : "+boolToString(camp.isDetailParking())+"\n"+
                                "픽업 서비스 : "+boolToString(camp.isDetailPickup())+"\n"
                );
                dialog.show();
            }
        });


    }


    private String boolToString(boolean flag){
        String str =new String();
        if (flag){
             str = "O";
        }else{
            str = "X";
        }
        return str;
    }

}