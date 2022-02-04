package com.example.frb.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.activities.PreviousOrderItemsActivity;

import java.util.ArrayList;

public class PreviousOrdersAdapter extends RecyclerView.Adapter<com.example.frb.adapters.PreviousOrdersAdapter.MyViewHolder>{
    private ArrayList<String> token;
    private ArrayList<String> transactionId;
    private ArrayList<String> phone;
    private ArrayList<String> time;
    private ArrayList<String> amount;
    private Context context;
    ImageView imageView;
    public PreviousOrdersAdapter(Context context, ArrayList<String> transactionId, ArrayList<String> phone, ArrayList<String> time, ArrayList<String> amount, ArrayList<String> token){
        this.amount = amount;
        this.transactionId = transactionId;
        this.phone = phone;
        this.time = time;
        this.context = context;
        this.token = token;
    }
    @NonNull
    @Override
    public com.example.frb.adapters.PreviousOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getName().setText(token.get(position));
        holder.getQty().setText(String.valueOf(phone.get(position)));
        holder.getPrice().setText(String.valueOf(time.get(position)));
        holder.getamount().setText(String.valueOf(amount.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PreviousOrderItemsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("transactionId", transactionId.get(position));
                i.putExtra("phoneNo", phone.get(position));
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return transactionId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView qty;
        TextView price;
        TextView amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);




            name = itemView.findViewById(R.id.cart_food_name);
            qty = itemView.findViewById(R.id.cart_food_qty);
            price = itemView.findViewById(R.id.cart_food_price);
            amount = itemView.findViewById(R.id.cart_result);

        }

        public TextView getName() {
            return name;
        }

        public TextView getQty() {
            return qty;
        }

        public TextView getPrice() {
            return price;
        }
        public TextView getamount(){
            return amount;
        }
    }
}