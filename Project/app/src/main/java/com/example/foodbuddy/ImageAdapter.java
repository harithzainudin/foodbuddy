package com.example.foodbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{

    private Context mContext;
    private List<UploadImageAdapter> mUploads;

    public ImageAdapter(Context context, List<UploadImageAdapter> uploads) {
        mContext = context;
        mUploads = uploads;
        // this list is needed for search function. nanti akan search apa2 dalam ni
        List<UploadImageAdapter> mUploadssearch = new ArrayList<>(uploads); //for search,
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final UploadImageAdapter uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textmenuname.setText(uploadCurrent.getmenuname());
        holder.textmenuprice.setText(uploadCurrent.getmenuprice());

        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

        //click sini, akan pegi ke page satulagi. yang ada nama kedai, kalau tekan kedai iqah, akan keluar nama iqah
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,ListOfFoodInShopActivity.class);

                //hantar nama kedai ke page menu list
                i.putExtra("name", uploadCurrent.getName());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public void filteredList(List<UploadImageAdapter> filterList) {
        mUploads = filterList;
        notifyDataSetChanged();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textmenuname;
        public TextView textmenuprice;
        public ImageView imageView;;
        LinearLayout relativeLayout;
        View v;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textmenuname = itemView.findViewById(R.id.text_menu_name);
            textmenuprice = itemView.findViewById(R.id.text_menu_price);
            imageView = itemView.findViewById(R.id.image_view_upload);
            relativeLayout = itemView.findViewById(R.id.parent_layouts);

        }
    }
}
