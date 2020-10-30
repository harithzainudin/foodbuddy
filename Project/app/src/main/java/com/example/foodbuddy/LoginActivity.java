package com.example.foodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin;
    TextView textViewSignUp;
    TextInputEditText textInputEmail, textInputPassword;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    String email, password;
    String emailPattern = "[a-zA-Z0-9._-]+@student.upm.edu.my+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        buttonLogin         = findViewById(R.id.sign_in_button);
        textViewSignUp      = findViewById(R.id.textViewSignUp);
        textInputEmail      = findViewById(R.id.text_input_email);
        textInputPassword   = findViewById(R.id.text_input_password);
        progressBar         = findViewById(R.id.progressBar);
        fAuth               = FirebaseAuth.getInstance();

        //checking if the user have already login or not
        if(fAuth.getCurrentUser() != null) {
            Toast.makeText(LoginActivity.this, "Please logout first from your account to create a new account.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            finish();
        }

        // TEXTVIEW setOnClickListener - navigate to create account when clicked
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAccount();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        // BUTTON setOnClickListener - navigate to HOMEPAGE when clicked
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checking();
            }
        });
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------------------METHOD---------------------------------------------
    //----------------------------------------------------------------------------------------------

    //METHOD  - checking all field not empty and according to pattern
    private void checking(){
        initalize();
        progressBar.setVisibility(View.VISIBLE);
        if(!validate()){
            Toast toast = Toast.makeText(getApplicationContext(),"Please fill in the field correctly", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                        openHomepage();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    } else {
                        Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
            progressBar.setVisibility(View.INVISIBLE);
        }
        progressBar.setVisibility(View.INVISIBLE);
    }

    //METHOD - check for validation of the email
    private boolean validate(){
        boolean valid = true;
        if (email.isEmpty() || !email.matches(emailPattern)){
            textInputEmail.setError("Please enter a valid email address");
            valid = false;
        }
        if(password.isEmpty()){
            textInputPassword.setError("Please input your password");
            valid = false;
        }
        return valid;
    }

    //METHOD - get all the information from the field
    public void initalize(){
        email       = textInputEmail.getText().toString().trim();
        password    = textInputPassword.getText().toString().trim();
    }

    // METHOD INTENT - go to activity create account
    public void openCreateAccount(){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    // METHOD INTENT - go to activity homepage
    public void openHomepage(){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}

