package com.example.campingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ManageReservationActivity extends AppCompatActivity {
    ReserveCampItemAdapter reserveCampItemAdapter;
    ReservationSystem reservationSystem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservation);
        reservationSystem = ViewModelProviders.of(this).get(ReservationSystem.class);
        Intent intent = getIntent();
        CampingEntity camp = (CampingEntity) intent.getSerializableExtra("CampingEntity");
        ListView manageList = findViewById(R.id.manage_list);
        TextView campName = findViewById(R.id.manage_camp_name);
        campName.setText(camp.getCampName());
        reserveCampItemAdapter = new ReserveCampItemAdapter();
        reservationSystem.printCampReservation(camp, new CallbackData() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot dSanp : dataSnapshot.getChildren()){
                    ReservationEntity reservation = dSanp.getValue(ReservationEntity.class);
                    reserveCampItemAdapter.addItem(reservation);
                }
                manageList.setAdapter(reserveCampItemAdapter);
            }
        });

    }

    class ReserveCampItemAdapter extends BaseAdapter {
        ArrayList<ReservationEntity> items = new ArrayList<ReservationEntity>();


        public void addItem(ReservationEntity item) {
            items.add(item);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReserveItemCampView view = null;
            if (convertView == null) {
                view = new ReserveItemCampView(ManageReservationActivity.this);
            } else {
                view = (ReserveItemCampView) convertView;
            }
            ReservationEntity item = items.get(position);
            view.setTextStart(item.getStartDay());
            view.setTextEnd(item.getEndDay());
            view.setTextPeople(item.getPeople());
            Button cancel = view.findViewById(R.id.reserveCamp_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ManageReservationActivity.this);
                    dialog.setMessage("취소하시겠습니까?");
                    dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reservationSystem.cancelReservation(item, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(ManageReservationActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    items.remove(item);
                                    reserveCampItemAdapter.items = items;
                                    reserveCampItemAdapter.notifyDataSetChanged();

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }

                    });
                    dialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            });

            return view;
        }


    }
}