package com.example.campingapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ReviewSystem {
    DatabaseReference firebaseDb = FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();

    public void getReview(String campId,String userId,CallbackReview callbackReview){
        firebaseDb.child("review").child(campId).child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                WritingReviewEntity review = snapshot.getValue(WritingReviewEntity.class);
                callbackReview.onSuccess(review);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateReview(WritingReviewEntity review,Callback callback){
        String newContent = review.getCont();
        double newRating = review.getRating();
        HashMap<String,Object> updateReview = new HashMap<>();
        updateReview.put("cont",newContent);
        updateReview.put("rating",newRating);
        firebaseDb.child("review").child(review.getCid()).child(review.getUid()).updateChildren(updateReview).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseDb.child("camp").child(review.getCid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        CampingEntity camp = snapshot.getValue(CampingEntity.class);
                        HashMap<String,Object> updateCamp = new HashMap<>();
                        double campRating = ( (camp.getRating() * (camp.getReview()-1)) + newRating) / camp.getReview();
                        updateCamp.put("rating",campRating);
                        firebaseDb.child("camp").child(review.getCid()).updateChildren(updateCamp).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                callback.onSuccess();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void createReview(WritingReviewEntity review, Callback callback){

        firebaseDb.child("review").child(review.getCid()).child(review.getUid()).setValue(review).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseDb.child("camp").child(review.getCid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        CampingEntity camp = snapshot.getValue(CampingEntity.class);
                        int oldReview = camp.getReview();
                        double oldRating = camp.getRating();
                        int newReview = oldReview + 1;
                        double newRating = ( (oldRating* oldReview) + review.getRating() )/newReview;
                        HashMap<String,Object> update = new HashMap<>();
                        update.put("review",newReview);
                        update.put("rating",newRating);
                        firebaseDb.child("camp").child(review.getCid()).updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                callback.onSuccess();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    public void uploadReview(WritingReviewEntity review,Context context){
        firebaseDb.child("review").child(review.getCid()).child(review.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    updateReview(review, new Callback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(context,"리뷰가 수정되었습니다.",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(context,"리뷰 수정에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    createReview(review, new Callback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(context,"리뷰를 등록하였습니다.",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(context,"리뷰 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteReview(WritingReviewEntity review,Callback callback){
        if (review == null){
            callback.onFailure();
        }else {
            firebaseDb.child("review").child(review.getCid()).child(review.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        firebaseDb.child("review").child(review.getCid()).child(review.getUid()).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                firebaseDb.child("camp").child(review.getCid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        CampingEntity camp = snapshot.getValue(CampingEntity.class);
                                        int oldReview = camp.getReview();
                                        double oldRating = camp.getRating();
                                        int newReview = oldReview - 1;
                                        double newRating;
                                        if (newReview != 0) {
                                            newRating = ((oldRating * oldReview) - review.getRating()) / newReview;
                                        } else {
                                            newRating = 0;
                                        }
                                        HashMap<String, Object> update = new HashMap<>();
                                        update.put("review", newReview);
                                        update.put("rating", newRating);
                                        firebaseDb.child("camp").child(review.getCid()).updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                callback.onSuccess();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });
                    } else {
                        callback.onFailure();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}
