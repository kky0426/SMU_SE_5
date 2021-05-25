package com.example.campingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.campingapp.databinding.FragmentAccountBinding;


public class AccountFragment extends Fragment {

    Button btn_used;
    Button btn_resv;
    Button btn_upload;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        FragmentAccountBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account,container,false);
        btn_resv = binding.confrimReserveButton;
        btn_resv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ConfirmReservationActivity.class);
                startActivity(intent);
            }
        });

        btn_used = binding.usedCamp;
        btn_used.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_upload=binding.uploadButton;
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UploadInformation.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}