package com.example.smartstickapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MCPlantAdapter extends RecyclerView.Adapter<MCPlantAdapter.MyViewHolder> {

    ArrayList<SensorItem> mDataList;
    LayoutInflater inflater, inflater2;

    public MCPlantAdapter(Context context, ArrayList<SensorItem> data){
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    public MCPlantAdapter(Runnable runnable, ArrayList<SensorItem> data2){
        inflater2 = LayoutInflater.from((Context) runnable);
        this.mDataList = data2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // xml den java dönüştürmek istediğimiz layoutu veriyoruz.
        switch (viewType) {
            case 0:
                View v = inflater.inflate(R.layout.my_current_plant_cards, parent, false);
                MyViewHolder holder = new MyViewHolder(v);
                return holder;
            case 1:
                View v2 = inflater2.inflate(R.layout.my_current_plant_cards, parent, false);
                MyViewHolder holder2 = new MyViewHolder(v2);
                return holder2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Tıklanan öğeler SensorItem class tipinde nesnedir.
        SensorItem tiklanilanSensorItem = mDataList.get(position);
        holder.setData(tiklanilanSensorItem,position);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDeger;
        TextView mTextViewData;
        SeekBar mSeekbar;

        int minValue=0;
        int maxValue=0;
        int currentValue=0;
        int optimumValue=0;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewData = itemView.findViewById(R.id.textViewData);
            mSeekbar = itemView.findViewById(R.id.seek_bar);
            textViewDeger = itemView.findViewById(R.id.textViewDeger);
        }

        public void setData(SensorItem tiklanilanSensorItem, int position) {
            this.mTextViewData.setText(tiklanilanSensorItem.getBitkiVeriTur());
            this.textViewDeger.setText(tiklanilanSensorItem.getS_bar_current());
            //.................
        }
    }
}
