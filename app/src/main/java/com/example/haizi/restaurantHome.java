package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.haizi.Model.Notifications;
import com.example.haizi.Model.food_list_model;
import com.example.haizi.Model.food_type_model;
import com.example.haizi.ViewHolder.NotificationAdapter;
import com.example.haizi.ViewHolder.food_list_adapter;
import com.example.haizi.ViewHolder.food_type_Adapter;
import com.example.haizi.ViewHolder.home_food_list_adapter;
import com.example.haizi.ViewHolder.home_food_type_adapter;
import com.example.haizi.preValent.preValent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Ref;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class restaurantHome extends AppCompatActivity {
    private TextView closebtn;

    private RecyclerView typeRecyclerView;
    private home_food_type_adapter adapter;

    private RecyclerView listRecyclerView;
    private home_food_list_adapter RadapterList;
    private DatabaseReference ListDataRef;
    private List<food_list_model> Rfood_list_model;
    private ElegantNumberButton elgNumBut;
    //    private home_food_list_adapter.home_food_list_Holder mm;
    private String categoryy;
    private Button cartBtn;
    private String data;
    private String type ="", type2;
    private DatabaseReference ProductsRef;
    private FloatingActionButton flo;
    String xx;


    private List<food_type_model> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home);
//
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            type = getIntent().getExtras().get("Admins").toString();
        }
//
//
//        else
//        {
//            data = getIntent().getExtras().get("Last").toString();
//        }
//        if(bundle != null )
//        {
//            if(getIntent().hasExtra("Admins"))
//            {
//                type = getIntent().getExtras().get("Admins").toString();
//            }
//            else if(getIntent().hasExtra("Admins"))
//            {
//                data = getIntent().getExtras().get("Last").toString();
//            }
//        }
//        Intent in = getIntent();
//        data = intent.getExtras().getString("Last");
//        type = intent.getExtras().getString("Admins");


//        type = getIntent().getStringExtra("Admins");
//        type = getIntent().getExtras().get("Admins").toString();

        ListDataRef = FirebaseDatabase.getInstance().getReference().child("food_list_model");


        closebtn = (TextView) findViewById(R.id.close);

        listRecyclerView = (RecyclerView) findViewById(R.id.List_recyclerView);
        listRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        listRecyclerView.setLayoutManager(layoutManager);
        // listRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Rfood_list_model = new ArrayList<>();
        // cartBtn = (Button) findViewById(R.id.cartB);


//        flo = (FloatingActionButton) findViewById(R.id.floating);
//        flo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(restaurantHome.this,Cart.class);
//                startActivity(intent);
//            }
//        });


//            ListDataRef = FirebaseDatabase.getInstance().getReference("food_list_model");
//            ListDataRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//                {
//                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
//                    {
//                        food_list_model upload = snapshot.getValue(food_list_model.class);
//                        Rfood_list_model.add(upload);
//                        ProductsRef = FirebaseDatabase.getInstance().getReference("food_list_model").child("category");
//
//                    }
//                    RadapterList = new home_food_list_adapter(restaurantHome.this,Rfood_list_model);
//                    ProductsRef = FirebaseDatabase.getInstance().getReference("food_list_model").child("category");
//                    listRecyclerView.setAdapter(RadapterList);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError)
//                {
//
//                }
//            });

//        cartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent intent = new Intent(restaurantHome.this,Cart.class);
//                       startActivity(intent);
//            }
//        });


        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Admins")) {
                    Intent intent = new Intent(restaurantHome.this, adminTongzhi.class);

                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(restaurantHome.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////
        foodList = new ArrayList<>();


        foodList.add(
                new food_type_model(
                        R.drawable.rice, "米饭"

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

//       // typeRecyclerView = (RecyclerView) findViewById(R.id.type_recyclerView);
//        typeRecyclerView.setHasFixedSize(true);
//
//        //creating recyclerview adapter
//        adapter = new home_food_type_adapter(restaurantHome.this, foodList);
//
//        typeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//
//        //setting adapter to recyclerview
//        typeRecyclerView.setAdapter(adapter);


/////////////////////////////////////////////////////////////////////////////////////////
//        adapter.setOnItemClickListener(new home_food_type_adapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(final int position) {
//
//
//                xx = foodList.get(position).getCardText();
//                Toast.makeText(restaurantHome.this, xx, Toast.LENGTH_LONG).show();
//
//
//
//
//
//                // using the object didnot work because the object refer to null , because the method are empty it should be referred to the list
////                Intent intent = new Intent(restaurantHome.this, restaurantHome.class);
////                intent.putExtra("category", xx);
//                // startActivity(intent);
////food_list_model Y = new food_list_model();
////String YY = Y.getId();
////
////                    DatabaseReference pp = FirebaseDatabase.getInstance().getReference().child("food_list_model").child(YY).child("category");
//
//
//            }
//
//
//        });

    }
        @Override
        protected void onStart () {
            super.onStart();
            FirebaseRecyclerOptions<food_list_model> options =
                    new FirebaseRecyclerOptions.Builder<food_list_model>()
                            .setQuery(ListDataRef, food_list_model.class)
                            .build();

            FirebaseRecyclerAdapter<food_list_model, home_food_list_adapter> adapter =
                    new FirebaseRecyclerAdapter<food_list_model, home_food_list_adapter>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull home_food_list_adapter holder, int position, @NonNull final food_list_model model) {

                            holder.CplateName.setText(model.getFoodName());
                            holder.HCategory.setText(model.getCategory());
                            Picasso.get().load(model.getImageUri()).into(holder.CardFoodListImage);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (type.equals("Admins")) {
                                        Intent intent = new Intent(restaurantHome.this, admin_change_food.class);
                                        intent.putExtra("dish", model.getId());
                                        startActivity(intent);

                                    } else {
                                        Intent intent = new Intent(restaurantHome.this, show_product.class);
                                        intent.putExtra("dish", model.getId());
                                        startActivity(intent);
                                    }

                                }
                            });


                        }

                        @NonNull
                        @Override
                        public home_food_list_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_food_list_card, parent, false);
                            home_food_list_adapter holder = new home_food_list_adapter(view);
                            return holder;
                        }
                    };
            listRecyclerView.setAdapter(adapter);
            adapter.startListening();


        }

}
//
//    @Override
//    protected void onStart () {
//        super.onStart();
//        FirebaseRecyclerOptions<food_list_model> options =
//                new FirebaseRecyclerOptions.Builder<food_list_model>()
//                        .setQuery(ListDataRef, food_list_model.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<food_list_model, home_food_list_adapter> adapter =
//                new FirebaseRecyclerAdapter<food_list_model, home_food_list_adapter>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull home_food_list_adapter holder, int position, @NonNull final food_list_model model) {
//
//                        holder.CplateName.setText(model.getFoodName());
//                        holder.HCategory.setText(model.getCategory());
//                        Picasso.get().load(model.getImageUri()).into(holder.CardFoodListImage);
//
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (type.equals("Admins")) {
//                                    Intent intent = new Intent(restaurantHome.this, admin_change_food.class);
//                                    intent.putExtra("dish", model.getId());
//                                    Toast.makeText(restaurantHome.this, "this one ", Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
//
//                                } else {
//                                    Intent intent = new Intent(restaurantHome.this, show_product.class);
//                                    intent.putExtra("dish", model.getId());
//                                    Toast.makeText(restaurantHome.this, "noooo", Toast.LENGTH_SHORT).show();
//                                    startActivity(intent);
//                                }
//
//                            }
//                        });
//
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public home_food_list_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_food_list_card, parent, false);
//                        home_food_list_adapter holder = new home_food_list_adapter(view);
//                        return holder;
//                    }
//                };
//        listRecyclerView.setAdapter(adapter);
//        adapter.startListening();
//
//
//    }


//
//        ListDataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    food_list_model upload = snapshot.getValue(food_list_model.class);
//
//
//                    Rfood_list_model.add(upload);
//                    ProductsRef = FirebaseDatabase.getInstance().getReference("food_list_model");
//
//                }
//                RadapterList = new home_food_list_adapter(restaurantHome.this, Rfood_list_model);
//                ProductsRef = FirebaseDatabase.getInstance().getReference("food_list_model");
//                listRecyclerView.setAdapter(RadapterList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




//    //
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
////        cartMap.put("Pid", ListDataRef);
//        cartMap.put("plate name", food_type_model.list_model.getFoodName());
////        cartMap.put("category", mm.HCategory);
////        cartMap.put("price", mm.Cprice);
////        cartMap.put("description", mm.Cdescription);
////        cartMap.put("persons to eat", mm.Cpeople);
//        cartMap.put("Date", saveCurrentDate);
//        cartMap.put("Time", saveCurrentTime);
////        cartMap.put("time order", mm.Cpeople.getText().toString());
////        cartMap.put("date order", mm.Cpeople.getText().toString());
////        cartMap.put("quantity", elgNumBut.getNumber());
//
//        cartList.child("User View").child(preValent.onLineUser.getPhone())
//                .child("food_list_model")
//                .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                cartList.child("Admin View").child(preValent.onLineUser.getPhone())
//                        .child("food_list_model")
//                        .updateChildren(cartMap)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(restaurantHome.this, "添加成功", Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(restaurantHome.this, HomeActivity.class);
//                                    startActivity(intent);
//                                }
//                            }
//                        });
//            }
//        });
//
//
//    }







//   adapter.setOnItemClickListener(new home_food_type_adapter.OnItemClickListener() {
//                                               @Override
//                                               public void onItemClick(int position) {
//                                                   for (int i = 0; i < foodList.size(); i++) {
//
//                                                       String xx = foodList.get(i).getCardText();
//                                                       // using the object didnot work because the object refer to null , because the method are empty it should be referred to the list
//                                                       Intent intent = new Intent(restaurantHome.this, restaurantHome.class);
//                                                       intent.putExtra("category", xx);
//
//                                                   }
//                                               }
//
//    });
//
//
//        }





















//   adapter.setOnItemClickListener(new home_food_type_adapter.OnItemClickListener() {
//                String xx;
//                                               @Override
//                                               public void onItemClick(int position) {
//
//
//                                                   xx = foodList.get(position).getCardText();
//                                                   Toast.makeText(restaurantHome.this, xx, Toast.LENGTH_LONG).show();
//
//
//                                                   models.add()
//
//                                                   food_list_model mm = new food_list_model();
//                                                   String xxx = mm.getCategory();
//
//                                                   // using the object didnot work because the object refer to null , because the method are empty it should be referred to the list
////                                                       Intent intent = new Intent(restaurantHome.this, restaurantHome.class);
////                                                       intent.putExtra("category", xx);
//
//
//                                                       ListDataRef.addValueEventListener(new ValueEventListener() {
//                                                           @Override
//                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                                                   food_list_model upload = snapshot.getValue(food_list_model.class);
//                                                                   Rfood_list_model.add(upload);
//                                                                   ProductsRef = FirebaseDatabase.getInstance().getReference("food_list_model");
//
//                                                               }
//                                                               RadapterList = new home_food_list_adapter(restaurantHome.this, Rfood_list_model);
//                                                               ProductsRef = FirebaseDatabase.getInstance().getReference("food_list_model");
//                                                               listRecyclerView.setAdapter(RadapterList);
//                                                           }
//
//                                                           @Override
//                                                           public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                           }
//                                                       });
//
//                                                   }
//
//
//    });
//
//
//        }























//private ProgressDialog loading;
//
//private food_list_adapter adapter;
//private RecyclerView recyclerView;
//private DatabaseReference databaseRef;
//private List<food_list_model>  food_list_models;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.activity_restaurant_home, container, false);
//        final FragmentActivity c = getActivity();
//        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
////********
//        recyclerView.setLayoutManager(new LinearLayoutManager(c));
//
//        food_list_models = new ArrayList<>();
//
//        databaseRef = FirebaseDatabase.getInstance().getReference("food_list_model");
//
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    food_list_model food_list = snapshot.getValue(food_list_model.class);
//                    food_list_models.add(food_list);
//                }
////
//                adapter = new food_list_adapter(getActivity(), food_list_models);
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//        return view;
//    }



