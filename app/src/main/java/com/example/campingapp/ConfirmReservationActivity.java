package com.example.campingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ConfirmReservationActivity extends AppCompatActivity  {
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);
        ReservationSystem reservationSystem = ViewModelProviders.of(this).get(ReservationSystem.class);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String userId = user.getUid();
        ListView confirmList = (ListView) findViewById(R.id.confirm_list);

        ReserveItemAdapter adapter = new ReserveItemAdapter();

        reservationSystem.printReservation(new Callback() {

            @Override
            public void onSuceess(DataSnapshot dataSnapshot) {
                for (DataSnapshot dSnap1 : dataSnapshot.getChildren()){
                    for (DataSnapshot dSnap2 : dSnap1.getChildren()){
                        if (dSnap2.getKey().equals(userId)) {
                            ReservationEntity reservation = dSnap2.getValue(ReservationEntity.class);
                            adapter.addItem(reservation);
                        }
                    }
                }
                confirmList.setAdapter(adapter);
            }
        });



    }

    class ReserveItemAdapter extends BaseAdapter {
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
            ReserveItemView view = null;
            if (convertView == null) {
                view = new ReserveItemView(ConfirmReservationActivity.this);
            } else {
                view = (ReserveItemView) convertView;
            }
            ReservationEntity item = items.get(position);
            view.setTextName(item.getCampName());
            view.setTextStart(item.getStartDay());
            view.setTextEnd(item.getEndDay());
            view.setTextPeople(item.getPeople());


            return view;
        }

    }




}