package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.haizi.Model.Notifications;
import com.example.haizi.Model.food_list_model;
import com.example.haizi.Model.food_type_model;
import com.example.haizi.preValent.preValent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class show_product extends AppCompatActivity {
    private String dishInfo ="";

    private ImageView Simage ;
    private  String user;
    private TextView Sname ,Sprice ,Sdescription , Scategory , Spersons ,close;
    private ElegantNumberButton elgNumBut;
    private FloatingActionButton cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null)
//        {
//            dishInfo = intent.getStringExtra("dish");
//        }
        dishInfo = getIntent().getExtras().get("dish").toString();

        // user = preValent.onLineUser.getPhone();
        //  elgNumBut = (ElegantNumberButton) findViewById(R.id.ShelegantNumber);
        Sname = (TextView) findViewById(R.id.ShCardFoodName);
        Sprice = (TextView) findViewById(R.id.ShCardPrice);
        Sdescription = (TextView) findViewById(R.id.ShCardDescription);
        Spersons = (TextView) findViewById(R.id.ShCardPersonsToEat);
        Simage = (ImageView) findViewById(R.id.ShPlateImage);
        Scategory =(TextView) findViewById(R.id.ShCardFoodCategory);
        close = (TextView) findViewById(R.id.close);


        // cart = (FloatingActionButton) findViewById(R.id.ShaddToCArt);

        getDishInfo(dishInfo);







//        cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addingToCartView();
//            }
//        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(show_product.this,restaurantHome.class);

                finish();
                startActivity(intent);
            }
        });

    }

    private void getDishInfo(final String dishInfo)
    {
        DatabaseReference notRef = FirebaseDatabase.getInstance().getReference().child("food_list_model");

        notRef.child(dishInfo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    food_list_model model = dataSnapshot.getValue(food_list_model.class);

                    Sname.setText(model.getFoodName());
                    Sprice.setText(model.getFoodPrice());
                    Sdescription.setText(model.getFoodDescription());
                    Spersons.setText(model.getHowManyPersonsCanEatIt());
                    Scategory.setText(model.getCategory());
                    Picasso.get().load(model.getImageUri()).into(Simage);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }





//
//    private void addingToCartView() {
//        Calendar calForDate = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        String saveCurrentDate = currentDate.format(calForDate.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        String saveCurrentTime = currentTime.format(calForDate.getTime());
//
//        final DatabaseReference cartList = FirebaseDatabase.getInstance().getReference().child("cart");
//
//        final HashMap<String, Object> cartMap = new HashMap<>();
//        ///////////???
//        cartMap.put("Pid", dishInfo);
//        cartMap.put("plateName", Sname.getText().toString());
//        cartMap.put("category", Scategory.getText().toString());
//        cartMap.put("price", Sprice.getText().toString());
//        cartMap.put("foodDescription", Sdescription.getText().toString());
//        cartMap.put("personstoeat",Spersons.getText().toString());
//        cartMap.put("Date", saveCurrentDate);
//        cartMap.put("Time", saveCurrentTime);
//        cartMap.put("quantity", elgNumBut.getNumber());
//
//        cartList.child("User View").child(user)
//                .child("Order").child(dishInfo)
//                .updateChildren(cartMap)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task)
//                    {
//                        if (task.isSuccessful())
//                        {
//                            cartList.child("User View").child(user)
//                                    .child("Order").child(dishInfo)
//                                    .updateChildren(cartMap)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task)
//                                        {
//                                            if(task.isSuccessful())
//                                            {
//                                                Toast.makeText(show_product.this, "添加成功", Toast.LENGTH_LONG).show();
//                                                Intent intent = new Intent(show_product.this, restaurantHome.class);
//                                                startActivity(intent);
//                                            }
//                                        }
//                                    });
//                        }
//
//                    }
//                });
//
//
//    }
}



