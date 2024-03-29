package com.example.frb.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.adapters.OuterAdapter;
import com.example.frb.adapters.ViewOrderAdapter;
import com.example.frb.models.Bill;
import com.example.frb.models.DataModel;
import com.example.frb.models.OrderedItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewOrderActivity extends AppCompatActivity {
    DatabaseReference dbref;

    private ArrayList<Bill> bills;
    private ArrayList<DataModel> dataModels;
    private ArrayList<OrderedItem> orderedItems;

    RecyclerView recyclerView;
    OuterAdapter outerAdapter;
    ViewOrderAdapter viewOrderAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order);

        bills = new ArrayList<>();
        orderedItems = new ArrayList<>();
        dataModels = new ArrayList<>();

        dbref = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference();

        //initDataset();
        Log.i("after initializing", String.valueOf((bills.size() )));

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.recyclerview_view_order);
        recyclerView.setLayoutManager(linearLayoutManager);
        /*viewOrderAdapter = new ViewOrderAdapter(transactionId, phone, time, amount);
        recyclerView.setAdapter(viewOrderAdapter);*/

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("Bill").getChildren()){

                    Bill temp = dataSnapshot.getValue(Bill.class);
                    orderedItems =new ArrayList<>();
                    for(DataSnapshot snapshot1 : snapshot.child("OrderedItems").child(temp.getTransactionId()).getChildren()){
                        orderedItems.add(snapshot1.getValue(OrderedItem.class));
                    }
                    bills.add(temp);

                    dataModels.add(new DataModel(temp, orderedItems));

                    outerAdapter = new OuterAdapter(com.example.frb.activities.AdminViewOrderActivity.this, dataModels);
                    viewOrderAdapter = new ViewOrderAdapter(com.example.frb.activities.AdminViewOrderActivity.this, bills);
                    recyclerView.setAdapter(outerAdapter);
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
        recyclerView.setAdapter(viewOrderAdapter);

    }

    public void initDataset()
    {
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Bill temp = dataSnapshot.getValue(Bill.class);

                    bills.add(temp);

                    Log.i("Check", "transaction"+temp.getTransactionId());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}