package com.example.campingapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import java.net.URI;
import java.util.Random;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class WritingReview extends AppCompatActivity {
    private DatabaseReference mDatabase2;
    private RatingBar ratingBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing_review);
        Button btn=(Button)findViewById(R.id.review_button);
        Intent intent = getIntent();
        mDatabase2=FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        String campid=getIntent().getStringExtra("campid");
        //ReservationEntity camp=(ReservationEntity) intent.getSerializableExtra("camp");
        DatabaseReference firebaseDb = FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser user=auth.getCurrentUser();
                EditText editText1=(EditText)findViewById(R.id.review_writing);
                writingReviewEntity review;
                String cid = campid;
                String uid = user.getUid();
                String cont=editText1.getText().toString();
                String name=user.getEmail();
                review=new writingReviewEntity();
                review.setCid(cid);
                review.setCont(cont);
                review.setUid(uid);
                review.setUserName(name);
                firebaseDb.child("review").child(cid).child(uid).setValue(review);
                Toast.makeText(getApplicationContext(),"리뷰 작성이 완료되었습니다.",Toast.LENGTH_LONG).show();





            }
        });



    }



}
