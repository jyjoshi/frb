package com.example.frb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.frb.R;
import com.example.frb.models.BankDetails;
import com.example.frb.models.PaymentOptions;
import com.example.frb.models.ProfileDetails;
import com.example.frb.models.UserDB;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpPaymentDetailsActivity extends AppCompatActivity {

    private int check = 1;

    private String canteenName;
    private String canteenPhone;
    private String canteenPassword;

    private String bankAccNo;
    private String accHolderName;
    private String ifscCode;
    private String rzpKey;

    private EditText editTextBankAccNo;
    private EditText editTextAccHolderName;
    private EditText editTextIFSCCode;
    private EditText editTextRzpKey;

    private String requirement;
    private String entryPoint;

    private PaymentOptions paymentOptions;
    private BankDetails bankDetails;
    private ProfileDetails profileDetails;



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

        requirement = (String) b.get("requirement");
        entryPoint = (String) b.get("entryPoint");
        canteenName = (String) b.get("name");
        canteenPassword = (String) b.get("password");
        canteenPhone = (String) b.get("phoneNo");



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
        if (isEmpty(editTextRzpKey)){
            rzpKey = "null";
        }

    }

    public void processRegistration(View view) {
        check = 1;
        checkDataEntered();
        if (check == 1){
            bankAccNo = editTextBankAccNo.getText().toString();
            ifscCode = editTextIFSCCode.getText().toString();
            accHolderName = editTextAccHolderName.getText().toString();
            rzpKey = editTextRzpKey.getText().toString();



            profileDetails = new ProfileDetails(canteenName, canteenPassword, canteenPhone);
            bankDetails = new BankDetails(bankAccNo, ifscCode, accHolderName);

            paymentOptions = new PaymentOptions(bankDetails, rzpKey);

            Intent intent = new Intent(getApplicationContext(), com.example.frb.activities.VerifyPhoneActivity.class);
            intent.putExtra("phoneNo", canteenPhone);
            intent.putExtra("name", canteenName);
            intent.putExtra("password", canteenPassword);
            intent.putExtra("accNo", bankAccNo);
            intent.putExtra("holderName", accHolderName);
            intent.putExtra("ifsc", ifscCode);
            intent.putExtra("rzpKey", rzpKey);
            intent.putExtra("requirement", requirement);
            intent.putExtra("entryPoint", entryPoint);
//            intent.putExtra("profileDetails", (Parcelable) profileDetails);
//            intent.putExtra("paymentOptions", (Parcelable) paymentOptions);

            startActivity(intent);


        }
        else{

        }

    }
}