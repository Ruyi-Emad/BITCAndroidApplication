package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haizi.Model.Notifications;
import com.example.haizi.Model.Users;
import com.example.haizi.Model.classesModel;
import com.example.haizi.ViewHolder.NotificationAdapter;
import com.example.haizi.preValent.preValent;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private DrawerLayout drawer;
    private String type ="";

    private String finals;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private  TextView name ,studentHollo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            type =getIntent().getExtras().get("Admin").toString();
        }

        //  type = getIntent().getStringExtra("Admin");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Notifications");
        Paper.init(this);


        name = (TextView) findViewById(R.id.studentName);
        studentHollo = (TextView) findViewById(R.id.Studenthello);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("BITC");
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawerOpen, R.string.drawerClose);
        drawer.addDrawerListener(toggle);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);
        toggle.syncState();
        //to set the menu icons with its original color





        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_Name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_image);



        if(!type .equals("Admin"))
        {
            finals = preValent.onLineUser.getName();

            userNameTextView.setText(finals);
            name.setText(preValent.onLineUser.getName());
            Picasso.get().load(preValent.onLineUser.getImage()).placeholder(R.drawable.profile_picture).into(profileImageView);
        }

        if(type.equals("Admin"))
        {
            studentHollo.setVisibility(View.INVISIBLE);
            name.setText("教师");
        }

        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new notificationsHome()).commit();
            navigationView.setCheckedItem(R.id.notifications);

        }
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);






    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Notifications> options =
                new FirebaseRecyclerOptions.Builder<Notifications>()
                        .setQuery(ProductsRef, Notifications.class)
                        .build();


        FirebaseRecyclerAdapter<Notifications, NotificationAdapter> adapter =
                new FirebaseRecyclerAdapter<Notifications, NotificationAdapter>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotificationAdapter holder, int position, @NonNull final Notifications model)
                    {
                        holder.cardNotificationLines.setText(model.getLines());
                        holder.cardNotificationTime.setText(model.getDate());

                        Picasso.get().load(model.getImage()).into(holder.cardNotificationImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                if(type.equals("Admin"))
                                {
                                    Intent intent = new Intent(HomeActivity.this,admin_change_tongzhi.class);
                                    intent.putExtra("id",model.getId());
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(HomeActivity.this, show_Notifications.class);
                                    intent.putExtra("id", model.getId());
                                    startActivity(intent);
                                }

                            }
                        });





                    }

                    @NonNull
                    @Override
                    public NotificationAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
                        NotificationAdapter holder = new NotificationAdapter(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.restaurant:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new restaurantHome()).commit();
//                if(getIntent().hasExtra("NotificationBank")){
//                    tongzhiLines no = getIntent().getParcelableExtra("NotificationBank");
//                    Log.d(TAG,"onCreate:"+no.toString());
//                }

                if(!type.equals("Admin"))
//                {
//                    Intent intent = new Intent(HomeActivity.this,restaurantHome.class);
//                    intent.putExtra("Admin","Admin");
//                    startActivity(intent);
//                }
//                else {
                {
                    Intent intent1 = new Intent(HomeActivity.this, restaurantHome.class);
                    startActivity(intent1);
                    Toast.makeText(HomeActivity.this, "食堂", Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.notifications:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new notificationsHome()).commit();
                // Intent intent2 = new Intent(HomeActivity.this, notificationsHome.class);
                Toast.makeText(HomeActivity.this,"通知",Toast.LENGTH_LONG).show();

                // startActivity(intent2);
                break;

            case R.id.about:
                if(!type.equals("Admin")) {
                    Intent intent6 = new Intent(HomeActivity.this, about.class);
                    Toast.makeText(HomeActivity.this, "关于我们", Toast.LENGTH_LONG).show();

                    startActivity(intent6);

                }
                break;



            case R.id.Classes:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new settings()).commit();
                if(!type.equals("Admin"))
                {
                    Intent intent = new Intent(HomeActivity.this, HomeClasses.class);

                    startActivity(intent);

                }

                break;




            case R.id.settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new settings()).commit();

                if(!type.equals("Admin"))
//                {
//                    Toast.makeText(HomeActivity.this, "不允许", Toast.LENGTH_LONG).show();
//                }
//                else
                {
                    Intent intent7 = new Intent(HomeActivity.this, Settings2.class);
                    Toast.makeText(HomeActivity.this, "设置", Toast.LENGTH_LONG).show();

                    startActivity(intent7);

                }
                break;


            case R.id.logout:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new settings()).commit();
//                Intent intent3 = new Intent(HomeActivity.this, .class);
//                Toast.makeText(HomeActivity.this,"",Toast.LENGTH_LONG).show();
//
//                startActivity(intent);

                Paper.book().destroy();

                Intent intent4 = new Intent(HomeActivity.this, MainActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent4);
                finish();


                break;

        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


//            if(drawer.isDrawerOpen(GravityCompat.START))
//
//            {
//                drawer.closeDrawer(GravityCompat.START);
//            }
//            else
//            {
//                super.onBackPressed();
//            }

    }


}
