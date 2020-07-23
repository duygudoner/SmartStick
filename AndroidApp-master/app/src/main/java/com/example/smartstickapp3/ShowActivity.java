package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartstickapp3.R;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Button vericek;
        vericek=(Button) findViewById(R.id.data);

        vericek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ShowActivity.this,BluetoothScreen.class);
                startActivity(i);
                finish();
            }
        });

    }
}
