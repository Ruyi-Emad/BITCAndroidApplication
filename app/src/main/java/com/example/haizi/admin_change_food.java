package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class admin_change_food extends AppCompatActivity {

    private String getDish ;
    private DatabaseReference ref ;
    private EditText Cname , Cprice , Ccategory , Cdescription;
    private TextView closeBtn ;
    private EditText Cpersons ;
    private Button changeFoodB ;
    private FloatingActionButton deleteFoodBtn ;
    private ImageView foodIMage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_food);

        getDish =getIntent().getExtras().get("dish").toString();

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null) {
//            getDish =intent.getStringExtra("dish");
//        }


        ref = FirebaseDatabase.getInstance().getReference().child("food_list_model").child(getDish);

        Cname = (EditText) findViewById(R.id.CCardFoodName);
        Cprice = (EditText) findViewById(R.id.CCardPrice);
        Ccategory = (EditText) findViewById(R.id.CCardFoodCategory);
        Cdescription = (EditText) findViewById(R.id.CCardDescription);
        Cpersons = (EditText) findViewById(R.id.CCardPersonsToEat);
        foodIMage = (ImageView) findViewById(R.id.CPlateImage);
        deleteFoodBtn = (FloatingActionButton) findViewById(R.id.deletefood);
        changeFoodB = (Button) findViewById(R.id.changefood);
        closeBtn = (TextView) findViewById(R.id.close);


        DisplayNotifInfo();

        changeFoodB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });

        deleteFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent = new Intent(admin_change_food.this,restaurantHome.class);

                        startActivity(intent);
                        Toast.makeText(admin_change_food.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_change_food.this, food_type.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void applyChanges()
    {

        String foodName = Cname.getText().toString();
        String foodPrice = Cprice.getText().toString();
        String foodCategory = Ccategory.getText().toString();
        String foodDescription = Cdescription.getText().toString();
        String foodPersons = Cpersons.getText().toString();

        if (foodName == null)
        {
            Toast.makeText(this, "请你输入菜名字...", Toast.LENGTH_SHORT).show();
        }
        else if (foodPrice == null)
        {
            Toast.makeText(this, "请你输入菜价格...", Toast.LENGTH_SHORT).show();

        }
        else if (foodCategory == null)
        {
            Toast.makeText(this, "请你输入菜类...", Toast.LENGTH_SHORT).show();
        }
        else if (foodDescription == null)
        {
            Toast.makeText(this, "请你输入菜描述...", Toast.LENGTH_SHORT).show();
        }
        else if (foodPersons == null)
        {
            Toast.makeText(this, "请你输入几个人能吃...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();

            productMap.put("foodDescription", foodDescription);
            productMap.put("category", foodCategory);
            productMap.put("foodName", foodName);
            productMap.put("foodPrice",foodPrice);
            productMap.put("howManyPersonsCanEatIt", foodPersons);

            ref.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(admin_change_food.this,"修改成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(admin_change_food.this,HomeActivity.class);
                        startActivity(intent);

                    }
                }
            });

        }
    }


    private void DisplayNotifInfo()
    {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String foodName = dataSnapshot.child("foodName").getValue().toString();
                    String foodPrice = dataSnapshot.child("foodPrice").getValue().toString();
                    String foodCategory = dataSnapshot.child("category").getValue().toString();
                    String foodDescription = dataSnapshot.child("foodDescription").getValue().toString();
                    String foodImage = dataSnapshot.child("imageUri").getValue().toString();
                    String foodPersons = dataSnapshot.child("howManyPersonsCanEatIt").getValue().toString();

                    Cname.setText(foodName);
                    Cprice.setText(foodPrice);
                    Ccategory.setText(foodCategory);
                    Cdescription.setText(foodDescription);
                    Cpersons.setText(foodPersons);

                    Picasso.get().load(foodImage).into(foodIMage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        }) ;
    }


}
