package com.example.foodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.internal.bind.ObjectTypeAdapter;

import java.util.HashMap;
import java.util.Map;

public class activity_create_account2 extends AppCompatActivity {
    Button buttonCreateAccount;
    TextView textViewBack;
    TextInputEditText textInputAddress, textInputPhoneNumber, textInputMatrix;
    ProgressBar progressBar;
    String name, email, password, confirmPassword,phoneNumber, address, matrix, userID;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseDatabase fDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);

        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        textViewBack        = findViewById(R.id.textViewBack);
        progressBar         = findViewById(R.id.progressBar);
        textInputPhoneNumber= findViewById(R.id.text_input_phone_number);
        textInputAddress    = findViewById(R.id.text_input_address);
        textInputMatrix     = findViewById(R.id.text_input_matrix);

        name            = getIntent().getStringExtra("Name");
        email           = getIntent().getStringExtra("Email");
        password        = getIntent().getStringExtra("Password");
        confirmPassword = getIntent().getStringExtra("ConfirmPassword");
        fAuth           = FirebaseAuth.getInstance();
        fDatabase       = FirebaseDatabase.getInstance();
        fStore          = FirebaseFirestore.getInstance();

        //checking if the user have already login or not
        if(fAuth.getCurrentUser() != null) {
            Toast.makeText(activity_create_account2.this, "Please logout first from your account to create a new account.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            finish();
        }


        // BUTTON - setOnClickListener, will navigate to "Account Has Been Created"
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initalize();
                openAccountHasBeenCreatedRealtime();
                //openAccountHasBeenCreated();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        // TEXTVIEW - setOnClickListener, will navigate back to previous page of create account
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------------------METHOD---------------------------------------------
    //----------------------------------------------------------------------------------------------


    // REALTIME DATABASE
    // METHOD INTENT - store the information of the user and toast
    // then go to activity account has been created
    public void openAccountHasBeenCreatedRealtime(){
        progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(activity_create_account2.this, "User Created", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    databaseReference = fDatabase.getReference("users");
                    Map<String, Object> newUser = new HashMap<>();
                    newUser.put("fName", name);
                    newUser.put("email", email);
                    newUser.put("phoneNumber", phoneNumber);
                    newUser.put("address", address);
                    newUser.put("matrixNumber", matrix);
                    databaseReference.child(userID).setValue(newUser);
                    startActivity(new Intent(getApplicationContext(), AccountHasBeenCreatedActivity.class));
                } else {
                    Toast.makeText(activity_create_account2.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    // CLOUD FIRESTORE
    // METHOD INTENT - store the information of the user, and toast
    // then go to activity account has been created
    public void openAccountHasBeenCreated(){
        progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(activity_create_account2.this, "User Created", Toast.LENGTH_SHORT).show();
                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("fName", name);
                    user.put("email", email);
                    user.put("phoneNumber", phoneNumber);
                    user.put("address", address);
                    user.put("matrixNumber", matrix);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                    startActivity(new Intent(getApplicationContext(), AccountHasBeenCreatedActivity.class));

                } else {
                    Toast.makeText(activity_create_account2.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // METHOD INTENT - create account first page
    public void openCreateAccount(){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    //METHOD - get all the information from the field
    public void initalize(){
        //phoneNumber = Integer.parseInt(textInputPhoneNumber.getText().toString());
        phoneNumber = textInputPhoneNumber.getText().toString().trim();
        address = textInputAddress.getText().toString().trim();
        matrix = textInputMatrix.getText().toString().trim();
    }
}