package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haizi.Model.Notifications;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class show_Notifications extends AppCompatActivity {
    private String notifId ="";
    private TextView linesV , dateV,timeV, closebtn;
    private ImageView imageV ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__notifications);

        notifId = getIntent().getStringExtra("id");

        linesV = (TextView) findViewById(R.id.SLines);
        dateV = (TextView) findViewById(R.id.Sdate);
        timeV = (TextView) findViewById(R.id.Stime);
        imageV = (ImageView) findViewById(R.id.Sphoto);
        closebtn =(TextView) findViewById(R.id.close);

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(show_Notifications.this,HomeActivity.class);
                startActivity(intent);
            }
        });


        getNotificationDetails(notifId);


    }
    //to get the information of the card and be on this activity
    private void getNotificationDetails(final String notifId) {

        DatabaseReference notRef = FirebaseDatabase.getInstance().getReference().child("Notifications");

        notRef.child(notifId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Notifications notifications = dataSnapshot.getValue(Notifications.class);

                    linesV.setText(notifications.getLines());
                    dateV.setText(notifications.getDate());
                    timeV.setText(notifications.getTime());
                    Picasso.get().load(notifications.getImage()).into(imageV);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
