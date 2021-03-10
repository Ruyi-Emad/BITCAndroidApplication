package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haizi.preValent.preValent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Settings2 extends AppCompatActivity {


    private Button save;
    private EditText nameText, phoneEdit, id, pass, classId, major;
    private CircleImageView profileImageView;
    private TextView changePic, close;

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


    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);


        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");

        save = (Button) findViewById(R.id.save);

        nameText = (EditText) findViewById(R.id.settings_full_name);
        phoneEdit = (EditText) findViewById(R.id.settings_phone);
        classId = (EditText) findViewById(R.id.settings_classId);
        id = (EditText) findViewById(R.id.settings_id);
        pass = (EditText) findViewById(R.id.settings_password);
        major = (EditText) findViewById(R.id.settings_major);

        profileImageView = (CircleImageView) findViewById(R.id.circleImageView);


        changePic = (TextView) findViewById(R.id.changePic);
        close = (TextView) findViewById(R.id.close);


        userInfoDisplay(nameText, phoneEdit, classId, id, pass, major, profileImageView);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings2.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")) {
                    userInfoSaved();
                } else {
                    updateOnlyUserInfo();
                }
            }
        });

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(Settings2.this);

//                Intent intent = CropImage.activity()
//                        .setAspectRatio(16, 9)
//                        .getIntent(Settings2.this);
//
//                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", nameText.getText().toString());
        userMap.put("phoneOrder", phoneEdit.getText().toString());
        userMap.put("major", major.getText().toString());
        userMap.put("password", pass.getText().toString());
        userMap.put("studentId", id.getText().toString());
        userMap.put("classNum", classId.getText().toString());


        ref.child(preValent.onLineUser.getPhone()).updateChildren(userMap);


        startActivity(new Intent(Settings2.this, HomeActivity.class));
        Toast.makeText(Settings2.this, " 成功 ", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        } else
        {
            Toast.makeText(this, "错误 ，再试试", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(Settings2.this, Settings2.class));
            finish();
        }

    }


    private void userInfoSaved() {

        String password=pass.getText().toString();
        String phone=phoneEdit.getText().toString();

        if (TextUtils.isEmpty(nameText.getText().toString())) {
            Toast.makeText(Settings2.this, "请输入名字", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phoneEdit.getText().toString())) {
            Toast.makeText(Settings2.this, "请输入手机号", Toast.LENGTH_SHORT).show();

        } else if (!Phone_PATTERN.matcher(phone).matches())

        { Toast.makeText(this,"手机格式不对,手机号是11位",Toast.LENGTH_LONG).show();


        } else if (TextUtils.isEmpty(id.getText().toString())) {
            Toast.makeText(Settings2.this, "请输入学号", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass.getText().toString()))
        {
            Toast.makeText(Settings2.this, "请输入密码", Toast.LENGTH_SHORT).show();
        }
        else if (!PASSWORD_PATTERN.matcher(password).matches())

        {Toast.makeText(this,"密码只少六位，还有不能有空格",Toast.LENGTH_LONG).show(); }
        else if (TextUtils.isEmpty(classId.getText().toString())) {
            Toast.makeText(Settings2.this, "请输入班级号", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(major.getText().toString())) {
            Toast.makeText(Settings2.this, "请输入专业", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(Settings2.this);
        progressDialog.setTitle("设置成功");
        progressDialog.setMessage("请你稍等，在升级过程中");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(preValent.onLineUser.getPhone() + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        //name, phone, password, image, studentId, classNum, major
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("name", nameText.getText().toString());
                        userMap.put("phoneOrder", phoneEdit.getText().toString());
                        userMap.put("major", major.getText().toString());
                        userMap.put("password", pass.getText().toString());
                        userMap.put("studentId", id.getText().toString());
                        userMap.put("classNum", classId.getText().toString());
                        userMap.put("image", myUrl);

                        ref.child(preValent.onLineUser.getPhone()).updateChildren(userMap);
                        progressDialog.dismiss();

                        startActivity(new Intent(Settings2.this, HomeActivity.class));
                        Toast.makeText(Settings2.this, "设置成功 ", Toast.LENGTH_SHORT).show();
                        finish();


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Settings2.this, "错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(Settings2.this, "图呢 ?", Toast.LENGTH_SHORT).show();
        }


    }


    private void userInfoDisplay(final EditText nameText, final EditText phone, final EditText classId, final EditText id, final EditText pass, final EditText major, final CircleImageView profileImageView) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(preValent.onLineUser.getPhone());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("image").exists()) {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String nameStu = dataSnapshot.child("name").getValue().toString();
                        String phoneStu = dataSnapshot.child("phone").getValue().toString();
                        String classIdStu = dataSnapshot.child("classNum").getValue().toString();
                        String IdStu = dataSnapshot.child("studentId").getValue().toString();
                        String passStu = dataSnapshot.child("password").getValue().toString();
                        String majorStu = dataSnapshot.child("major").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);
                        nameText.setText(nameStu);
                        phone.setText(phoneStu);
                        classId.setText(classIdStu);
                        id.setText(IdStu);
                        pass.setText(passStu);
                        major.setText(majorStu);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
