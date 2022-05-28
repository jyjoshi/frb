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
import com.example.frb.models.Canteen;
import com.example.frb.models.UserDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignUpProfileDetailsActivity extends AppCompatActivity {
    int check =1;
    private String entryPoint;
    EditText name;
    EditText password;
    EditText confirmPassword;
    EditText phone;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/");
    DatabaseReference dbRef = database.getReference();
    DatabaseReference reference = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference("Canteens");
    ArrayList<UserDB> canteens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editTextCanteenName);
        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextPassword2);
        phone = findViewById(R.id.editTextPhone);
        canteens = new ArrayList<>();

        Intent intentIn = getIntent();
        Bundle b = intentIn.getExtras();

        if(b!=null){
            entryPoint = (String) b.get("entryPoint");
        }



    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    void checkDataEntered() {

        if (isEmpty(name)) {
            name.setError("First Name is required");
            check=0;
        }
        if (isEmpty(phone)) {
            phone.setError("Phone is required");
            check=0;
        }

        if (isEmpty(password)){
            password.setError("Password can't be empty");
            check=0;
        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString()))
        {
            password.setError("Passwords do not match");
            confirmPassword.setError("Passwords do not match");
            check=0;
        }

    }

    public void toSignUpPaymentDetails(View view){
        Log.i("Log","Entered the function");
        check = 1;
        checkDataEntered();//no problem here

        if (check == 1) {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int check = 1; // Check if an account with the given phone number already exists.
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        UserDB temp = snapshot1.getValue(UserDB.class);
                        if(temp.getPhone().equals(phone.getText().toString())){
                            check = 0;
                            phone.setError("Number is already registered.");
                        }
                    }
                    if(check==1){
                        Canteen canteen = new Canteen(
                                phone.getText().toString(),
                                name.getText().toString(),
                                password.getText().toString());

//                        Intent intent = new Intent(getApplicationContext(), com.example.frb.activities.VerifyPhoneActivity.class);
                        Intent intent = new Intent(getApplicationContext(), com.example.frb.activities.SignUpPaymentDetailsActivity.class);
                        intent.putExtra("requirement", "sign_up");
                        intent.putExtra("entryPoint", entryPoint);
                        intent.putExtra("phoneNo", canteen.getPhone());
                        intent.putExtra("name", canteen.getName());
                        intent.putExtra("password", canteen.getPassword());
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
