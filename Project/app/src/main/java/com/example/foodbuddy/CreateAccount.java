package com.example.foodbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;


public class CreateAccount extends AppCompatActivity {
    TextInputEditText textInputEmail, textInputName,textInputPassword, textInputConfirmPassword;
    TextView loginActivity;
    String name, email, password, confirmPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@student.upm.edu.my+";
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        loginActivity               = findViewById(R.id.textViewAlreadyHaveAcc);
        textInputName               = findViewById(R.id.textInputName);
        textInputEmail              = findViewById(R.id.text_input_email);
        textInputPassword           = findViewById(R.id.text_input_password);
        textInputConfirmPassword    = findViewById(R.id.text_input_confirm_password);
        nextButton                  = findViewById(R.id.next_button);

        textInputName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        //onClickListener - clicking "already have an account? Login" - will go back to login activity
        String text = "Already have an account? Login";
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //changing word Login to GREEN COLOR
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.GREEN);
        ss.setSpan(fcsGreen, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginActivity.setText(ss);

        //BUTTON onClickListener
        nextButton.setOnClickListener(new View.OnClickListener() {
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
        if(!validate()){
            Toast toast = Toast.makeText(getApplicationContext(),"Please fill in the field correctly", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            openNextCreateAccount();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    //METHOD - check for validation of the email
    private boolean validate(){
        boolean valid = true;
        if(name.isEmpty()){
            textInputEmail.setError("Please enter your full name");
            valid = false;
        }
        if (email.isEmpty() || !email.matches(emailPattern)){
            textInputEmail.setError("Please enter a valid email address");
            valid = false;
        }
        if(password.isEmpty()){
            textInputPassword.setError("Please input your password");
            valid = false;
        }
        if (password.length() < 6){
            textInputPassword.setError("Password must be more than 6");
            valid = false;
        }
        if(confirmPassword.isEmpty()){
            textInputConfirmPassword.setError("Please confirm your password");
            valid = false;
        }
        if(!confirmPassword.equals(password)){
            textInputConfirmPassword.setError("Your password is not match with the one you entered");
            valid = false;
        }
        return valid;

    }

    //METHOD - get all the information from the field
    public void initalize(){
        name = textInputName.getText().toString().trim();
        email = textInputEmail.getText().toString().trim();
        password = textInputPassword.getText().toString().trim();
        confirmPassword = textInputConfirmPassword.getText().toString().trim();
    }

    //METHOD - navigate to login page
    public void openLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    //METHOD - navigate to create account 2
    public void openNextCreateAccount(){
        Intent intent = new Intent(this,activity_create_account2.class);
        intent.putExtra("Name", name);
        intent.putExtra("Email", email);
        intent.putExtra("Password", password);
        intent.putExtra("ConfirmPassword", confirmPassword);
        startActivity(intent);
    }
}