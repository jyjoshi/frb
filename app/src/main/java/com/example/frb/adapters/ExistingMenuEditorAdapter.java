package com.example.frb.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.activities.EditFoodActivity;
import com.example.frb.models.MenuItem;

import java.util.ArrayList;

public class ExistingMenuEditorAdapter extends RecyclerView.Adapter<com.example.frb.adapters.ExistingMenuEditorAdapter.MyViewHolder>{
    private ArrayList<MenuItem> menuItems;
    Context context;

    public ExistingMenuEditorAdapter(Context context, ArrayList<MenuItem> menuItems){
        this.menuItems = menuItems;
        this.context = context;
    }

    @NonNull
    @Override
    public com.example.frb.adapters.ExistingMenuEditorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_2_columns, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.getFoodName().setText(menuItems.get(position).getName());
        holder.getPrice().setText( menuItems.get(position).getPrice());

    }


    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView foodName;
        private TextView price;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            foodName = itemView.findViewById(R.id.cart_food_name);
            price = itemView.findViewById(R.id.cart_food_qty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditFoodActivity.class);
                    intent.putExtra("name", menuItems.get(getAdapterPosition()).getName());
                    intent.putExtra("uri", menuItems.get(getAdapterPosition()).getImgUri());
                    intent.putExtra("desc", menuItems.get(getAdapterPosition()).getDescription());
                    intent.putExtra("price", menuItems.get(getAdapterPosition()).getPrice());
                    intent.putExtra("uid", menuItems.get(getAdapterPosition()).getUid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

    }

        public TextView getFoodName() {
            return foodName;
        }

        public TextView getPrice() {
            return price;
        }
    }
}
