package in.insideandroid.logintemplate;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import in.insideandroid.logintemplate.RoleSelect;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfile extends AppCompatActivity {

    RoleSelect roleselect = new RoleSelect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
    }
}
