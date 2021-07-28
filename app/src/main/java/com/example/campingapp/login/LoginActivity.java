package com.example.campingapp.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.campingapp.interfaces.Callback;
import com.example.campingapp.home.MainActivity;
import com.example.campingapp.R;
import com.example.campingapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    Button signUp;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        signUp = binding.singUpButton;
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        login = binding.loginButton;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEdit.getText().toString();
                String password = binding.passwordEdit.getText().toString();
                LoginSystem loginSystem = new LoginSystem();
                loginSystem.Login(email, password, new Callback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(LoginActivity.this,"아이디가 없거나 비밀번호 오류입니다.",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}