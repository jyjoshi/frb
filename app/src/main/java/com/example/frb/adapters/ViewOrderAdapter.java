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
import com.example.frb.activities.AdminViewOrderItemsActivity;
import com.example.frb.models.Bill;

import java.util.ArrayList;

public class ViewOrderAdapter extends RecyclerView.Adapter<com.example.frb.adapters.ViewOrderAdapter.MyViewHolder>{

    private ArrayList<Bill> bills;
    private Context context;
    ImageView imageView;

    public ViewOrderAdapter(Context context, ArrayList<Bill> bills){
        this.context = context;
        this.bills = bills;
    }
    @NonNull
    @Override
    public com.example.frb.adapters.ViewOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getName().setText((bills.get(position).getToken().toString()));
        holder.getQty().setText(String.valueOf(bills.get(position).getPhone()));
        holder.getPrice().setText(String.valueOf(bills.get(position).getTime()));
        holder.getamount().setText(String.valueOf(bills.get(position).getTotalPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdminViewOrderItemsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("transactionId", bills.get(position).getTransactionId());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bills.size();
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