package com.example.campingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campingapp.databinding.ActivityCampInformationBinding;
import com.example.campingapp.databinding.ActivityUploadInformationBinding;

import java.util.ArrayList;
import java.util.List;


public class UploadInformation extends AppCompatActivity {

    String name;
    String addr;
    String phone;
    int price;
    int addPrice;
    int minPeople;
    int maxPeople;
    boolean bbq;
    boolean wifi;
    boolean parking;
    boolean water;
    boolean pickup;

    final ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_upload_information);
        ActivityUploadInformationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_information);
        UploadViewModel viewModel = ViewModelProviders.of(this).get(UploadViewModel.class);
        //detail = (Button) findViewById(R.id.set_detail_button);
        Button detail = (Button) binding.setDetailButton;
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = new String[]{"셔틀 or 픽업 서비스",
                        "바베큐", "온수", "주차장", "Wifi"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(UploadInformation.this);
                dialog.setTitle("세부정보 선택")
                        .setMultiChoiceItems(items,
                                new boolean[]{false, false, false, false, false},
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            Toast.makeText(UploadInformation.this,
                                                    items[which], Toast.LENGTH_SHORT).show();
                                            list.add(items[which]);
                                        } else {
                                            list.remove(items[which]);
                                        }
                                    }
                                })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for (String item : list) {
                                    switch (item){
                                        case "셔틀 or 픽업 서비스":
                                            pickup=true;
                                            break;
                                        case "바베큐":
                                            bbq=true;
                                            break;
                                        case "온수":
                                            water=true;
                                            break;
                                        case "주차장":
                                            parking=true;
                                            break;
                                        case "Wifi":
                                            wifi=true;
                                            break;
                                    }
                                }

                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bbq=false;
                                pickup=false;
                                parking=false;
                                wifi=false;
                                water=false;
                            }
                        });
                dialog.create();
                dialog.show();
            }
        });
        Button upload = (Button) binding.buttonUpload;
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = binding.uploadCampName.getText().toString();
                addr = binding.uploadCampAddr.getText().toString();
                phone = binding.uploadCampPhone.getText().toString();
                price = Integer.parseInt(binding.uploadCampPrice.getText().toString());
                addPrice = Integer.parseInt(binding.uploadAddPrice.getText().toString());
                minPeople = Integer.parseInt(binding.peopleMin.getText().toString());
                maxPeople = Integer.parseInt(binding.peopleMax.getText().toString());

                viewModel.uploadDB(name,addr,phone,price,addPrice,minPeople,maxPeople,bbq,parking,pickup,water,wifi);


                onBackPressed();
            }
        });


    }

}