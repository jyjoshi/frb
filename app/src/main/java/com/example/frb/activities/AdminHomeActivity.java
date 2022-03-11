package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frb.R;

public class AdminHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    public void toOrderList(View view) {
        startActivity(new Intent(view.getContext(), AdminOrderActivity.class));
    }

    public void toMenu(View view) {
        startActivity(new Intent(view.getContext(), com.example.frb.activities.AdminViewMenuActivity.class));
    }

    public void toAdminMenuEditOptions(View view) {
        startActivity(new Intent(view.getContext(), com.example.frb.activities.AdminMenuEditOptionsActivity.class));
    }

    public void toAdminPlaceOrder(View view) {
        startActivity(new Intent(view.getContext(), com.example.frb.activities.AdminPlaceOrderActivity.class));
    }

/*    public void toTeacherSignUp(View view) {
        Intent intent = new Intent(view.getContext(), SignUpActivity.class);
        intent.putExtra("entryPoint", "Admin");
        startActivity(intent);
    }*/
}
