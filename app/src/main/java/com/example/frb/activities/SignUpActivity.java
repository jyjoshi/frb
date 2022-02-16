package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frb.R;
import com.example.frb.models.UserDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    int check =1;
    private String entryPoint;
    EditText firstName;
    EditText lastName;
    EditText password;
    EditText confirmPassword;
    EditText phone;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://canteen-management-systems-19bce.asia-southeast1.firebasedatabase.app/");
    DatabaseReference dbRef = database.getReference();
    DatabaseReference reference = FirebaseDatabase.getInstance("https://canteen-management-systems-19bce.asia-southeast1.firebasedatabase.app/").getReference("Users");
    ArrayList<Integer> checker;
    ArrayList<UserDB> user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.editTextTextPersonName);
        lastName = findViewById(R.id.editTextTextPersonLastName2);
        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextPassword2);
        phone = findViewById(R.id.editTextPhone);
        checker = new ArrayList<>();
        user = new ArrayList<>();

        Intent intentIn = getIntent();
        Bundle b = intentIn.getExtras();

        if(b!=null){
            entryPoint =(String) b.get("entryPoint");
        }



    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    void checkDataEntered() {

        if (isEmpty(firstName)) {
            firstName.setError("First Name is required");
            check=0;
        }
        if (isEmpty(phone)) {
            phone.setError("Phone is required");
            check=0;
        }

        if (isEmpty(lastName)) {
            lastName.setError("Last name is required!");
            check=0;
        }
        if (isEmpty(password)){
            password.setError("Password can't be empty");
            check=0;
        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            password.setError("Passwords do not match");
            check=0;
        }

    }

    public void processRegistration(View view){
        Log.i("Log","Entered the function");
        check = 1;
        checkDataEntered();//no problem here

        if (check == 1) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int check = 1;
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        UserDB temp = snapshot1.getValue(UserDB.class);
                        if(temp.getPhone().equals(phone.getText().toString())){
                            check = 0;
                            phone.setError("Number is already registered.");
                        }
                    }
                    if(check==1){
                        UserDB user = new UserDB(
                                phone.getText().toString(),
                                firstName.getText().toString(),
                                lastName.getText().toString(),
                                password.getText().toString()
                        );

                        Intent intent = new Intent(getApplicationContext(), com.example.frb.activities.VerifyPhoneActivity.class);
                        intent.putExtra("requirement", "sign_up");
                        intent.putExtra("entryPoint", entryPoint);
                        intent.putExtra("phoneNo", phone.getText().toString());
                        intent.putExtra("firstName", user.getFirstName());
                        intent.putExtra("lastName", user.getLastName());
                        intent.putExtra("password", user.getPassword());
                        startActivity(intent);

                        /*dbRef.child("Users").child(phone.getText().toString()).setValue(user);

                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);*/
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }
}
