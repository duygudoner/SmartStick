package com.example.smartstickapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserPlantAdapter extends RecyclerView.Adapter<UserPlantAdapter.MyViewHolder>{

    ArrayList<UserPlants> mDataList;
    LayoutInflater inflater;

    public UserPlantAdapter(Context context, ArrayList<UserPlants> data){
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.my_user_plant_cards,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserPlants tiklananPlant = mDataList.get(position);
        holder.setData(tiklananPlant,position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView ;
        TextView tv1;
        TextView tv2;
        TextView tv3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_plant_image);
            tv1 = itemView.findViewById(R.id.edt_plant_name);
            tv2 = itemView.findViewById(R.id.edt_plant_age);
            tv3 = itemView.findViewById(R.id.edt_plant_water_day);
        }

        public void setData(UserPlants tiklananPlant, int position) {
            this.imageView.setImageResource(tiklananPlant.getPlantImageId());
            this.tv1.setText(tiklananPlant.getPlantName());
            this.tv2.setText(tiklananPlant.getPlantAge());
            this.tv3.setText(tiklananPlant.getPlantWaterDay());
        }
    }

}
