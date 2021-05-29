package com.example.campingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

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
        //setContentView(R.layout.activity_camp_information);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_camp_information);

        Intent intent = getIntent();
        CampingEntity camp = (CampingEntity) intent.getSerializableExtra("CampingEntity");
        binding.setCamp(camp);
        if (camp==null){

        }else {
            Uri uri = Uri.parse(camp.getPhotoUri());
            Glide.with(this)
                    .load(uri)
                    .into(binding.campInfoImage);
        }



        Button reserve_btn = (Button)binding.reserveButton;
        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReservationActivity.class);
                startActivity(intent);
            }
        });


    }



}