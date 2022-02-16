package com.example.frb.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frb.R;
import com.example.frb.adapters.ExistingMenuEditorAdapter;
import com.example.frb.models.MenuItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExistingMenuEditorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExistingMenuEditorAdapter existingMenuEditorAdapter;
    private ArrayList<MenuItem> menuItems;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_menu_editor);

        recyclerView = findViewById(R.id.existing_menu_editor_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuItems = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://canteen-management-systems-19bce.asia-southeast1.firebasedatabase.app/").getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.child("Menu").getChildren()){
                    MenuItem menuItem = dataSnapshot.getValue(MenuItem.class);
                    menuItems.add(menuItem);
                }
                existingMenuEditorAdapter = new ExistingMenuEditorAdapter(getApplicationContext(), menuItems);
                recyclerView.setAdapter(existingMenuEditorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}