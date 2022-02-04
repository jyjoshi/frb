package com.example.frb.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.models.FoodQuantity;
import com.example.frb.models.MenuItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<com.example.frb.adapters.MenuAdapter.MenuViewHolder> {
    ArrayList<Integer> quantity;
    ArrayList<MenuItem> item;
    Context context;
    String phoneNumber;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("temp");


    public MenuAdapter(ArrayList<MenuItem> item, ArrayList<Integer> quantity, String phoneNumber) {
        this.item = item;
        this.quantity = quantity;
        this.phoneNumber = phoneNumber;
    }


    @NonNull

    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        context = parent.getContext();

        return new MenuViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
       // Glide.with(context).load(item.get(position).getImgUri()).into(holder.imgIcon);
        Picasso.get().load(Uri.parse(item.get(position).getImgUri())).into(holder.imgIcon);
        Log.i("Image","Image Check");
        Log.d(item.get(position).getName(), item.get(position).getImgUri());
        holder.getTextTitle().setText("Name : "+item.get(position).getName());
        holder.getTextDescription().setText("Desc : "+item.get(position).getDescription());
        holder.getTextPrice().setText("Price : "+item.get(position).getPrice());
        holder.getTextQuantity().setText(quantity.get(position).toString());

        holder.incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity.set(position, quantity.get(position)+1);
                holder.getTextQuantity().setText(quantity.get(position).toString());

                FoodQuantity temp = new FoodQuantity(item.get(position).getName(),quantity.get(position));
                root.child(phoneNumber).child(item.get(position).getName()).setValue(temp);
            }
        });

        holder.decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity.get(position)>0){
                    quantity.set(position, quantity.get(position)-1);
                    holder.getTextQuantity().setText(quantity.get(position).toString());

                    FoodQuantity temp = new FoodQuantity(item.get(position).getName(),quantity.get(position));
                    root.child(phoneNumber).child(item.get(position).getName()).setValue(temp);
                }
            }
        });






    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView textTitle;
        TextView textDescription;
        TextView textPrice;
        TextView textQuantity;
        Button incrementBtn;
        Button decrementBtn;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_icon);
            textTitle = itemView.findViewById(R.id.text_title);
            textDescription = itemView.findViewById(R.id.text_description);
            textPrice = itemView.findViewById(R.id.text_price);
            textQuantity = itemView.findViewById(R.id.text_quantity);
            incrementBtn = itemView.findViewById(R.id.add);
            decrementBtn = itemView.findViewById(R.id.sub);


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

        public TextView getTextQuantity() {
            return textQuantity;
        }
    }
}
