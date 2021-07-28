package com.example.campingapp.upload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ListView;

import com.example.campingapp.R;
import com.example.campingapp.home.SearchSystem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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