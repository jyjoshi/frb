package com.example.frb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<com.example.frb.adapters.CartAdapter.MyViewHolder>{
    private ArrayList<String> names;
    private ArrayList<Integer> quantities;
    private ArrayList<Integer> prices;
    private ArrayList<Integer> result;
    private Context context;
    ImageView imageView;
    public CartAdapter( ArrayList<String> names,ArrayList<Integer> quantities, ArrayList<Integer> prices, ArrayList<Integer> result){
        this.result = result;
        this.names = names;
        this.quantities = quantities;
        this.prices = prices;
    }
    @NonNull
    @Override
    public com.example.frb.adapters.CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getName().setText(names.get(position));
        holder.getQty().setText(String.valueOf(quantities.get(position)));
        holder.getPrice().setText(String.valueOf(prices.get(position)));
        holder.getResult().setText(String.valueOf(result.get(position)));


    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView qty;
        TextView price;
        TextView result;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.cart_food_name);
            qty = itemView.findViewById(R.id.cart_food_qty);
            price = itemView.findViewById(R.id.cart_food_price);
            result = itemView.findViewById(R.id.cart_result);

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
        public TextView getResult(){
            return result;
        }
    }
}