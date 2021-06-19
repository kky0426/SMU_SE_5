package com.example.campingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.campingapp.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class  AccountFragment extends Fragment {

    Button btn_used;
    Button btn_resv;
    Button btn_upload;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference firebaseDb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_account, container, false);
        FragmentAccountBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false);
        firebaseDb =FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        String uid=user.getUid();
        final String[] business = new String[1];
        firebaseDb.child("user").child(uid).child("businessNum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                business[0]= (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_resv = binding.confrimReserveButton;
        btn_resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConfirmReservationActivity.class);
                startActivity(intent);
            }
        });

        Button btn_uploaded = binding.myCamp;
        btn_uploaded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (business[0]!=null) {
                    Intent intent = new Intent(getActivity(), UploadedCampActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"사업자회원만 사용가능한 기능입니다.",Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_upload=binding.uploadButton;
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (business[0]!=null) {
                    Intent intent = new Intent(getActivity(), UploadInformation.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"사업자회원만 사용가능한 기능입니다.",Toast.LENGTH_SHORT).show();

                }
            }
        });

        return binding.getRoot();
    }
}