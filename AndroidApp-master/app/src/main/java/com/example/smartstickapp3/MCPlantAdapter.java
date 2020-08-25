package com.example.smartstickapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MCPlantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<SensorItem> list;
    private Context context;
    private final static int tip_firebase =0, tip_sensor=1;
    //ArrayList<FirebaseItem> mFirebaseDataList;
    //ArrayList<SensorItem> mSensorDataList;
    LayoutInflater inflater, inflater2;
    Button button;

    public MCPlantAdapter(Context context, List<SensorItem> list){
        inflater = LayoutInflater.from(context);
        this.list=list;
        this.context=context;
    }

    /*@Override
    public int getItemViewType(int position) {

        List içerisindeki gelen tipi yakalayabileceğimiz metot

        if (list.get(position) instanceof FirebaseItem) { // Tipler eşit ise true döner
            return tip_firebase;
        } else if (list.get(position) instanceof SensorItem) {
            return tip_sensor;
        }
        return -1;
    }*/

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // xml den java dönüştürmek istediğimiz layoutu veriyoruz.
        RecyclerView.ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_current_plant_cards, parent, false);
        viewHolder = new FirebaseViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Tıklanan öğeler SensorItem class tipinde nesnedir.
        int viewType = holder.getItemViewType();
        SensorItem firebaseItem = (SensorItem)list.get(position);
        ((FirebaseViewHolder)holder).setData(firebaseItem,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewData;
        TextView textViewDeger;
        ProgressBar mProgressBar;

        String currentValue=null;
        int minValue=0;
        int maxValue=0;
        int optimumValue=0;


        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewData = itemView.findViewById(R.id.textViewData);
            textViewDeger = itemView.findViewById(R.id.textViewDeger);
            mProgressBar= itemView.findViewById(R.id.progressBar);
        }

        public void setData(SensorItem item, int position) {
            this.mTextViewData.setText(item.getBitkiVeriTur());
            this.textViewDeger.setText(item.getS_bar_current());
            this.mProgressBar.setMax(item.getS_bar_max());
            //this.mProgressBar.setProgress(item.getProgressbar());
        }
    }


}
