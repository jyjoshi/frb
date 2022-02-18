package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.adapters.OrderItemsAdapter;
import com.example.frb.models.OrderedItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewOrderItemsActivity extends AppCompatActivity {

    private String transactionId;
    private ArrayList<OrderedItem> orderedItems;
    private DatabaseReference dbref;

    private RecyclerView recyclerView;
    private OrderItemsAdapter orderItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_view_order_items);

        orderedItems = new ArrayList<>();
        dbref = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference().child("OrderedItems");
        recyclerView = findViewById(R.id.recyclerview_view_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intentIn = getIntent();
        Bundle b = intentIn.getExtras();
        if (b!=null){
            Log.i("getextra","no null");
            transactionId = (String) b.get("transactionId");
            Log.i("tid", transactionId);

        }
        //recyclerView.setAdapter(new OrderItemsAdapter(orderedItems));

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    OrderedItem orderedItem = snapshot1.getValue(OrderedItem.class);
                    Log.i("tvalue", orderedItem.getTransactionId());
                    if(orderedItem.getTransactionId().equals(transactionId)){
                        Log.i("innside", "items with thransaction id spotted");
                        orderedItems.add(orderedItem);
                        orderItemsAdapter = new OrderItemsAdapter(orderedItems);
                        recyclerView.setAdapter(orderItemsAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(orderItemsAdapter);

    }
}