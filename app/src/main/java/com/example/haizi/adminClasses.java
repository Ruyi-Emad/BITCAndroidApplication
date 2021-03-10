package com.example.haizi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminClasses extends AppCompatActivity
{
    private Button first, second, third , forth , fifth;
    private TextView closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_classes);
        closeBtn = (TextView) findViewById(R.id.close);
        first = (Button) findViewById(R.id.class16);
        second = (Button) findViewById(R.id.class17);
        third = (Button) findViewById(R.id.class18);
        forth = (Button) findViewById(R.id.class16);
        fifth = (Button) findViewById(R.id.class16);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(adminClasses.this,adminAddClasses.class);
                intent.putExtra("Class 号", "1623013");
                startActivity(intent);
            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(adminClasses.this,adminAddClasses.class);
                intent.putExtra("Class 号", "1726022");
                startActivity(intent);
            }
        });


        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(adminClasses.this,adminAddClasses.class);
                intent.putExtra("Class 号", "1822033");
                startActivity(intent);
            }
        });

        forth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(adminClasses.this,adminAddClasses.class);
                intent.putExtra("Class 号", "19330161");
                startActivity(intent);
            }
        });

        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(adminClasses.this,adminAddClasses.class);
                intent.putExtra("Class 号", "19230171");
                startActivity(intent);
            }
        });



        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminClasses.this, adminTongzhi.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
