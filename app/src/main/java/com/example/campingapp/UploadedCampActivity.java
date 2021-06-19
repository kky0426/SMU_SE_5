package com.example.campingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UploadedCampActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_camp);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        SearchSystem searchSystem = ViewModelProviders.of(UploadedCampActivity.this).get(SearchSystem.class);
        ListView uploadedList = (ListView)findViewById(R.id.uploaded_list);
        //UploadItemAdapter adapter = new UploadItemAdapter();
        searchSystem.printUploadedCamp(uploadedList,this);

    }



}