package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frb.R;

public class AdminMenuEditOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_edit_options);
    }

    public void toMenuEditor(View view) {
        startActivity(new Intent(this, MenuEditorActivity.class));

    }

    public void toExistingMenuEditor(View view) {
        startActivity(new Intent(this, ExistingMenuEditorActivity.class));
    }
}