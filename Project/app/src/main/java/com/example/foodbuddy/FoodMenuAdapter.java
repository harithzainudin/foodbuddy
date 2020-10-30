package com.example.foodbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.FoodmenuViewHolder>{
    private Context foodContext;
    private List<FoodMenu> ffooddata;

    public FoodMenuAdapter(Context foodContext,List<FoodMenu> fooddata) {
        this.foodContext = foodContext;
        this.ffooddata = fooddata;
    }

    @NonNull
    @Override
    public FoodmenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(foodContext).inflate(R.layout.item_menu, parent, false);
        return new FoodmenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodmenuViewHolder holder, int position) {
        FoodMenu uploadCurrent = ffooddata.get(position);
        holder.textViewmenuname.setText(uploadCurrent.getFoodname());
        holder.textViewmenuprice.setText(uploadCurrent.getFoodprice());
        Picasso.get()
                .load(uploadCurrent.getFoodimage())
                // .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageViewmenufood);
    }

    @Override
    public int getItemCount() {
        return ffooddata.size();
    }

    public static class FoodmenuViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewmenuname;
        public TextView textViewmenuprice;
        public ImageView imageViewmenufood;
        LinearLayout cardViewlayout;

        public FoodmenuViewHolder(View itemView){
            super(itemView);
            textViewmenuname = itemView.findViewById(R.id.text_menu_name);
            textViewmenuprice = itemView.findViewById(R.id.text_menu_price);
            imageViewmenufood = itemView.findViewById(R.id.image_foodmenu);
            cardViewlayout = itemView.findViewById(R.id.menu_layouts);
        }
    }


}

