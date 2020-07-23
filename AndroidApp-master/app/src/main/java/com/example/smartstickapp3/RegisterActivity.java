package com.example.smartstickapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText mail;
    private EditText password;
    private EditText password2;
    private Button register;
    private TextView existaccount;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=(EditText) findViewById(R.id.register_user);
        mail=(EditText) findViewById(R.id.register_email);
        password=(EditText) findViewById(R.id.register_password);
        password2=(EditText) findViewById(R.id.register_2password);
        register=(Button) findViewById(R.id.register_button);
        existaccount=(TextView) findViewById(R.id.register_have_account);
        registerProgress=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        existaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String passw=password.getText().toString();
                String passw2=password2.getText().toString();
                String email=mail.getText().toString();
                if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(passw) || !TextUtils.isEmpty(passw2) || !TextUtils.isEmpty(email)){
                        registerProgress.setTitle("Kaydediliyor...");
                        registerProgress.setMessage("Hesap oluşturuluyor, Lüften bekleyiniz.");
                        registerProgress.setCanceledOnTouchOutside(false);
                        registerProgress.show();
                        register_user(name, passw, email);
                }
            }
        });
    }

    private void register_user(final String name,final String passw,final String email) {
        mAuth.createUserWithEmailAndPassword(email, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String user_id=mAuth.getCurrentUser().getUid();
                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                    HashMap<String,String> userMap=new HashMap<>();
                    userMap.put("name",name);
                    userMap.put("passw",passw);
                    userMap.put("email",email);
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                registerProgress.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(mainIntent);
                            }
                        }
                    });
                } else {
                    registerProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Hata:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
