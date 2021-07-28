package com.example.campingapp.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.campingapp.interfaces.Callback;
import com.example.campingapp.interfaces.CallbackData;
import com.example.campingapp.interfaces.CallbackReview;
import com.example.campingapp.R;
import com.example.campingapp.review.ReviewSystem;
import com.example.campingapp.review.WritingReview;
import com.example.campingapp.review.WritingReviewEntity;
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
import java.util.ArrayList;

public class ConfirmReservationActivity extends AppCompatActivity  {
    private FirebaseAuth auth;
    private FirebaseUser user;
    ReservationSystem reservationSystem;
    ReviewSystem reviewSystem;
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
        reservationSystem.printReservation(new CallbackData() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                LocalDate current = LocalDate.now();

                //LocalDate current = new LocalDate("2021",06,25);
                for (DataSnapshot dSnap1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot dSnap2 : dSnap1.getChildren()) {
                        if (dSnap2.getKey().equals(userId)) {
                            ReservationEntity reservation = dSnap2.getValue(ReservationEntity.class);
                            LocalDate time = LocalDate.parse(reservation.getEndDay(), DateTimeFormatter.ISO_DATE);
                            if (time.isBefore(current)) {
                                //예약종료일이 오늘날짜 전
                                System.out.println(reservation.getEndDay());
                                usedAdapter.addItem(reservation);
                            } else {
                                //아직 예약종료일이 끝나기 전

                                confirmAdapter.addItem(reservation);
                            }

                        }
                    }
                }
                usedList.setAdapter(usedAdapter);
                confirmList.setAdapter(confirmAdapter);
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
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ConfirmReservationActivity.this);
                    dialog.setMessage("삭제하시겠습니까?");
                    dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reservationSystem.cancelReservation(item, new Callback() {
                                @Override
                                public void onSuccess() {
                                    items.remove(position);
                                    confirmAdapter.items = items;
                                    Toast.makeText(ConfirmReservationActivity.this,"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                    confirmAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(ConfirmReservationActivity.this,"오류 발생",Toast.LENGTH_SHORT).show();
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
                    Intent intent1=new Intent(ConfirmReservationActivity.this, WritingReview.class);
                    intent1.putExtra("campid",item.getCampId());
                    startActivity(intent1);

                }
            });
            Button delete_review = (Button) view.findViewById(R.id.delete_review);
            delete_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ConfirmReservationActivity.this);
                    dialog.setMessage("리뷰를 삭제하시겠습니까?");
                    dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           reviewSystem = new ReviewSystem();
                           reviewSystem.getReview(item.getCampId(), item.getUserId(), new CallbackReview() {
                               @Override
                               public void onSuccess(WritingReviewEntity review) {
                                   reviewSystem.deleteReview(review, new Callback() {
                                       @Override
                                       public void onSuccess() {
                                            Toast.makeText(ConfirmReservationActivity.this,"리뷰를 삭제했습니다.",Toast.LENGTH_SHORT).show();
                                       }

                                       @Override
                                       public void onFailure() {
                                            Toast.makeText(ConfirmReservationActivity.this,"등록된 리뷰가 없습니다.",Toast.LENGTH_SHORT).show();
                                       }
                                   });
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


