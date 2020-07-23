package com.example.smartstickapp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartstickapp3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddActivity extends AppCompatActivity {

    private EditText bitkiAdi;
    private EditText bitkiYas;
    private EditText bitkiTur;
    private Button bitkiKaydet;
    private ProgressDialog registerProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        bitkiAdi= (EditText) findViewById(R.id.edt_bitki_ad);
        bitkiYas= (EditText) findViewById(R.id.edt_bitki_yas);
        bitkiTur= (EditText) findViewById(R.id.edt_bitki_tur);
        bitkiKaydet= (Button) findViewById(R.id.btnKaydet);
        registerProgress=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();

        bitkiKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plantname=bitkiAdi.getText().toString();
                String plantage=bitkiYas.getText().toString();
                String planttype=bitkiTur.getText().toString();

                if(!TextUtils.isEmpty(plantname) || !TextUtils.isEmpty(plantage) || !TextUtils.isEmpty(planttype)){
                    registerProgress.setTitle("Kaydediliyor...");
                    registerProgress.setMessage("Bitki oluşturuluyor, Lüften bekleyiniz.");
                    registerProgress.setCanceledOnTouchOutside(false);
                    registerProgress.show();
                    register_plant(plantname,plantage,planttype);
                }
            }
        });
    }

    private void register_plant(String plantname, String plantage, String planttype) {

        String user_id=mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("UserPlants").child(user_id);
        HashMap<String,String> userMap=new HashMap<>();
        userMap.put("plantname",plantname);
        userMap.put("plantage",plantage);
        userMap.put("planttype",planttype);
        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    registerProgress.dismiss();
                    Intent mainIntent = new Intent(AddActivity.this, ShowActivity.class);
                    startActivity(mainIntent);
                }
            }
        });

        Intent i = new Intent(AddActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
