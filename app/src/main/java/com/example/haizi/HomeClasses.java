package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haizi.Model.Users;
import com.example.haizi.Model.classesModel;
import com.example.haizi.preValent.preValent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeClasses extends AppCompatActivity {

    private TextView closebtn;
    private TextView classNum ,classBoss , firstTime , secTime ,firstNotes ,secondNotes;
    private ImageView firstPHoto , secPhoto ;
    private String nn="";
    private String classId ="";

    private  adminAddClasses cc;
    String classNumgetExtra ;
    private FirebaseDatabase Hfirebase;
    private ProgressDialog loading;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_classes);

        closebtn = (TextView) findViewById(R.id.close);
        classNum = (TextView) findViewById(R.id.classNumber);
        classBoss =(TextView) findViewById(R.id.classBossName);
        firstTime = (TextView)findViewById(R.id.firstSemTime);
        secTime = (TextView) findViewById(R.id.secSemTime);
        firstPHoto = (ImageView) findViewById(R.id.firstSemPhoto);
        secPhoto = (ImageView) findViewById(R.id.secSemPhoto);
        loading=new ProgressDialog(this);
        firstNotes = (TextView) findViewById(R.id.firstSemNotes);
        secondNotes =(TextView) findViewById(R.id.SecSemNotes);

        nn = getIntent().getStringExtra("number");

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeClasses.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        String classId = FirebaseDatabase.getInstance().getReference().child("classNum")
//                            .setValue(usermodel.getClassNum()).toString();




        try {
            DatabaseReference classRef = FirebaseDatabase.getInstance().getReference()
                    .child("Classes").child(preValent.onLineUser.getClassNum());

            classRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//                    String Num =dataSnapshot.child("classNum").getValue().toString();
//                    classNum.setText(Num);
                        classesModel model = dataSnapshot.getValue(classesModel.class);

                        classNum.setText(model.getClassNum());
                        classBoss.setText(model.getClassBoss());
                        firstTime.setText(model.getFirstDate());
                        firstNotes.setText(model.getFirstSemesterNotes());
                        Picasso.get().load(model.getFirstSemImage()).into(firstPHoto);

                        //secondNotes.setText(model.getSecondSemesterNotes());
                        Picasso.get().load(model.getSecSemImage()).into(secPhoto);

                        secTime.setText(model.getSecondDate());


                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });

        }


        catch (Exception e) {
            Toast.makeText(HomeClasses.this,"请您在设置页写您的班级号",Toast.LENGTH_SHORT).show();
            loading.setTitle("注意");
            loading.setMessage("请您在设置页写您的班级号");
            loading.setCanceledOnTouchOutside(false);
            loading.show();
            Intent intent = new Intent(HomeClasses.this, HomeActivity.class);
            startActivity(intent);
        }


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        DatabaseReference classRef = FirebaseDatabase.getInstance().getReference()
//                .child("Classes").child(preValent.onLineUser.getClassNum());
//
//        if (classRef.equals(null)) {
//
//            Toast.makeText(HomeClasses.this, "请您在设置页写您的班级号 ", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(HomeClasses.this, HomeActivity.class);
//            startActivity(intent);
//
//        } else{
//            classRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
////                    String Num =dataSnapshot.child("classNum").getValue().toString();
////                    classNum.setText(Num);
//                        classesModel model = dataSnapshot.getValue(classesModel.class);
//
//                        classNum.setText(model.getClassNum());
//                        classBoss.setText(model.getClassBoss());
//                        firstTime.setText(model.getFirstDate());
//                        firstNotes.setText(model.getFirstSemesterNotes());
//                        Picasso.get().load(model.getFirstSemImage()).into(firstPHoto);
//
//                        //secondNotes.setText(model.getSecondSemesterNotes());
//                        Picasso.get().load(model.getSecSemImage()).into(secPhoto);
//
//                        secTime.setText(model.getSecondDate());
//
//
//                    }
//                }
//
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//
//
//            });
//
//    }
//
//        }

//        DatabaseReference classRef2 =FirebaseDatabase.getInstance().getReference()
//                .child("Classes").child(nn);
//        classRef2.child("Second Semester").child("May 17, 202005:20:37 AM").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists())
//                {
////                    String Num =dataSnapshot.child("classNum").getValue().toString();
////                    classNum.setText(Num);
//                    classesModel model = dataSnapshot.getValue(classesModel.class);
//                     secondNotes.setText(model.getSecondSemesterNotes());
//                    secTime.setText(model.getSecondDate());
//                    Picasso.get().load(model.getFirstSemImage()).into(secPhoto);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });



}
