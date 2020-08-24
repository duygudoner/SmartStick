package com.example.smartstickapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mail;
    private EditText password;
    private Button enter;
    private Button newaccount;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUSer=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail=(EditText) findViewById(R.id.login_email);
        password=(EditText) findViewById(R.id.login_password);
        enter=(Button) findViewById(R.id.login_button);
        newaccount=(Button) findViewById(R.id.login_need_account);
        registerProgress=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        firebaseUSer=mAuth.getCurrentUser();

        /*if (firebaseUSer != null){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }*/

        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mail.getText().toString();
                String passw=password.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(passw)){
                    registerProgress.setTitle("Giriş yapılıyor...");
                    registerProgress.setMessage("Hesap getiriliyor, Lüften bekleyiniz.");
                    registerProgress.setCanceledOnTouchOutside(false);
                    registerProgress.show();
                    login_user(email,passw);
                } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(passw)){
                    Toast.makeText(getApplicationContext(),"Lütfen gerekli alanları doldurunuz.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login_user(String email, String passw) {
        mAuth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(LoginActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            // hata
                            Toast.makeText(getApplicationContext(),"Hata : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}
