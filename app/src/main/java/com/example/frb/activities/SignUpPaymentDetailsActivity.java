package com.example.frb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.frb.R;
import com.example.frb.models.UserDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpPaymentDetailsActivity extends AppCompatActivity {

    private int check = 1;

    private String canteenName;
    private String canteenPhone;
    private String canteenPassword;

    EditText editTextBankAccNo;
    EditText editTextAccHolderName;
    EditText editTextIFSCCode;
    EditText editTextRzpKey;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/");
    DatabaseReference reference = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference("Canteens");
    ArrayList<UserDB> canteens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_payment_details);

        editTextAccHolderName = findViewById(R.id.editTextAccHolderName);
        editTextBankAccNo = findViewById(R.id.editTextBankAccNo);
        editTextIFSCCode = findViewById(R.id.editTextIFSCCode);
        editTextRzpKey = findViewById(R.id.editTextRzpKey);

        Intent intentIn = getIntent();
        Bundle b = intentIn.getExtras();

    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered() {

        if (isEmpty(editTextAccHolderName)) {
            editTextAccHolderName.setError("Name is required");
            check=0;
        }
        if (isEmpty(editTextBankAccNo)) {
            editTextBankAccNo.setError("Phone is required");
            check=0;
        }
        if (isEmpty(editTextIFSCCode)){
            editTextIFSCCode.setText("IFSC Code is required");
        }

    }

    public void processRegistration(View view) {
    }
}