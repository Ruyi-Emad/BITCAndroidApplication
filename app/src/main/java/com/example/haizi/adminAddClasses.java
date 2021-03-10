package com.example.haizi;
import com.google.firebase.storage.UploadTask;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class adminAddClasses extends AppCompatActivity {
    private ImageView firstPhoto, secPhoto;
    private Button firstSemBtn, secSemBtn, uploadBtn;
    private TextView closeBtn;
    private EditText firstNotesText, secNotesText, ClassBossText;


    private String ClassesChild, Notes1, Notes2, saveCurrentDate, saveCurrentTime, classBossName;

    private static final int Gallerypick1 = 1;
    private static final int Gallerypick2 = 2;

    private Uri ImageUri1, ImageUri2;
    private String classRandomKey;
    private String downloadImageUri1, downloadImageUri2;

    private ProgressDialog loadingBar;

    private StorageReference classImageStorageRef;
    private DatabaseReference classesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_classes);

        firstPhoto = (ImageView) findViewById(R.id.firstSemPhoto);
        secPhoto = (ImageView) findViewById(R.id.SecSemPhoto);

        firstNotesText = (EditText) findViewById(R.id.firstSemNotes);
        secNotesText = (EditText) findViewById(R.id.SecSemNotes);
        ClassBossText = (EditText) findViewById(R.id.classBoss);

        firstSemBtn = (Button) findViewById(R.id.firstSem);
        secSemBtn = (Button) findViewById(R.id.secSem);
        uploadBtn = (Button) findViewById(R.id.upload);
        closeBtn = (TextView) findViewById(R.id.close);

        loadingBar = new ProgressDialog(this);

        ClassesChild = getIntent().getExtras().get("Class 号").toString();

        classImageStorageRef = FirebaseStorage.getInstance().getReference().child("Classes Table");
        classesRef = FirebaseDatabase.getInstance().getReference().child("Classes");


        firstSemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery1();
            }
        });

        secSemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery2();
            }
        });


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUpdatedData();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminAddClasses.this, adminClasses.class);
//                intent.putExtra("classN",ClassesChild);
                startActivity(intent);
                finish();
            }
        });

    }


    private void OpenGallery1() {
        Intent galleryIntent1 = new Intent();
        galleryIntent1.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent1.setType("image/*");
        startActivityForResult(galleryIntent1, Gallerypick1);
    }

    private void OpenGallery2() {
        Intent galleryIntent2 = new Intent();
        galleryIntent2.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent2.setType("image/*");
        startActivityForResult(galleryIntent2, Gallerypick2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == Gallerypick1 && resultCode == RESULT_OK && data != null) {
            ImageUri1 = data.getData();
            firstPhoto.setImageURI(ImageUri1);

        }



        if(requestCode == Gallerypick2 && resultCode == RESULT_OK && data != null) {

            ImageUri2 = data.getData();
            secPhoto.setImageURI(ImageUri2);


        }
//        if (requestCode == Gallerypick1 && resultCode == RESULT_OK && data != null) {
//
//        }
// (requestCode == Gallerypick2 && resultCode == RESULT_OK && data != null) {
//            ImageUri2 = data.getData();
//            secPhoto.setImageURI(ImageUri2);
//        }




    }

    private void validateUpdatedData() {
        Notes1 = firstNotesText.getText().toString();
        Notes2 = secNotesText.getText().toString();
        classBossName = ClassBossText.getText().toString();

        if (ImageUri1 == null) {
            Toast.makeText(adminAddClasses.this, "请您选择第一个学期的课程表图", Toast.LENGTH_LONG).show();

        } else if (ImageUri2 == null) {
            Toast.makeText(adminAddClasses.this, "请您选择第二个学期的课程表图", Toast.LENGTH_LONG).show();

        } else if (TextUtils.isEmpty(classBossName)) {
            Toast.makeText(adminAddClasses.this, "请您写班主任名", Toast.LENGTH_LONG).show();

        } else {
            StoreClassesInformation();
        }


    }

    private void StoreClassesInformation() {
        loadingBar.setTitle("添加课程表");
        loadingBar.setMessage("教师请您稍等");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        String saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        String saveCurrentTime = currentTime.format(calForDate.getTime());

        classRandomKey = saveCurrentDate + saveCurrentTime;
        final StorageReference filePath = classImageStorageRef.child("First Semester").child(ImageUri1.getLastPathSegment()
                + classRandomKey + ".jpg");

        final StorageReference filePath2 = classImageStorageRef.child("Second Semester").child(ImageUri2.getLastPathSegment()
                + classRandomKey + ".jpg");

        final UploadTask uploadTask1 = filePath.putFile(ImageUri1);
        final UploadTask uploadTask2 = filePath2.putFile(ImageUri2);


        uploadTask1.addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(adminAddClasses.this, "错误 ：" + message, Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(adminAddClasses.this, "课程表添加成功了", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask1.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUri1 = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUri1 = task.getResult().toString();
                            Toast.makeText(adminAddClasses.this, "请您选择第一个学期的课程表图", Toast.LENGTH_LONG).show();
                            SaveClassesInfoToDataBase1();
                        }
                    }

                });
            }

        });


        uploadTask2.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(adminAddClasses.this, "错误 ：" + message, Toast.LENGTH_LONG).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(adminAddClasses.this, "课程表添加成功了", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloadImageUri2 = filePath2.getDownloadUrl().toString();
                        return filePath2.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUri2 = task.getResult().toString();
                            Toast.makeText(adminAddClasses.this, "请您选择第二个学期的课程表图", Toast.LENGTH_LONG).show();
                            SaveClassesInfoToDataBase1();
                        }
                    }

                });
            }

        });

    }


    private void SaveClassesInfoToDataBase1() {
        HashMap<String, Object> classMap = new HashMap<>();
        classMap.put("classId", classRandomKey);
        classMap.put("firstDate", saveCurrentDate);
        classMap.put("firstTime", saveCurrentTime);
        classMap.put("classBoss", classBossName);
        classMap.put("firstSemImage", downloadImageUri1);
        classMap.put("classNum", ClassesChild);
        classMap.put("FirstSemesterNotes", Notes1);
        classMap.put("SecondSemesterNotes", Notes2);
        classMap.put("secSemImage", downloadImageUri2);

        classesRef.child(ClassesChild).updateChildren(classMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(adminAddClasses.this, adminClasses.class);
                            startActivity(intent);
                            loadingBar.dismiss();
                        }
                    }
                });


    }

//    String classId , classBoss ,firstSemImage ,classNum ,FirstSemesterNotes;
//    String    secSemImage,SecondSemesterNotes;


//    private void SaveClassesInfoToDataBase2()
//    {
//        HashMap<String, Object> classMap2 = new HashMap<>();
//        classMap2.put("classId", classRandomKey);
//        classMap2.put("SecondDate", saveCurrentDate);
//        classMap2.put("SecondTime", saveCurrentTime);
//        classMap2.put("classBoss", classBossName);
//
//        classMap2.put("classNum", ClassesChild);
//
//
//        classesRef.child(ClassesChild).child("Second Semester").child(classRandomKey).updateChildren(classMap2)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Intent intent = new Intent(adminAddClasses.this, adminClasses.class);
//                            startActivity(intent);
//                            loadingBar.dismiss();
//                        }
//                    }
//                });
//    }
}
