package com.example.smartstickapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserPlantsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private List<UserPlants> pData;
    private Button bitkiGoruntule;
    private RecyclerView bitkiler;
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_plants);

        pData = new ArrayList<>();
        bitkiGoruntule = (Button) findViewById(R.id.button_plant);
        bitkiler = (RecyclerView) findViewById(R.id.user_plant_rc);
        gridAdapter = new GridAdapter(this, pData);

        bitkiGoruntule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitkiler.setAdapter(gridAdapter);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserPlants bitki = snapshot.getValue(UserPlants.class);
                    pData.add(
                            new UserPlants(
                                    bitki.getPlantName(),
                                    bitki.getPlantAge(),
                                    bitki.getPlantType()
                                    )
                    );
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}

        });
    }
}
