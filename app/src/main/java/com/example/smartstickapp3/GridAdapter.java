package com.example.smartstickapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.PlantViewHolder> implements Filterable {

    Context context;
    List<UserPlants> pData;
    List<UserPlants> pDataFiltered;

    public GridAdapter(Context context, List<UserPlants> pData) {
        this.context = context;
        this.pData = pData;
        this.pDataFiltered = pData;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.rc_item_plants,parent,false);
        return new PlantViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));

        // bind data here
        holder.plantname.setText(pDataFiltered.get(position).getPlantName());
        holder.plantage.setText(pDataFiltered.get(position).getPlantAge());
        holder.planttype.setText(pDataFiltered.get(position).getPlantWaterDay());
    }

    @Override
    public int getItemCount() {
        return pDataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if(Key.isEmpty()){
                    pDataFiltered = pData;
                }
                else{
                    List<UserPlants> lstFiltered = new ArrayList<>();
                    for (UserPlants row : pData){
                        if(row.getPlantName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }
                    pDataFiltered = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = pDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                pDataFiltered = (List<UserPlants>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder{
        TextView plantname,plantage,planttype;
        RelativeLayout relativeLayout;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            /*tv_tittle = itemView.findViewById(R.id.tv_tittle);
            tv_context = itemView.findViewById(R.id.tv_content);
            img_logo = itemView.findViewById(R.id.imgV_ic_flower);*/
            relativeLayout = itemView.findViewById(R.id.relative_layout);

        }
    }

}
