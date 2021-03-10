package com.example.haizi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class adminTongzhi extends AppCompatActivity {

    private Button addNotificationButton;
    private Button addfoodButton;
    private TextView closebtn;
    private Button classesBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tongzhi);

        addNotificationButton =(Button) findViewById(R.id.addNotificationButton);
        classesBtn =(Button) findViewById(R.id.addClasses);
        addfoodButton =(Button) findViewById(R.id.foodButton);
        closebtn = (TextView) findViewById(R.id.close);




        addNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(adminTongzhi.this,tongzhiLines.class);
                intent.putExtra("NotificationBank","Notification");
                startActivity(intent);
            }
        });

        addfoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(adminTongzhi.this,food_type.class);
                intent.putExtra("category","cai");
                startActivity(intent);
            }
        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(adminTongzhi.this, MainActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent4);
                finish();
            }
        });


        classesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent =new Intent(adminTongzhi.this,adminClasses.class);
                startActivity(intent);
            }
        });






    }
}
