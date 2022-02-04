package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frb.R;

public class AdminOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);
    }

    public void toCurrentOrders(View view) {
        startActivity(new Intent(view.getContext(), com.example.frb.activities.AdminCurrentOrdersActivity.class));
    }

    public void toAllOrder(View view) {
        startActivity(new Intent(view.getContext(), com.example.frb.activities.AdminViewOrderActivity.class));
    }
}