package com.example.campingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campingapp.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText editName;
    EditText editPassword;
    EditText editEmail;
    EditText editCheck;
    Button register;
    LoginSystem loginSystem = new LoginSystem();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActivitySignUpBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up);
        editName=(EditText)binding.userName;
        editPassword=(EditText) binding.userPassword;
        editEmail = (EditText) binding.userEmail;
        editCheck = (EditText) binding.passwordCheck;

        register=binding.registerButton;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String check = editCheck.getText().toString();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || check.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();

                }else if (!password.equals(check)){
                    Toast.makeText(SignUpActivity.this,"비밀번호가 같지 않습니다",Toast.LENGTH_SHORT).show();
                }else{
                    if (loginSystem.SignUpUser(name,email,password)){
                        Toast.makeText(SignUpActivity.this,"회원가입 성공",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(SignUpActivity.this,"회원가입 실패",Toast.LENGTH_SHORT).show();
                    }
                    onBackPressed();
                }

            }
        });


    }
}