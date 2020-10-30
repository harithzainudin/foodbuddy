package com.example.foodbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListOfFoodInShopActivity extends AppCompatActivity {

    private RecyclerView foodRecyclerView;
    private FoodMenuAdapter foodAdapter;
    private ProgressBar foodProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<FoodMenu> fooddata;

    TextView text_view_name;
    String mykey;
    LinearLayout backfunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_food_in_shop);
        //assign variable
        text_view_name = findViewById(R.id.shopName);
        backfunction = findViewById(R.id.backheader);

        foodRecyclerView = findViewById(R.id.recycler_foodview);
        foodRecyclerView.setHasFixedSize(true);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodProgressCircle = findViewById(R.id.progress_foodcircle);
        fooddata = new ArrayList<>();

        // mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        //String uploadId = mDatabaseRef.push().getKey();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        //mDatabaseRef.child(uploadId).addValueEventListener(new ValueEventListener() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uploadSnapshot : dataSnapshot.getChildren()) {
                    // this is where you actually getting the information
                    //uploadSnapshot.child("menukedai").getValue(foodmenu.class);
                    for(DataSnapshot menuItemSnapshot : uploadSnapshot.child("menukedai").getChildren()){
                        FoodMenu foodmenu = menuItemSnapshot.getValue(FoodMenu.class);
                        fooddata.add(foodmenu);
                    }

                }

                foodAdapter = new FoodMenuAdapter(ListOfFoodInShopActivity.this, fooddata);
                foodRecyclerView.setAdapter(foodAdapter);
                foodRecyclerView.setLayoutManager(new GridLayoutManager(ListOfFoodInShopActivity.this, 2));
                foodProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListOfFoodInShopActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                foodProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        //create bundle
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String uploadId = bundle.getString("uploadId");

        //set to textview
        text_view_name.setText(name);

        //function backicon
        backfunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBack();
            }
        });

    }

    private void goToBack() {
        Intent backicons = new Intent(this, HomepageActivity.class);
        startActivity(backicons);
    }
}