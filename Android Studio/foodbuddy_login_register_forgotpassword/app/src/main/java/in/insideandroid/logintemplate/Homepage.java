package in.insideandroid.logintemplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import in.insideandroid.logintemplate.RoleSelect;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage extends AppCompatActivity {

    RoleSelect roleselect = new RoleSelect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_layout);
    }

    public void onClick(View view){
        Button admin = (Button) findViewById(R.id.button_admin);
        Button customer = (Button) findViewById(R.id.button_customer);
        Button seller = (Button) findViewById(R.id.button_seller);

        if (view.getId() == admin.getId() ){
            roleselect.setRole(0);
            startActivity(new Intent(this, LoginActivity.class ));
        }
        else if (view.getId() == customer.getId() ){
            roleselect.setRole(1);
            startActivity(new Intent(this, LoginActivity.class ));
        }
        else if (view.getId() == seller.getId() ){
            roleselect.setRole(2);
            startActivity(new Intent(this, LoginActivity.class ));
        }
    }

    }

