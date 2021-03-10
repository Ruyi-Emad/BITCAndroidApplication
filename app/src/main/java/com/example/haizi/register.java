package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class register extends AppCompatActivity {

//定义变量


    private Button btnRegister;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    // "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$");


    private static final Pattern Phone_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    // "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{11}" +               //at least 4 characters
                    "$");





    private EditText Ename,Epassword,Ephone;
    private ProgressDialog loading;
    private TextView haveAccountB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//refer the variables to its view
        btnRegister=(Button) findViewById(R.id.register);
        Ename =(EditText)findViewById(R.id.userName);
        Epassword=(EditText)findViewById(R.id.password);
        Ephone=(EditText)findViewById(R.id.phone);
        loading=new ProgressDialog(this);
        haveAccountB = (TextView) findViewById(R.id.haveAccount);


        //method for the register button
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intReg=new Intent(register.this,MainActivity.class);
//            startActivity(intReg);
//
//            }
//        });
//




        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });



        haveAccountB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this,login.class);
                startActivity(intent);
            }
        });


    }






    private void createAccount() {

        String Sname=Ename.getText().toString();
        String passwordd=Epassword.getText().toString();
        String Sphone=Ephone.getText().toString();

        if(TextUtils.isEmpty(Sname))
        {
            Toast.makeText(this,"请输入您的用户名",Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(Sphone))

        {
            Toast.makeText(this,"请输入您的手机号",Toast.LENGTH_LONG).show();
        }

        else if (!Phone_PATTERN.matcher(Sphone).matches())

        {Toast.makeText(this,"手机格式不对",Toast.LENGTH_LONG).show(); }

        else if (TextUtils.isEmpty(passwordd))

        {Toast.makeText(this,"请输入您的密码",Toast.LENGTH_LONG).show();


        }



        else if (!PASSWORD_PATTERN.matcher(passwordd).matches())

        {Toast.makeText(this,"密码只少六位，还有不能有空格",Toast.LENGTH_LONG).show();


        }


        else
        {
            loading.setTitle("创建用户");
            loading.setMessage("请你稍等");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            validatePhoneNumber(Sname,Sphone,passwordd);
        }

    }

    private void validatePhoneNumber(final String Sname, final String Sphone, final String passwordd) {

        final DatabaseReference Ref;
        Ref= FirebaseDatabase.getInstance().getReference();
        //method of above
        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users")).child(Sphone).exists())
                {
                    HashMap<String,Object>userDataMap=new HashMap<>();
                    // the first phone is one of the parts in the category in the data fire  base ,the second one is the string name
                    userDataMap.put("name",Sname);
                    userDataMap.put("phone",Sphone);
                    userDataMap.put("password",passwordd);
                    Ref.child("Users").child(Sphone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(register.this,"创建成功",Toast.LENGTH_LONG);
                                loading.dismiss();
                                Intent intReg=new Intent(register.this,login.class);
                                startActivity(intReg);
                            }
                            else
                            {
                                loading.dismiss();
                                Toast.makeText(register.this,"网络不好",Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(register.this,"这个手机号"+Sphone+"已经存在",Toast.LENGTH_LONG);
                    loading.dismiss();
                    Toast.makeText(register.this,"用别的手机号",Toast.LENGTH_LONG);

                    Intent intReg=new Intent(register.this,MainActivity.class);
                    startActivity(intReg);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
