package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.haizi.Model.Users;
import com.example.haizi.preValent.preValent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin=(Button)findViewById(R.id.login);
        btnRegister=(Button)findViewById(R.id.register);

        loading=new ProgressDialog(this);
        Paper.init(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLogin=new Intent(MainActivity .this,login.class);
                startActivity(intLogin);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intLogin=new Intent(MainActivity .this,register.class);
                startActivity(intLogin);
            }
        });
//i am creating string variables to pass the valuse to preValent variables
        String PhoneKey =Paper.book().read(preValent.userPhoneKey);
        String PasswordKey =Paper.book().read(preValent.userPasswordKey);

        if(PhoneKey!=null && PasswordKey!=null){
            if(TextUtils.isEmpty(PhoneKey)&& TextUtils.isEmpty(PasswordKey));
            {
                AllowAccess(PhoneKey,PasswordKey);

                loading.setTitle("用户已登录");
                loading.setMessage("请你稍等 。。。");
                loading.setCanceledOnTouchOutside(false);
                loading.show();

            }
        }
    }

    private void AllowAccess( final String phone, final  String password) {

        final DatabaseReference Ref;
        Ref= FirebaseDatabase.getInstance().getReference();
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){

                    Users userData=dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if(userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            loading.dismiss();

                            Intent intLog = new Intent(MainActivity.this, HomeActivity.class);
                            preValent.onLineUser = userData;
                            startActivity(intLog);
                        } else {
                            loading.dismiss();
                            Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"这个用户部存在",Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    Toast.makeText(MainActivity.this,"需要创建新的用户",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
