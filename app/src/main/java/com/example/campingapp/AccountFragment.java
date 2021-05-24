package com.example.campingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AccountFragment extends Fragment {

    Button btn_used;
    Button btn_resv;
    Button btn_upload;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        btn_resv = (Button) view.findViewById(R.id.confrim_reserve_button);
        btn_resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConfirmReservationActivity.class);
                startActivity(intent);
            }
        });

        btn_used = (Button) view.findViewById(R.id.used_camp);
        btn_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_upload = (Button)view.findViewById(R.id.upload_button);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UploadInformation.class);
                startActivity(intent);
            }
        });

        return view;
    }
}