package com.example.foodbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    TextView shopname1,orderdesc1,price1,shopname2,orderdesc2,price2,shopname3,orderdesc3,price3,selldesc1,sellprice1,selldesc2,sellprice2,selldesc3,sellprice3;
    ImageView orderpic1,orderpic2,orderpic3,sellpic1,sellpic2,sellpic3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        //intent receiver, please change the intent variable if decide the key will be other than user phone number
        Intent intent = getIntent();
        String str = intent.getStringExtra("phonenum");

        //set textfield variable
        //for order bought
        shopname1 = findViewById(R.id.shopname1);
        shopname2 = findViewById(R.id.shopname2);
        shopname3 = findViewById(R.id.shopname3);
        orderdesc1 = findViewById(R.id.orderdesc1);
        orderdesc2 = findViewById(R.id.orderdesc2);
        orderdesc3 = findViewById(R.id.orderdesc3);
        price1 = findViewById(R.id.price1);
        price2 = findViewById(R.id.price2);
        price3 = findViewById(R.id.price3);
        //for order sell
        selldesc1 = findViewById(R.id.selldesc1);
        selldesc2 = findViewById(R.id.selldesc2);
        selldesc3 = findViewById(R.id.selldesc3);
        sellprice1 = findViewById(R.id.sellprice1);
        sellprice2 = findViewById(R.id.sellprice2);
        sellprice3 = findViewById(R.id.sellprice3);

        //set ImageView variable
        orderpic1 = findViewById(R.id.orderpic1);
        orderpic2 = findViewById(R.id.orderpic2);
        orderpic3 = findViewById(R.id.orderpic3);
        sellpic1 = findViewById(R.id.sellpic1);
        sellpic2 = findViewById(R.id.sellpic2);
        sellpic3 = findViewById(R.id.sellpic3);

        final ArrayList<String> orderid = new ArrayList<String>();
        final ArrayList<OrderObject> orderholder = new ArrayList<OrderObject>();

        //initiating Firebase reference
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mGetReference = mDatabase.getReference().child("users/"+str+"/orderid");

        //get order id
        mGetReference.limitToLast(3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //iterate getting all parent key to access children data
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    orderid.add(ds.getKey());
                }

                //if orderid grabbed 3 key
                if (orderid.size() == 3){
                    for (int i = 2; i > -1 ; i--){
                        orderholder.add(dataSnapshot.child(orderid.get(i)).getValue(OrderObject.class));
                    }//end of loop

                    shopname1.setText(orderholder.get(0).getShopname());
                    orderdesc1.setText(orderholder.get(0).getOrderdesc());
                    price1.setText("RM" + String.valueOf(orderholder.get(0).getPrice()));
                    String url1 = orderholder.get(0).getShopimg();
                    Picasso.get().load(url1).into(orderpic1);

                    shopname2.setText(orderholder.get(1).getShopname());
                    orderdesc2.setText(orderholder.get(1).getOrderdesc());
                    price2.setText("RM" + String.valueOf(orderholder.get(1).getPrice()));
                    String url2 = orderholder.get(1).getShopimg();
                    Picasso.get().load(url2).into(orderpic2);

                    shopname3.setText(orderholder.get(2).getShopname());
                    orderdesc3.setText(orderholder.get(2).getOrderdesc());
                    price3.setText("RM" + String.valueOf(orderholder.get(2).getPrice()));
                    String url3 = orderholder.get(2).getShopimg();
                    Picasso.get().load(url3).into(orderpic3);
                }

                //if orderid grabbed 2 key
                else if (orderid.size() == 2){
                    for (int i = 1; i > -1 ; i--){
                        orderholder.add(dataSnapshot.child(orderid.get(i)).getValue(OrderObject.class));
                    }//end of loop

                    shopname1.setText(orderholder.get(0).getShopname());
                    orderdesc1.setText(orderholder.get(0).getOrderdesc());
                    price1.setText("RM" + String.valueOf(orderholder.get(0).getPrice()));
                    String url1 = orderholder.get(0).getShopimg();
                    Picasso.get().load(url1).into(orderpic1);

                    shopname2.setText(orderholder.get(1).getShopname());
                    orderdesc2.setText(orderholder.get(1).getOrderdesc());
                    price2.setText("RM" + String.valueOf(orderholder.get(1).getPrice()));
                    String url2 = orderholder.get(1).getShopimg();
                    Picasso.get().load(url2).into(orderpic2);
                }

                //if orderid grabbed 1 key
                else if (orderid.size() == 1){
                    for (int i = 0; i > -1 ; i--){
                        orderholder.add(dataSnapshot.child(orderid.get(i)).getValue(OrderObject.class));
                    }//end of loop

                    shopname1.setText(orderholder.get(0).getShopname());
                    orderdesc1.setText(orderholder.get(0).getOrderdesc());
                    price1.setText("RM" + String.valueOf(orderholder.get(0).getPrice()));
                    String url1 = orderholder.get(0).getShopimg();
                    Picasso.get().load(url1).into(orderpic1);
                }
            }//end of datachange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });//end of bought order history

        //referencing to sell id order, limit to only 3 as there only 3 card view
        DatabaseReference sellref = mDatabase.getReference().child("users/"+str+"/sellid");
        sellref.limitToLast(3).addValueEventListener(new ValueEventListener() {

            ArrayList<String> sellid = new ArrayList<String>();
            ArrayList<SellObject> sellholder = new ArrayList<SellObject>();

            //grabbing key for fetching each parent children data
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    sellid.add(ds.getKey());
                }

                //if the sellid grabbed reached 3
                if(sellid.size() == 3) {

                    for (int i = 2; i > -1 ; i--){
                        sellholder.add(dataSnapshot.child(sellid.get(i)).getValue(SellObject.class));
                    }//end of loop

                    selldesc1.setText(sellholder.get(0).getSelldesc());
                    sellprice1.setText("RM" + String.valueOf(sellholder.get(0).getPrice()));
                    String url1 = sellholder.get(0).getShopimg();
                    Picasso.get().load(url1).into(sellpic1);

                    selldesc2.setText(sellholder.get(1).getSelldesc());
                    sellprice2.setText("RM" + String.valueOf(sellholder.get(1).getPrice()));
                    String url2 = sellholder.get(1).getShopimg();
                    Picasso.get().load(url2).into(sellpic2);

                    selldesc3.setText(sellholder.get(2).getSelldesc());
                    sellprice3.setText("RM" + String.valueOf(sellholder.get(2).getPrice()));
                    String url3 = sellholder.get(2).getShopimg();
                    Picasso.get().load(url3).into(sellpic3);
                }
                //if the sellid grabbed reached 3
                else if (sellid.size() == 2){

                    for (int i = 1; i > -1 ; i--){
                        sellholder.add(dataSnapshot.child(sellid.get(i)).getValue(SellObject.class));
                    }//end of loop

                    selldesc1.setText(sellholder.get(0).getSelldesc());
                    sellprice1.setText("RM" + String.valueOf(sellholder.get(0).getPrice()));
                    String url1 = sellholder.get(0).getShopimg();
                    Picasso.get().load(url1).into(sellpic1);

                    selldesc2.setText(sellholder.get(1).getSelldesc());
                    sellprice2.setText("RM" + String.valueOf(sellholder.get(1).getPrice()));
                    String url2 = sellholder.get(1).getShopimg();
                    Picasso.get().load(url2).into(sellpic2);
                }
                //if the sellid grabbed reached 3
                else if (sellid.size() == 1){

                    for (int i = 0; i > -1 ; i--){
                        sellholder.add(dataSnapshot.child(sellid.get(i)).getValue(SellObject.class));
                    }//end of loop

                    selldesc1.setText(sellholder.get(0).getSelldesc());
                    sellprice1.setText("RM" + String.valueOf(sellholder.get(0).getPrice()));
                    String url1 = sellholder.get(0).getShopimg();
                    Picasso.get().load(url1).into(sellpic1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }//end of onCreate class
}//end of Class