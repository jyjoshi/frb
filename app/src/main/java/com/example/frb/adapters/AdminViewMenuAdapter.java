package com.example.frb.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.models.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AdminViewMenuAdapter extends RecyclerView.Adapter<com.example.frb.adapters.AdminViewMenuAdapter.AdminViewMenuHolder> {
    ArrayList<MenuItem> item;
    Context context;


    public AdminViewMenuAdapter(ArrayList<MenuItem> item) {
        this.item = item;
    }


    @NonNull

    @Override
    public AdminViewMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout_admin, parent, false);
        context = parent.getContext();

        return new AdminViewMenuHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdminViewMenuHolder holder, int position) {
        // Glide.with(context).load(item.get(position).getImgUri()).into(holder.imgIcon);
        Picasso.get().load(Uri.parse(item.get(position).getImgUri())).into(holder.imgIcon);
        Log.i("Image","Image Check");
        holder.getTextTitle().setText("Name : "+item.get(position).getName());
        holder.getTextDescription().setText("Desc : "+item.get(position).getDescription());
        holder.getTextPrice().setText("Price : "+item.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class AdminViewMenuHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView textTitle;
        TextView textDescription;
        TextView textPrice;

        public AdminViewMenuHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            textTitle = itemView.findViewById(R.id.text_title);
            textDescription = itemView.findViewById(R.id.text_description);
            textPrice = itemView.findViewById(R.id.text_price);
        }

        public ImageView getImgIcon() {
            return imgIcon;
        }

        public TextView getTextTitle() {
            return textTitle;
        }

        public TextView getTextDescription() {
            return textDescription;
        }

        public TextView getTextPrice() {
            return textPrice;
        }

    }
}
