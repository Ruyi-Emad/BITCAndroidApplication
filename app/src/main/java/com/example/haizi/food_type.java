package com.example.haizi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haizi.Model.food_type_model;
import com.example.haizi.ViewHolder.food_type_Adapter;

import java.util.ArrayList;
import java.util.List;

public class food_type extends AppCompatActivity implements food_type_Adapter.ONfoodListener  {

    private RecyclerView recyclerView;
    private food_type_Adapter adapter;
    private food_type_model mm;

    private TextView closeB;
    private ImageView homebtn;
    private int x;
    private List <food_type_model> foodList;
    private String CategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type);
        //initializing the productlist
        foodList = new ArrayList<>();
        homebtn = (ImageView) findViewById(R.id.homess);
        closeB = (TextView) findViewById(R.id.close);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(food_type.this ,restaurantHome.class);
                intent7.putExtra("Admins","Admins");
                startActivity(intent7);
            }
        });



        closeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(food_type.this,adminTongzhi.class);
                startActivity(intent);
            }
        });


        foodList.add(
                new food_type_model(
                        R.drawable.rice,
                        "米饭"

                )
        );
        foodList.add(
                new food_type_model(
                        R.drawable.noodles, "面条"

                )
        );
        foodList.add(
                new food_type_model(
                        R.drawable.salad, "沙拉"

                )
        );
        foodList.add(
                new food_type_model(
                        R.drawable.vegetables, "菜"

                )
        );
        foodList.add(
                new food_type_model(
                        R.drawable.meat, "肉"

                )
        );



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //creating recyclerview adapter
        adapter =new food_type_Adapter(food_type.this,foodList,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


//        adapter.setOnItemClickListener(new food_type_Adapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//String xx="";
//                Intent intent = new Intent(food_type.this, add_food_type_list.class);
//
//                for(int i = 0; i<foodList.size(); i++) {
//
//                   xx = foodList.get(i).getCardText();
//                    // using the object didnot work because the object refer to null , because the method are empty it should be referred to the list
//
//
//                }
//                intent.putExtra("category", xx);
//                startActivity(intent);
//                // food_type_model upload = food_type_model.getCardText();
//               // String title = getIntent().getStringExtra("title");
////
//            }
//        });
//

    }

    @Override
    public void onNoteClick(int position) {
        String xx;
        food_type_Adapter.food_type_Holder kill;

//        for(int i = 0; i<foodList.size(); i++)
//        {
        xx = foodList.get(position).getCardText();
        Intent intent = new Intent(food_type.this, add_food_type_list.class);
        intent.putExtra("category", xx);
        startActivity(intent);



    }
}
