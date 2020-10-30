package com.example.foodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class UserProfileActivity extends AppCompatActivity {

    EditText email,password,phonenum,shopname,address,imgurl;
    Button editemail, editpass, editphone, editshopname, editaddress,orderhistory,btnChoose,btnUpload,userpicChoose,userpicUpload;
    ImageView editpic;
    TextView userprofiletext;
    Uri filePath;
    final int PICK_IMAGE_REQUEST = 71;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        //textfield
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        phonenum = findViewById(R.id.phoneText);
        shopname = findViewById(R.id.shopText);
        address = findViewById(R.id.addressText);
        imgurl = findViewById(R.id.imgurlTxt);


        //button
        editemail = findViewById(R.id.editUsername);
        editpass = findViewById(R.id.editPassword);
        editphone = findViewById(R.id.editPhone);
        editshopname = findViewById(R.id.shopEditButton);
        editaddress = findViewById(R.id.addressEditButton);
        orderhistory = findViewById(R.id.orderHistory);
        editpic = findViewById(R.id.userProfile);
        btnChoose = (Button) findViewById(R.id.chooseBtn);
        btnUpload = (Button) findViewById(R.id.uploadBtn);
        userpicChoose = findViewById(R.id.choosepicBtn);
        userpicUpload = findViewById(R.id.uploadpicBtn);

        //TextView
        userprofiletext = findViewById(R.id.userProfileText);

        //Receiving intent from previous page
        /* Please replace the intent name after initialize said intent variable, also change the key at orderhistory button if ur not using phone
        number as key. Also de-comment the ref where the key is used if u wanna de-comment this
        Intent intent = getIntent();
        String key = intent.getStringExtra("phonenum");
        */
        //initiating Firebase reference
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mGetReference = mDatabase.getReference().child("users/0133081510");
        //final DatabaseReference mGetReference = mDatabase.getReference().child("users/"+key);
        final ProgressDialog pd = new ProgressDialog(this);

        //initializing textfield input
        mGetReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileObject holder = dataSnapshot.getValue(UserProfileObject.class);
                email.setText(holder.email);
                password.setText(holder.password);
                phonenum.setText(holder.phone);
                address.setText(holder.address);
                shopname.setText(holder.shopname);
                String url = holder.userimg;
                Picasso.get().load(url).into(editpic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast invalid_email = Toast.makeText(getApplicationContext(),"Data Retrieval Failed",Toast.LENGTH_SHORT);
            }
        });

        //email edit button function
        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailHolder = email.getText().toString();
                UserProfileObject valid = new UserProfileObject();

                if(valid.emailRegex(emailHolder) == true ){
                    mGetReference.child("email").setValue(emailHolder);
                    email.setText(emailHolder);
                    Toast valid_email = Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT);
                    valid_email.show();
                }

                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid Email",Toast.LENGTH_SHORT);
                    mGetReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            UserProfileObject holder = dataSnapshot.getValue(UserProfileObject.class);
                            email.setText(holder.email);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast invalid_email = Toast.makeText(getApplicationContext(),"Data Retrieval Failed",Toast.LENGTH_SHORT);
                            invalid_email.show();
                        }
                    });
                }

            }// end of onClick method
        });//onClick Listener for edit email button


        //phone edit button action
        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneHolder = phonenum.getText().toString();
                UserProfileObject valid = new UserProfileObject();

                if(valid.phoneRegex(phoneHolder) == true ){
                    mGetReference.child("phone").setValue(phoneHolder);
                    phonenum.setText(phoneHolder);
                    Toast valid_email = Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT);
                    valid_email.show();
                }

                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid Phone",Toast.LENGTH_SHORT);
                    mGetReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            UserProfileObject holder = dataSnapshot.getValue(UserProfileObject.class);
                            phonenum.setText(holder.phone);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast invalid_email = Toast.makeText(getApplicationContext(),"Data Retrieval Failed",Toast.LENGTH_SHORT);
                            invalid_email.show();
                        }
                    });
                }

            }// end of onClick method
        });//onClick Listener for edit phone button


        //password edit button function
        editpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwHolder = password.getText().toString();
                mGetReference.child("password").setValue(pwHolder);
                password.setText(pwHolder);
                Toast valid_email = Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT);
                valid_email.show();
            }// end of onClick method
        });//onClick Listener for edit password button

        //shopname edit button function
        editshopname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shopnameHolder = shopname.getText().toString();
                mGetReference.child("shopname").setValue(shopnameHolder);
                shopname.setText(shopnameHolder);
                Toast valid_email = Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT);
                valid_email.show();
            }// end of onClick method
        });//onClick Listener for edit password button

        //address edit button function
        editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addressHolder = address.getText().toString();
                mGetReference.child("address").setValue(addressHolder);
                shopname.setText(addressHolder);
                Toast valid_email = Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT);
                valid_email.show();
            }// end of onClick method
        });//onClick Listener for edit password button

        //choosing shop picture button
        btnChoose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }

        });

        //user profile pic for choosing picture
        userpicChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        //user profile pic for uploading picture and show updated pic
        userpicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filePath != null){

                    pd.setMessage("Uploading...");
                    pd.show();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();

                    final StorageReference childRef = storageRef.child("users/0133081510/userimg/"+ UUID.randomUUID().toString());

                    final UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(UserProfileActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UserProfileActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw task.getException();
                            }

                            return childRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri downloadUri = task.getResult();
                                mGetReference.child("userimg").setValue(downloadUri.toString());
                            }else {
                                Toast.makeText(UserProfileActivity.this, "Fail to get URI", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //uploading picture for shop images
        btnUpload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (filePath != null){

                    pd.setMessage("Uploading...");
                    pd.show();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();

                    final StorageReference childRef = storageRef.child("users/0133081510/shopimg/"+ UUID.randomUUID().toString());

                    final UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(UserProfileActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(UserProfileActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw task.getException();
                            }

                            return childRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri downloadUri = task.getResult();
                                mGetReference.child("shopimg").setValue(downloadUri.toString());
                            }else {
                                Toast.makeText(UserProfileActivity.this, "Fail to get URI", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });//end of upload shop pic

        //order history button, please change the intent sending key if ur not using phone number
        orderhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mGetReference.child("phone").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String str = dataSnapshot.getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(),OrderHistoryActivity.class);
                        intent.putExtra("phonenum",str);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }//end of on Create Method

    //this is method for both choosing picture button where it save the img url
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgurl.setText(String.valueOf(filePath));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}