package com.example.campingapp;

import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class LoginSystem extends ViewModel {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDb;
    private FirebaseUser fUser;
    public boolean SignUpUser(String name, String email, String password,String business) {
        final boolean[] flag = {true};
        firebaseDb=FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    fUser=firebaseAuth.getCurrentUser();
                    String uid = fUser.getUid();
                    UserEntity user=new UserEntity(uid,email,name,business);
                    firebaseDb.child("user").child(uid).setValue(user);
                    return;
                }else{
                    flag[0] =false;
                    return;
                }
            }
        });
        return flag[0];
    }


    public FirebaseUser Login(String email,String password){
        final FirebaseUser currentUser;
        firebaseDb=FirebaseDatabase.getInstance("https://smu-se5-camping-default-rtdb.firebaseio.com/").getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                }else{

                }
            }
        });
        currentUser=firebaseAuth.getCurrentUser();
        return currentUser;
    }
}