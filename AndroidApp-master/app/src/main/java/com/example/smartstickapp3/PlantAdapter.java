package com.example.smartstickapp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> implements Filterable {

    Context context;
    List<PlantItem> mData;
    List<PlantItem> mDataFiltered;

    public PlantAdapter(Context context, List<PlantItem> mData) {
        this.context = context;
        this.mData = mData;
        this.mDataFiltered = mData;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.rc_item_plants,parent,false);
        return new PlantViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        holder.img_logo.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation));
        holder.relativeLayout.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));

        // bind data here
        holder.tv_tittle.setText(mDataFiltered.get(position).getTitle());
        holder.tv_context.setText(mDataFiltered.get(position).getContex());
        holder.img_logo.setImageResource(mDataFiltered.get(position).getIc_logo());

    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String Key = constraint.toString();
                if(Key.isEmpty()){
                    mDataFiltered = mData;
                }
                else{
                    List<PlantItem> lstFiltered = new ArrayList<>();
                    for (PlantItem row : mData){
                        if(row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }
                    }
                    mDataFiltered = lstFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                mDataFiltered = (List<PlantItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tittle, tv_context;
        ImageView img_logo;
        RelativeLayout relativeLayout;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tittle = itemView.findViewById(R.id.tv_tittle);
            tv_context = itemView.findViewById(R.id.tv_content);
            img_logo = itemView.findViewById(R.id.imgV_ic_flower);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }

}
