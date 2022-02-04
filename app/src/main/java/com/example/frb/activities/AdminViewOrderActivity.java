package com.example.frb.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.adapters.ViewOrderAdapter;
import com.example.frb.models.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewOrderActivity extends AppCompatActivity {
    DatabaseReference dbref;
    private ArrayList<String> transactionId;
    private ArrayList<String> phone;
    private ArrayList<String> time;
    private ArrayList<String> amount;
    private ArrayList<String> token;
    RecyclerView recyclerView;
    ViewOrderAdapter viewOrderAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_order);

        token = new ArrayList<>();
        transactionId = new ArrayList<>();
        phone = new ArrayList<>();
        time = new ArrayList<>();
        amount = new ArrayList<>();

        dbref = FirebaseDatabase.getInstance().getReference("Bill");

        //initDataset();
        Log.i("after initializing", String.valueOf((transactionId.size() )));

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
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Bill temp = dataSnapshot.getValue(Bill.class);
                    transactionId.add(temp.getTransactionId());
                    phone.add(temp.getPhone());
                    amount.add(temp.getTotalPrice());
                    time.add(temp.getTime());
                    token.add(temp.getToken().toString());
                    Log.i("Check", "transaction"+transactionId);
                    viewOrderAdapter = new ViewOrderAdapter(com.example.frb.activities.AdminViewOrderActivity.this, transactionId, phone, time, amount, token);
                    recyclerView.setAdapter(viewOrderAdapter);
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
                    transactionId.add(temp.getTransactionId());
                    phone.add(temp.getPhone());
                    amount.add(temp.getTotalPrice());
                    time.add(temp.getTime());
                    Log.i("Check", "transaction"+transactionId);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}