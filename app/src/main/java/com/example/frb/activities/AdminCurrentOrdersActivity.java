package com.example.frb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.frb.R;
import com.example.frb.adapters.CurrentOrdersAdapter;
import com.example.frb.models.Bill;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AdminCurrentOrdersActivity extends AppCompatActivity {
    DatabaseReference dbref;
    private ArrayList<String> transactionId;
    private ArrayList<String> time;
    private ArrayList<String> amount;
    private ArrayList<String> token;
    private ArrayList<String> status;

    private Timer autoUpdate;

    RecyclerView recyclerView;
    CurrentOrdersAdapter currentOrdersAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_current_orders);

/*        token = new ArrayList<>();
        transactionId = new ArrayList<>();
//        phone = new ArrayList<>();
        time = new ArrayList<>();
        amount = new ArrayList<>();
        status = new ArrayList<>();*/


        dbref = FirebaseDatabase.getInstance("https://canteen-management-systems-19bce.asia-southeast1.firebasedatabase.app/").getReference();

        //initDataset();
//        Log.i("after initializing", String.valueOf((transactionId.size() )));

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.recyclerview_view_order);
        recyclerView.setLayoutManager(linearLayoutManager);
        /*CurrentOrdersAdapter = new CurrentOrdersAdapter(transactionId, phone, time, amount);
        recyclerView.setAdapter(CurrentOrdersAdapter);*/

        /*dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("Status").getChildren()){
//                    Bill temp = dataSnapshot.getValue(Bill.class);
                    String id = dataSnapshot.getKey();
                    transactionId.add(id);
                    String statusUpdate = dataSnapshot.getValue(String.class);
                    Log.i("Logger", statusUpdate);
                    status.add(statusUpdate);

                    Bill bill = snapshot.child("Bill").child(id).getValue(Bill.class);
                    token.add(bill.getToken().toString());
                    time.add(bill.getTime());
                    amount.add(bill.getTotalPrice());

                    Log.i("Check", "transaction"+transactionId);

                }
                currentOrdersAdapter = new CurrentOrdersAdapter(AdminCurrentOrdersActivity.this, transactionId, status, time, amount, token);
                recyclerView.setAdapter(currentOrdersAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        updateHTML();
                    }
                });
            }
        }, 0, 10000);
        recyclerView.setAdapter(currentOrdersAdapter);

    }

    private void updateHTML() {
        token = new ArrayList<>();
        transactionId = new ArrayList<>();
//        phone = new ArrayList<>();
        time = new ArrayList<>();
        amount = new ArrayList<>();
        status = new ArrayList<>();

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("Status").getChildren()){
//                    Bill temp = dataSnapshot.getValue(Bill.class);
                    String id = dataSnapshot.getKey();
                    transactionId.add(id);
                    String statusUpdate = dataSnapshot.getValue(String.class);
                    Log.i("Logger", statusUpdate);
                    status.add(statusUpdate);

                    Bill bill = snapshot.child("Bill").child(id).getValue(Bill.class);
                    token.add(bill.getToken().toString());
                    time.add(bill.getTime());
                    amount.add(bill.getTotalPrice());

                    Log.i("Check", "transaction"+transactionId);

                }
                currentOrdersAdapter = new CurrentOrdersAdapter(AdminCurrentOrdersActivity.this, transactionId, status, time, amount, token);
                recyclerView.setAdapter(currentOrdersAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        token = new ArrayList<>();
        transactionId = new ArrayList<>();
//        phone = new ArrayList<>();
        time = new ArrayList<>();
        amount = new ArrayList<>();
        status = new ArrayList<>();
        currentOrdersAdapter = new CurrentOrdersAdapter(AdminCurrentOrdersActivity.this, transactionId, status, time, amount, token);
        recyclerView.setAdapter(currentOrdersAdapter);

    }
}