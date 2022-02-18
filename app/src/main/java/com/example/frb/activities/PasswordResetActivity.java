package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frb.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class PasswordResetActivity extends AppCompatActivity {
    private EditText password;
    private EditText confirmPassword;
    private String phoneNumber;
    private int check=1;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        
        Intent intentIn = getIntent();
        Bundle b = intentIn.getExtras();
        phoneNumber = (String) b.get("phoneNo");
        
        password = findViewById(R.id.enter_reset_password);
        confirmPassword = findViewById(R.id.confirm_reset_password);
        
        
        
        
        
        
        
    }
    private void checkDataEntered(){
        check=1;
        if(isEmpty(password)){
            check=0;
            password.setError("Enter password");
        }
        if(isEmpty(confirmPassword)){
            check = 0;
            confirmPassword.setError("Confirm the password");
        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            check=0;
            password.setError("Passwords do not match");
            confirmPassword.setError("Passwords do not match");
        }
    }
    private boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void resetPassword(View view) throws InterruptedException {
        checkDataEntered();
        if(check==1){
            FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference().child("Users").child(phoneNumber).child("password").setValue(password.getText().toString());
            Toast.makeText(this, "Password has been changed successfully", Toast.LENGTH_SHORT).show();
            TimeUnit.SECONDS.sleep(2);
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}