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

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    private ArrayList<OrderedItem> orderedItems;
    public NestedAdapter(ArrayList<OrderedItem> orderedItems){
        this.orderedItems = orderedItems;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.name.setText(orderedItems.get(position).getFoodName());
        holder.qty.setText(orderedItems.get(position).getQty());
        holder.price.setText(orderedItems.get(position).getPrice());
        holder.result.setText(orderedItems.get(position).getResult());
    }

    @Override
    public int getItemCount() {
        return orderedItems.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView qty;
        private TextView price;
        private TextView result;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cart_food_name);
            qty = itemView.findViewById(R.id.cart_food_qty);
            price = itemView.findViewById(R.id.cart_food_price);
            result = itemView.findViewById(R.id.cart_result);
        }
    }
}
