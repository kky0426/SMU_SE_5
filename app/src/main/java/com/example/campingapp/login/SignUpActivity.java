package com.example.campingapp.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.campingapp.R;
import com.example.campingapp.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText editName;
    EditText editPassword;
    EditText editEmail;
    EditText editCheck;
    EditText editBusiness;
    RadioGroup radioGroup;
    RadioButton radioNormal;
    RadioButton radioBusiness;
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
        editBusiness = (EditText) binding.business;
        register=binding.registerButton;
        radioNormal = binding.radioNormal;
        radioBusiness= binding.radioBusiness;
        radioGroup = binding.radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_normal){
                    editBusiness.setEnabled(false);
                }else if (checkedId == R.id.radio_business){
                    editBusiness.setEnabled(true);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();
                String check = editCheck.getText().toString();
                String business = editBusiness.getText().toString();
                //빈칸이 있는 경우
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || check.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"빈칸을 채워주세요",Toast.LENGTH_SHORT).show();
                //비밀번호 != 비밀번호확인
                }else if (!password.equals(check)){
                    Toast.makeText(SignUpActivity.this,"비밀번호가 같지 않습니다",Toast.LENGTH_SHORT).show();
                }else if(password.length()<6){
                        Toast.makeText(SignUpActivity.this,"비밀번호를 6자 이상 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else {
                    if (radioNormal.isChecked()) {
                        if (loginSystem.SignUpUser(name, email, password,null)) {
                            Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                        onBackPressed();
                    }else if (radioBusiness.isChecked()){
                        if (business.isEmpty()){
                            Toast.makeText(SignUpActivity.this,"사업자 번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                        }else{
                            if(loginSystem.SignUpUser(name,email,password,business)){
                                Toast.makeText(SignUpActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                            }
                            onBackPressed();
                        }

                    }

                }

            }
        });


    }
}