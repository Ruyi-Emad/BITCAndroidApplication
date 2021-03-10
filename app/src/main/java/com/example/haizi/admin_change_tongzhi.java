package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class admin_change_tongzhi extends AppCompatActivity {

    private TextView closeBtn ;
    private ImageView ImageNotif;
    private Button BchoosePhoto , Bsend;
    private  FloatingActionButton deleteB;
    private EditText Elines ;
    private String getId ;
    private DatabaseReference ref;
    private Button Bchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change_tongzhi);

        getId =getIntent().getStringExtra("id");

        ref = FirebaseDatabase.getInstance().getReference().child("Notifications").child(getId);


        closeBtn = (TextView) findViewById(R.id.close);
        deleteB = (FloatingActionButton) findViewById(R.id.delete2) ;
        ImageNotif = (ImageView) findViewById(R.id.notificationPhoto2);
        Elines = (EditText) findViewById(R.id.notificationLines2);
        Bchange = (Button) findViewById(R.id.change) ;



        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_change_tongzhi.this, tongzhiLines.class);
                startActivity(intent);
                finish();

            }
        });




        DisplayNotifInfo();

        Bchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });

        deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intent = new Intent(admin_change_tongzhi.this,HomeActivity.class);

                        startActivity(intent);
                        Toast.makeText(admin_change_tongzhi.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    private void applyChanges()
    {
        String Clines = Elines.getText().toString();

        if(Clines.equals(""))
        {
            Toast.makeText(admin_change_tongzhi.this,"请您写通知行",Toast.LENGTH_SHORT).show();

        }
        else
        {
            HashMap<String, Object> productMap=new HashMap<>();

            productMap.put("lines",Clines);
            ref.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(admin_change_tongzhi.this,"修改成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(admin_change_tongzhi.this,tongzhiLines.class);
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
                String linesNotif = dataSnapshot.child("lines").getValue().toString();
                String PIcNotif = dataSnapshot.child("image").getValue().toString();

                Elines.setText(linesNotif);
                Picasso.get().load(PIcNotif).into(ImageNotif);
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError)
         {

         }
     }) ;
    }


}
