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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haizi.Model.Users;
import com.example.haizi.preValent.preValent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class login extends AppCompatActivity {
    private Button btnLogin;
    private EditText EPhone,Epassword;
    private TextView student,teacher;
    private ProgressDialog loading;

    private String parentDbName="Users";

    private CheckBox checkBoxRemeberMe ;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=(Button) findViewById(R.id.login);
        EPhone=(EditText)findViewById(R.id.phone);
        Epassword=(EditText)findViewById(R.id.password);
        loading=new ProgressDialog(this);

        student=(TextView)findViewById(R.id.student);
        teacher=(TextView)findViewById(R.id.teacher);

        checkBoxRemeberMe=(CheckBox)findViewById(R.id.checkboxRemember);

        Paper.init(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在函数里写您想做的

                loginUser();
            }
        });

        //i am teacher text view  method 4.4.2020
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setText("教师登录");
                teacher.setVisibility(View.INVISIBLE);
                student.setVisibility(View.VISIBLE);
                parentDbName="Admins";

            }
        });
        // i am student text view  method
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setText("学生登录");
                student.setVisibility(View.INVISIBLE);
                teacher.setVisibility(View.VISIBLE);
                parentDbName = "Users";

            }
        });

    }

    private void loginUser() {
        String phone=EPhone.getText().toString();
        String password=Epassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this,"请输入您的用户名",Toast.LENGTH_LONG).show();
        }

        else if (!Phone_PATTERN.matcher(phone).matches())

        {Toast.makeText(this,"手机格式不对,手机号是11位",Toast.LENGTH_LONG).show(); }

        else if (TextUtils.isEmpty(password))
            Toast.makeText(this,"请输入您的密码",Toast.LENGTH_LONG).show();


        else if (!PASSWORD_PATTERN.matcher(password).matches())

        {Toast.makeText(this,"密码只少六位，还有不能有空格",Toast.LENGTH_LONG).show(); }

        else
        {
            loading.setTitle("正在登录");
            loading.setMessage("请你稍等");
            loading.setCanceledOnTouchOutside(false);
            loading.show();

            allowAccessToAccount(phone,password);
        }


    }

    private void allowAccessToAccount(final String phone, final String password) {

        //check box method
        if(checkBoxRemeberMe.isChecked())
        {
            Paper.book().write(preValent.userPhoneKey,phone);
            Paper.book().write(preValent.userPasswordKey,password);
        }

// firebse data Reference

        final DatabaseReference Ref;
        Ref= FirebaseDatabase.getInstance().getReference();

        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {

                    Users userData=dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(userData.getPhone().equals(phone))
                    {
                        if(userData.getPassword().equals(password))
                        {

                            if(parentDbName.equals("Admins"))
                            {
                                Toast.makeText(login.this, "教师，欢迎", Toast.LENGTH_SHORT).show();
                                loading.dismiss();

                                Intent intent = new Intent(login.this, adminTongzhi.class);
                                startActivity(intent);
                            }
                            else if(parentDbName.equals("Users"))
                            {

                                Toast.makeText(login.this,"学生登录成功",Toast.LENGTH_LONG).show();
                                loading.dismiss();

                                Intent intLog=new Intent(login.this,HomeActivity.class);
                                preValent.onLineUser=userData;
                                startActivity(intLog);
                            }
                        }
                        else
                        {
                            loading.dismiss();
                            Toast.makeText(login.this,"密码错误",Toast.LENGTH_LONG).show();
                        }}

                }
                else
                {
                    Toast.makeText(login.this,"用户不存在",Toast.LENGTH_LONG).show();
                    loading.dismiss();
//
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
//  i found a mistake when i was logging in the Admins ,through firebase databse ,as we are adding the Admins through the firebase it wasnot work as
// we should adding the child value between "  " , because itis valuse is String



//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseRecyclerOptions<Notifications> options =
//                new FirebaseRecyclerOptions.Builder<Notifications>()
//                        .setQuery(NotificationRef, Notifications.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<Notifications, NotificationAdapter.NotificationViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Notifications,NotificationAdapter.NotificationViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position, @NonNull Notifications model)
//                    {
//                        holder.cardNotificationLines.setText(model.getLines());
//                        Picasso.get().load(model.getImageUri()).into(holder.cardNotificationImage);
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//                    {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
//                        NotificationAdapter holder = new NotificationAdapter(view);
//                        return holder;
//                    }
//                };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//    }


//        NotificationRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
//                    Notifications notifications = postSnapShot.getValue(Notifications.class);
//                    notificationsList.add(notifications);
//                }
//                adapterObj = new NotificationAdapter(HomeActivity.this, notificationsList);
//                recyclerView.setAdapter(adapterObj);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(HomeActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });

//     }
//