package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.haizi.Model.Notifications;
import com.example.haizi.Model.food_list_model;
import com.example.haizi.ViewHolder.NotificationAdapter;
import com.example.haizi.ViewHolder.food_list_adapter;
import com.example.haizi.ViewHolder.food_type_Adapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class notificationsHome extends AppCompatActivity {



    private RecyclerView recyclerView;
    private DatabaseReference databaseRef;
   // private List<Notifications> notif_models;
    private FirebaseRecyclerOptions<Notifications> options;
 //   private NotificationAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_home);


        databaseRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        Paper.init(this);


       // notif_models = new ArrayList<>();
       // notificationCenter = getIntent().getExtras().get("NotificationBank").toString();
//********
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

      //********************
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //creating recyclerview adapter
      //  adapter =new food_type_Adapter(notificationsHome.this,Notifications);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setting adapter to recyclerview





        options = new FirebaseRecyclerOptions.Builder<Notifications>().setQuery(databaseRef, Notifications.class).build();

        FirebaseRecyclerAdapter<Notifications, NotificationAdapter> adapter =
                new FirebaseRecyclerAdapter<Notifications, NotificationAdapter>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull NotificationAdapter holder, int position, @NonNull Notifications model) {

                        //Notifications uploadCurrent = notif_models.get(position);
                        holder.cardNotificationLines.setText(model.getLines());
                        holder.cardNotificationTime.setText(model.getLines());
                        Picasso.get().load(model.getImage()).fit().centerCrop().into(holder.cardNotificationImage);

                    }

                    @NonNull
                    @Override
                    public NotificationAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
                        NotificationAdapter holder = new NotificationAdapter(view);
                        return holder;

                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Notifications N = snapshot.getValue(Notifications.class);
//                    notif_models.add(N);
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
    }







//        recyclerView.setAdapter(adapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);setLayoutManager


//
//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseRecyclerOptions<Notifications> options =
//                new FirebaseRecyclerOptions.Builder<Notifications>()
//                        .setQuery(databaseRef, Notifications.class)
//                        .build();
////
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Notifications N = snapshot.getValue(Notifications.class);
//                    notif_models.add(N);
//                }
////
//                adapter = new NotificationAdapter(notificationsHome.this, notif_models);
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(notificationsHome.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//
//
//
//
//
//  adapter = new FirebaseRecyclerAdapter<Notifications, NotificationAdapter>(options) {
//@Override
//protected void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position, @NonNull final Notifications model) {
//
//        Notifications uploadCurrent = notif_models.get(position);
//        holder.cardNotificationLines.setText(uploadCurrent.getLines());
//        holder.cardNotificationTime.setText(uploadCurrent.getLines());
//        Picasso.get().load(uploadCurrent.getImageUri()).fit().centerCrop().into(holder.cardNotificationImage);
//
//
//        }
//
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        Intent intent = new Intent(notificationsHome.this,show_Notifications.class);
//        intent.putExtra("Lines",model.getLines());
//        intent.putExtra("date",model.getDate());
//        intent.putExtra("image",model.getImageUri());
//        startActivity(intent);
//        }
//        });
//        }
//@Override
//public int getItemCount() {
//        return notif_models.size();
//        }
//
//@NonNull
//@Override
//public NotificationAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(notificationsHome.this).inflate(R.layout.notification_item_layout,parent,false);
//        return new NotificationAdapter(v);
//        }
//
//
//        };

















