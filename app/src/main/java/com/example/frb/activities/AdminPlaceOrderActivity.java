package com.example.frb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.frb.R;
import com.example.frb.adapters.MenuAdapter;
import com.example.frb.models.MenuItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AdminPlaceOrderActivity extends AppCompatActivity {

    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private ArrayList<Integer> quantity = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app//");
    private DatabaseReference dbref = database.getReference("Menu");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_place_order);
        RecyclerView menuList = findViewById(R.id.menuList);

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    menuItems.add(dataSnapshot.getValue(MenuItem.class));
                    quantity.add(0);
                    Log.d("List Size", String.valueOf(quantity.size()));

                    menuList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    MenuAdapter adapter = new MenuAdapter(menuItems, quantity, "100");
                    menuList.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void initDataset()
    {
    }

    public void goToCart(View view) {
        startActivity(new Intent(this, AdminCartActivity.class));
    }
}