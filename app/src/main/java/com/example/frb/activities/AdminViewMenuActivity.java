package com.example.frb.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.adapters.AdminViewMenuAdapter;
import com.example.frb.models.MenuItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewMenuActivity extends AppCompatActivity {

    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbref = database.getReference("Menu");
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_menu);

        recyclerView = findViewById(R.id.adminMenuList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    menuItems.add(dataSnapshot.getValue(MenuItem.class));
                }
                AdminViewMenuAdapter adminViewMenuAdapter = new AdminViewMenuAdapter(menuItems);
                recyclerView.setAdapter(adminViewMenuAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}