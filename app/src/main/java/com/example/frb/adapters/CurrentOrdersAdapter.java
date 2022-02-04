package com.example.frb.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.activities.PreviousOrderItemsActivity;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CurrentOrdersAdapter extends RecyclerView.Adapter<com.example.frb.adapters.CurrentOrdersAdapter.MyViewHolder>{
    private ArrayList<String> token;
    private ArrayList<String> transactionId;
    private ArrayList<String> status;
    private ArrayList<String> time;
    private ArrayList<String> amount;
    private Context context;


    public CurrentOrdersAdapter(Context context, ArrayList<String> transactionId, ArrayList<String> status, ArrayList<String> time, ArrayList<String> amount, ArrayList<String> token){
        this.amount = amount;
        this.transactionId = transactionId;
        this.status = status;
        this.time = time;
        this.context = context;
        this.token = token;
    }
    @NonNull
    @Override
    public com.example.frb.adapters.CurrentOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_order_item_layout, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getToken().setText(token.get(position));
        holder.getTime().setText(String.valueOf(time.get(position)));
        holder.getAmount().setText(String.valueOf(amount.get(position)));

        if(status.get(position).equals("1")){
            holder.status_btn.setBackgroundResource(R.color.green);
            holder.status_btn.setText("R");
        }
        else{
            holder.status_btn.setBackgroundResource(R.color.red);
            holder.status_btn.setText("NR");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PreviousOrderItemsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("transactionId", transactionId.get(position));
                i.putExtra("statusNo", status.get(position));
                context.startActivity(i);
            }
        });
        holder.status_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "SetTextI18n"})//What is this
            @Override
            public void onClick(View v) {
                if(status.get(position).equals("0")){
                    holder.status_btn.setBackgroundResource(R.color.green);
                    holder.status_btn.setText("R");
                    FirebaseDatabase.getInstance().getReference().child("Status").child(transactionId.get(position)).setValue("1");
                    status.set(position, "1");
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("Status").child(transactionId.get(position)).removeValue();
                    int pos = position;
                    amount.remove(pos);
                    transactionId.remove(pos);
                    token.remove(pos);
                    time.remove(pos);
                    status.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, transactionId.size());
//                    holder.itemView.setVisibility(View.GONE);
                }


            }
        });
//        holder.status_btn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if(status.get(position).equals("1")){
//                    FirebaseDatabase.getInstance().getReference().child("Status").child(transactionId.get(position)).removeValue();
//                    amount.remove(position);
//                    transactionId.remove(position);
//                    token.remove(position);
//                    time.remove(position);
//                    status.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, transactionId.size());
//                    holder.itemView.setVisibility(View.GONE);
//                    return true;
//                }
//                return false;
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return transactionId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView token;
        TextView time;
        Button status_btn;
        TextView amount;

        public TextView getToken() {
            return token;
        }

        public TextView getTime() {
            return time;
        }


        public TextView getAmount() {
            return amount;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            token = itemView.findViewById(R.id.uid);
            time = itemView.findViewById(R.id.time);
            status_btn = itemView.findViewById(R.id.btn_status);
            amount = itemView.findViewById(R.id.amount);



        }

    }
}