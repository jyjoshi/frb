package com.example.frb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.models.OrderedItem;

import java.util.ArrayList;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    private ArrayList<OrderedItem> orderedItems;
    private Context context;
    public NestedAdapter(Context context, ArrayList<OrderedItem> orderedItems){
        this.context = context;
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
        if(position == 0){
            int red = ContextCompat.getColor(context,R.color.red);
            holder.name.setTextColor(red);
            holder.qty.setTextColor(red);
            holder.price.setTextColor(red);
            holder.result.setTextColor(red);
        }

        holder.name.setText(orderedItems.get(position).getFoodName());
        holder.qty.setText(orderedItems.get(position).getQty());
        holder.price.setText(orderedItems.get(position).getPrice());
        holder.result.setText(orderedItems.get(position).getResult());
    }

    @Override
    public int getItemCount() {
        if(orderedItems == null) return 0;
        else return orderedItems.size();
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
