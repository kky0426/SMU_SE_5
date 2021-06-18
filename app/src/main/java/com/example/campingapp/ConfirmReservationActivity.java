package com.example.campingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;

public class ConfirmReservationActivity extends AppCompatActivity  {
    private FirebaseAuth auth;
    private FirebaseUser user;
    ReservationSystem reservationSystem;
    String userId;
    ReserveItemAdapter confirmAdapter;
    UsedItemAdapter usedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_reservation);
        reservationSystem = ViewModelProviders.of(this).get(ReservationSystem.class);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        ListView confirmList = (ListView) findViewById(R.id.confirm_list);
        ListView usedList = (ListView) findViewById(R.id.used_list);
        confirmAdapter = new ReserveItemAdapter();
        usedAdapter = new UsedItemAdapter();
        reservationSystem.printReservation(new Callback() {

            @Override
            public void onSuceess(DataSnapshot dataSnapshot) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                LocalDate current = LocalDate.now();

                //LocalDate current = new LocalDate("2021",06,25);
                for (DataSnapshot dSnap1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dSnap2 : dSnap1.getChildren()) {
                        if (dSnap2.getKey().equals(userId)) {
                            ReservationEntity reservation = dSnap2.getValue(ReservationEntity.class);
                            LocalDate time = LocalDate.parse(reservation.getEndDay(), DateTimeFormatter.ISO_DATE);
                            if (time.isBefore(current)){
                                //예약종료일이 오늘날짜 전
                                System.out.println(reservation.getEndDay());
                                usedAdapter.addItem(reservation);
                            }else{
                                //아직 예약종료일이 끝나기 전

                                confirmAdapter.addItem(reservation);
                            }

                        }
                    }
                }
                usedList.setAdapter(usedAdapter);
                confirmList.setAdapter(confirmAdapter);
            }

            @Override
            public void onSuceess() {

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
            Button goCamp_btn = (Button) view.findViewById(R.id.goto_campInfo);
            goCamp_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference firebaseDb= FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
                    StorageReference storage= FirebaseStorage.getInstance().getReferenceFromUrl("gs://smu-se5-camping.appspot.com");
                    firebaseDb.child("camp").child(item.getCampId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            storage.child("campImage").child(item.getCampId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Intent intent = new Intent(ConfirmReservationActivity.this, CampInformationActivity.class);
                                    CampingEntity camp = snapshot.getValue(CampingEntity.class);
                                    camp.setPhotoUri(uri.toString());
                                    intent.putExtra("CampingEntity",camp);
                                    startActivity(intent);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
            Button cancel_btn = (Button) view.findViewById(R.id.reserve_cancel);
            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reservationSystem.cancelReservation(item,userId);
                    items.remove(position);
                    confirmAdapter.items = items;

                }

            });
            return view;
        }

    }

    class UsedItemAdapter extends BaseAdapter {
        ArrayList<ReservationEntity> items = new ArrayList<ReservationEntity>();

        public void addItem(ReservationEntity reservation){
            items.add(reservation);
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
            UsedItemView view = null;

            if (convertView == null) {
                view = new UsedItemView(ConfirmReservationActivity.this);
            } else {
                view = (UsedItemView) convertView;
            }
            ReservationEntity item = items.get(position);
            view.setTextName(item.getCampName());
            view.setTextStart(item.getStartDay());
            view.setTextEnd(item.getEndDay());
            view.setTextPeople(item.getPeople());
            Button revwrbtn=view.findViewById(R.id.write_review);
            revwrbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1=new Intent(ConfirmReservationActivity.this,WritingReview.class);
                    intent1.putExtra("campid",item.getCampId());
                    startActivity(intent1);

                }
            });
            return view;
        }
    }
}


