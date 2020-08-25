package com.example.smartstickapp3;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserPlantsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_plants);

        buildRecyclerView();
    }

    public void buildRecyclerView(){
        recyclerView = findViewById(R.id.user_plant_rc);
        recyclerView.setHasFixedSize(true);
        final UserPlantAdapter userPlantAdapter = new UserPlantAdapter(this,UserPlants.getData());

       /* GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(userPlantAdapter);
        */

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(userPlantAdapter);

    }
}
