package com.example.frb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.models.OrderedItem;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<com.example.frb.adapters.OrderItemsAdapter.MyViewHolder> {
    ArrayList<OrderedItem> orderedItems;
    public OrderItemsAdapter(ArrayList<OrderedItem> orderedItems){
        this.orderedItems = orderedItems;
    }

    @NonNull
    @Override
    public com.example.frb.adapters.OrderItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getFoodName().setText(orderedItems.get(position).getFoodName());
        holder.getPrice().setText(orderedItems.get(position).getPrice());
        holder.getQty().setText(orderedItems.get(position).getQty());
        holder.getResult().setText(orderedItems.get(position).getResult());
    }

    @Override
    public int getItemCount() {
        return orderedItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView foodName;
        private TextView qty;
        private TextView price;
        private TextView result;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.cart_food_name);
            qty = itemView.findViewById(R.id.cart_food_qty);
            price = itemView.findViewById(R.id.cart_food_price);
            result = itemView.findViewById(R.id.cart_result);
        }

        public TextView getFoodName() {
            return foodName;
        }

        public void setFoodName(TextView foodName) {
            this.foodName = foodName;
        }

        public TextView getQty() {
            return qty;
        }

        public void setQty(TextView qty) {
            this.qty = qty;
        }

        public TextView getPrice() {
            return price;
        }

        public void setPrice(TextView price) {
            this.price = price;
        }

        public TextView getResult() {
            return result;
        }

        public void setResult(TextView result) {
            this.result = result;
        }
    }


}
