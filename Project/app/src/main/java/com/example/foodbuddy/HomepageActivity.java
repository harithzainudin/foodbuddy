package com.example.foodbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private DatabaseReference mDatabaseRef;
    private List<UploadImageAdapter> mUploads;
    private List<String> Key;

    ImageView iconuser, iconcart, iconsearch;
    EditText searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        iconuser = findViewById(R.id.profileaccount);
        iconcart = findViewById(R.id.shoppingcart);
        searchbar = findViewById(R.id.searchkedaibar);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = findViewById(R.id.progress_circle);
        mUploads = new ArrayList<>();
        Key = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadImageAdapter upload = postSnapshot.getValue(UploadImageAdapter.class);
                    Key.add(postSnapshot.getKey());
                    mUploads.add(upload);
                    Log.i(String.valueOf(Key), "onDataChange: ");
                }
                mAdapter = new ImageAdapter(HomepageActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                // mRecyclerView.setAdapter((RecyclerView.Adapter) Key);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomepageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        //function user icon
        iconuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUserProfile();

            }
        });

        //function iconcart
        iconcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCart();
            }
        });

        //


        //sini jangan buang, sini untuk search bar
        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    // ---------------------------------------------------------------------------------------------
    // ------------------------------------------METHOD---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private void filter(String text) {
        List<UploadImageAdapter> filterList = new ArrayList<>();

        for(UploadImageAdapter item: mUploads){
            if(item.getmenuname().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        mAdapter.filteredList(filterList);
    }


    private void goToCart() {
        Intent carts = new Intent(this, CartActivity.class);
        startActivity(carts);
    }

    private void goToUserProfile() {
        Intent profile = new Intent(this, UserProfileActivity.class);
        startActivity(profile);
    }

    public void logout (View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}