package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frb.R;
import com.example.frb.models.Bill;
import com.example.frb.models.UserDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //Variables to check data entry.
    int check=1;
    int check2 = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    EditText phone;
    EditText password;
    String firstName;
    String lastName;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//
        updateUI(currentUser);
    }

    private void initData() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference("Bill");
        int time = 100;
        int price = 150;
        int tId = 1001;
        String phone = "8169052664";
        int token = 501;
        for(int i = 0; i < 10; i++){
            String sTime = String.valueOf(time);
            String sPrice = String.valueOf(price);
            String sTId = String.valueOf(tId);

            Bill bill = new Bill(sTime, sPrice, sTId, phone, token);
            root.child(sTId).setValue(bill);

            FirebaseDatabase.getInstance().getReference().child("Status").child(sTId).setValue(Integer.toString(0));

            time++;
            price++;
            tId++;
            token++;
        }



    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null){
            String phoneNo = currentUser.getPhoneNumber().substring(3);
            Log.d("FirebaseAuth Phone", "Phone Number is " + phoneNo);
            FirebaseDatabase.getInstance().getReference("Users").child(phoneNo).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        UserDB temp = snapshot.getValue(UserDB.class);
                        firstName = temp.getFirstName();
                        lastName = temp.getLastName();
                    }

                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                        return;
                                    }
                                    FirebaseMessaging.getInstance().setAutoInitEnabled(true);

                                    // Get new FCM registration token
                                    String token = task.getResult();
                                    updateMessagingToken(phoneNo, token);

                                    // Log and toast
                                    Log.d("TAG", token);
                                    Toast.makeText(com.example.frb.activities.MainActivity.this, token, Toast.LENGTH_LONG).show();
                                }
                            });


//                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class); //Check getApplicationContext() ; I put it because it didn't give error.
//                    intent.putExtra("phone", phoneNo);
//                    intent.putExtra("firstName", firstName);
//                    intent.putExtra("lastName", lastName);
//                    startActivity(intent);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void updateMessagingToken(String phoneNumber, String messagingToken) {
        FirebaseDatabase.getInstance().getReference().child("MessageIds").child(phoneNumber).setValue(messagingToken);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    @Override
    public void onBackPressed() {
    }

    /**
     *Redirects new users to the sign-up page
     * @param view
     */
    public void toSignUp(View view){
        Intent intent = new Intent(getBaseContext(),SignUpActivity.class);
        intent.putExtra("entryPoint", "Main"); // Signifying that the user is a student.
        startActivity(intent);
    }


    /**
     * Validates the login credentials of users and redirects authorized users to the homepage.
     * @param view
     */
    public void toLogin(View view){
        checkDataEntered();
        String phoneNo = phone.getText().toString();
        if (check == 1) {
            if (phoneNo.equals("admin") && (password.getText().toString().equals("admin"))){
                startActivity(new Intent(view.getContext(), AdminHomeActivity.class));
            }
            else{
                dbRef.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                UserDB temp = userSnapshot.getValue(UserDB.class);
                                if (Objects.requireNonNull(temp).getPhone()!=null)
                                    if (temp.getPhone().equals(phoneNo)) {
                                        if (temp.getPassword().equals(password.getText().toString())) {
                                            check2 = 1;
                                            Intent intent = new Intent(view.getContext(), VerifyPhoneActivity.class);
                                            intent.putExtra("phoneNo", phoneNo);
                                            intent.putExtra("requirement", "login");
                                            startActivity(intent);
                                        }
                                        else {
                                            password.setError("Password Incorrect");
                                        }
                                        break;

                                    }
                            }

                            if(check2==1){

                            }
                            else{
                                phone.setError("Phone number is not registered.");
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
    }

    /**
     * Makes sure no fields are left empty
     */
    public void checkDataEntered() {
        check = 1;
        if (isEmpty(phone)){
            phone.setError("Enter phone number");
            check = 0;
        }
        if (isEmpty(password)){
            password.setError("Enter password");
            check = 0;
        }


    }

    /**
     * To check whether a particular field is empty or not.
     * @param text text present in the Text Field.
     * @return true if field is empty
     */
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public void toEnterPhone(View view) {
        startActivity(new Intent(this, EnterPhoneActivity.class));
    }

}