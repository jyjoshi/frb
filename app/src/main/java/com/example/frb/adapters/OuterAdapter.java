package com.example.frb.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.models.Bill;
import com.example.frb.models.DataModel;
import com.example.frb.models.OrderedItem;

import java.util.ArrayList;

public class OuterAdapter extends RecyclerView.Adapter<OuterAdapter.ItemViewHolder> {

    private ArrayList<DataModel> mList;
    private ArrayList<OrderedItem> nestedList;
    private Context context;

//    public OuterAdapter(ArrayList<Bill> bills){
//        this.bills = bills;
//    }

    public OuterAdapter(Context context, ArrayList<DataModel> mList){
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//        Bill bill = bills.get(position);
        DataModel dataModel = mList.get(position);
        Bill bill = dataModel.getBill();
        holder.billToken.setText(bill.getToken().toString());
        holder.billPhone.setText(bill.getPhone());
        holder.billTime.setText(bill.getTime());
        holder.billAmount.setText(bill.getTotalPrice());

        boolean isExpandable = dataModel.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if(isExpandable){
            holder.mArrowImage.setImageResource(R.drawable.arrow_up);
        }
        else{
            holder.mArrowImage.setImageResource(R.drawable.arrow_down);
        }

        NestedAdapter nestedAdapter = new NestedAdapter(this.context, nestedList);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(nestedAdapter);


        holder.linearLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dataModel.setExpandable(!dataModel.isExpandable());
                nestedList = dataModel.getNestedList();
                nestedList.add(0, new OrderedItem("Name", "Qty", "Price", "Result"));
                notifyItemChanged(holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private RelativeLayout expandableLayout;
        private TextView billToken;
        private TextView billPhone;
        private TextView billTime;
        private TextView billAmount;
        private ImageView mArrowImage;
        private RecyclerView nestedRecyclerView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            billToken = itemView.findViewById(R.id.bill_token);
            billPhone = itemView.findViewById(R.id.bill_phone);
            billTime = itemView.findViewById(R.id.bill_time);
            billAmount = itemView.findViewById(R.id.bill_amount);
            mArrowImage = itemView.findViewById(R.id.arro_imageview);
            nestedRecyclerView = itemView.findViewById(R.id.child_rv);

        }
    }
}
