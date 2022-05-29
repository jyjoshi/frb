package com.example.frb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frb.R;
import com.example.frb.models.BankDetails;
import com.example.frb.models.Canteen;
import com.example.frb.models.CanteenModel;
import com.example.frb.models.MenuItem;
import com.example.frb.models.OperationalVariables;
import com.example.frb.models.PaymentOptions;
import com.example.frb.models.ProfileDetails;
import com.example.frb.models.UserDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    Button verify_btn;
    EditText otp;
    ProgressBar progressBar;

    String phone;
    String name;
    String password;

    String requirement;
    String entryPoint;

    UserDB user;
    DatabaseReference reference;

    String canteenUid;

    FirebaseAuth mAuth;
    Boolean verificationOngoing = false;
    String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private String canteenPhone;
    private String canteenName;
    private String canteenPassword;
    private String bankAccNo;
    private String accHolderName;
    private String ifscCode;
    private String rzpKey;

    private ProfileDetails  profileDetails;
    private BankDetails bankDetails;
    private PaymentOptions paymentOptions;

    private CanteenModel canteenModel;

    private static final String TAG = "VerifyPhoneActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if(b!=null){
            phone = (String) b.get("phoneNo");
            requirement = (String) b.get("requirement");
            if(requirement.equals("sign_up")) {
                entryPoint = (String) b.get("entryPoint");

                canteenName = (String) b.get("name");
                canteenPassword = (String) b.get("password");

                bankAccNo = (String) b.get("accNo");
                accHolderName = (String) b.get("holderName");
                ifscCode = (String) b.get("ifsc");
                rzpKey = (String) b.get("rzpKey");
//                profileDetails = (ProfileDetails) b.get("profileDetails");
//                paymentOptions = (PaymentOptions) b.get("paymentOptions");

            }
        }


        verify_btn = findViewById(R.id.verify_phone_button);
        otp = findViewById(R.id.enter_phone_for_verification);
        progressBar = findViewById(R.id.progressBar_verify_phone);
        progressBar.setVisibility(View.INVISIBLE);
        reference = FirebaseDatabase.getInstance("https://canteen-management-systems-20a8c.asia-southeast1.firebasedatabase.app/").getReference();


        String phone = getIntent().getStringExtra("phoneNo");

//        sendVerificationCodeToUser(phone);

        Log.i("PhoneValue ", phone);
        mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);



        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otp.getText().toString();
                if(code.isEmpty()||code.length()<6){
                    otp.setError("Wrong OTP");
                    otp.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        });
    }

    private void sendVerificationCodeToUser(String phoneNo){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNo,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:" + credential);

            signIn(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
            Toast.makeText(com.example.frb.activities.VerifyPhoneActivity.this, "Verification failed. Please try again.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);

            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;

        }
    };

    private void verifyCode(String codeByUser){
        //Code change error
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, codeByUser);
            signIn(credential);
        }
        catch(Exception e){
            otp.setError("Wrong OTP");
            Toast.makeText(this, "Wrong OTP", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }



    }
    private void signIn(PhoneAuthCredential credential){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(com.example.frb.activities.VerifyPhoneActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Do what you want.
                    if(requirement.equals("sign_up")) {

                        canteenUid = reference.child("Canteens").push().getKey();

//                        Canteen canteen = new Canteen(phone, name, password);

                        profileDetails = new ProfileDetails(canteenName, canteenPassword, phone);
                        bankDetails = new BankDetails(bankAccNo, ifscCode, accHolderName);
                        paymentOptions = new PaymentOptions(bankDetails, rzpKey);

                        OperationalVariables operationalVariables = new OperationalVariables(1000, "null", true);

                        String menuItemUid = reference.child("Canteens").child(canteenUid).child("menuItems").push().getKey();
                        String menuItemUid1 = reference.child("Canteens").child(canteenUid).child("menuItems").push().getKey();

                        MenuItem menuItem = new MenuItem("", menuItemUid, "test_name", "test_description", String.valueOf(100));
                        MenuItem menuItem1 = new MenuItem("", menuItemUid1, "test_name_1", "test_description_1", String.valueOf(200));

                        ArrayList<MenuItem> menuItems = new ArrayList<>();
                        menuItems.add(menuItem);
                        menuItems.add(menuItem1);

                        canteenModel = new CanteenModel(canteenUid, menuItems, paymentOptions, profileDetails, operationalVariables);

                        reference.child("Canteens").child(canteenUid).setValue(canteenModel);

                        Toast.makeText(com.example.frb.activities.VerifyPhoneActivity.this, "Account successfully created!", Toast.LENGTH_SHORT).show();
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else if(requirement.equals("password_reset")){
                        Intent intent = new Intent(getApplicationContext(), PasswordResetActivity.class).putExtra("phoneNo",phone);
                        startActivity(intent);

                    }
                    else if(requirement.equals("login")){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(com.example.frb.activities.VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}