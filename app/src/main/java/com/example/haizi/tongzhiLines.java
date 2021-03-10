package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haizi.Model.Notifications;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class tongzhiLines extends AppCompatActivity {

    private ImageView uploadedPhoto;
    private EditText notificationLines;
    private Button SendButton;
    private TextView closebtn;

    private static final int GallerPick = 1;
    private Uri IamgeUri;
    private String notifyLines, saveCurrentDate, saveCurrentTime;
    private String productRandomKey;
    private Button choosePhotoBtn;
    private String notificationCenter ="";
    private String downloadImageUrl;
    private ImageView homebtn;
    private ProgressDialog loadingBar;
    private DatabaseReference databaseRef;
    private StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongzhi_lines);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
        {
            notificationCenter =getIntent().getExtras().get("NotificationBank").toString();
        }

        // notificationCenter = getIntent().getExtras().get("NotificationBank").toString();
        storageRef = FirebaseStorage.getInstance().getReference("Notifications").child("Notifications image");
        databaseRef = FirebaseDatabase.getInstance().getReference("Notifications");
//
        uploadedPhoto = (ImageView) findViewById(R.id.notificationPhoto);
        notificationLines = (EditText) findViewById(R.id.notificationLines);
        SendButton = (Button) findViewById(R.id.sendButton);
        choosePhotoBtn = (Button) findViewById(R.id.choosePhoto) ;
        loadingBar = new ProgressDialog(this);

        homebtn = (ImageView) findViewById(R.id.homess);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();



        closebtn = (TextView) findViewById(R.id.close);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(tongzhiLines.this ,HomeActivity.class);
                intent7.putExtra("Admin","Admin");
                startActivity(intent7);
            }
        });


        choosePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatemethod();
            }
        });
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tongzhiLines.this, adminTongzhi.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void openGallery()
    {
        Intent galleryIntent=new Intent();

        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent,GallerPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GallerPick && resultCode == RESULT_OK && data.getData() != null) {
            IamgeUri = data.getData();
            uploadedPhoto.setImageURI(IamgeUri);


        }
    }

    private String getfileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap Mime = MimeTypeMap.getSingleton();
        return Mime.getExtensionFromMimeType(cR.getType(uri));

    }

    private void validatemethod()
    {
        notifyLines = notificationLines.getText().toString();

        if (IamgeUri == null)
        {
            Toast.makeText(this, "请你下载图片", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(notifyLines))
        {
            Toast.makeText(this, "请你写通知", Toast.LENGTH_LONG).show();
        }
        else
        {
            sendNotification();
        }


    }

    private void sendNotification()
    {
        loadingBar.setTitle("添加通知");
        loadingBar.setMessage("请老师稍等，通知在添加");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = storageRef.child(IamgeUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(IamgeUri);

        if(IamgeUri != null)
        {
            StorageReference fileReference =storageRef.child(IamgeUri.getLastPathSegment() + productRandomKey + ".jpg");
            fileReference.putFile(IamgeUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingBar.setProgress(0);

                        }
                    },500);
                    Toast.makeText(tongzhiLines.this,"图上载好了 ！",Toast.LENGTH_LONG).show();



                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                        {
                            if (!task.isSuccessful())
                            {
                                throw task.getException();
                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(tongzhiLines.this, "通知图添加成功 ", Toast.LENGTH_SHORT).show();

                                saveNotificationToDatBase();
                            }
                        }
                    });





                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(tongzhiLines.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }




            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    loadingBar.setProgress((int)progress);


                }
            });
        }

        else {
            Toast.makeText(tongzhiLines.this, "通知添加失败了 ！" , Toast.LENGTH_SHORT).show();

        }
    }


    private void saveNotificationToDatBase()
    {
        HashMap<String, Object> productMap=new HashMap<>();
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("lines",notifyLines);
        productMap.put("id",productRandomKey);
        productMap.put("image",downloadImageUrl);

        databaseRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task){
                        if(task.isSuccessful()){

                            Intent intent=new Intent(tongzhiLines.this,adminTongzhi.class);
                            intent.putExtra("NotificationBank",Notifications.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(tongzhiLines.this,"通知添加好了",Toast.LENGTH_LONG).show();
                        }else{
                            loadingBar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(tongzhiLines.this,"错误"+message,Toast.LENGTH_LONG).show();

                        }


                    }

                });
    }



}


//// ...........................................................................................
//
//
//


// if (ImageUri != null ){
//
//           StorageReference fileReference = storageRef.child(System.currentTimeMillis()+"."+getfileExtension(ImageUri));
//           fileReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//               @Override
//               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
//               {
//                   Handler handler = new Handler();
//                   handler.postDelayed(new Runnable() {
//                       @Override
//                       public void run() {
//                           loading.setProgress(0);
//
//                       }
//                   },500);
//
//                   Toast.makeText(tongzhiLines.this,"图传上好了",Toast.LENGTH_LONG).show();
//                   Notifications uploadNotifications = new Notifications(notificationLines.getText().toString().trim(),taskSnapshot.toString());
//               }
//
//           }).addOnFailureListener(new OnFailureListener()
//           {
//               @Override
//               public void onFailure(@NonNull Exception e)
//               {
//                   Toast.makeText(tongzhiLines.this,e.getMessage(),Toast.LENGTH_LONG).show();
//               }
//
//           }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//               @Override
//               public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
//               {
//                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                    loading.setProgress((int)progress);
//               }
//           });
//
//       }
//
//       else {
//           Toast.makeText(tongzhiLines.this,"图没传好",Toast.LENGTH_LONG).show();
//       }
//
//    }



//    private Button choosePhotoBtn , sendPhotoBtn;
//    private ImageView notificationPhoto;
//
//    private String notificationCenter;
//
//    private EditText notificationLines;
//    private  static final int pick_image_Request = 1;
//    private Uri ImageUri;
//
//    private StorageReference storageRef;
//    private DatabaseReference databaseRef;
//
//    private ProgressBar loading;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tongzhi_lines);
//
//        choosePhotoBtn = (Button) findViewById(R.id.choosePhoto);
//        sendPhotoBtn = (Button) findViewById(R.id.sendButton);
//        notificationPhoto = (ImageView) findViewById(R.id.notificationPhoto);
//        notificationLines = (EditText) findViewById(R.id.notificationLines);
//
//        notificationCenter=getIntent().getExtras().get("category").toString();
//        storageRef = FirebaseStorage.getInstance().getReference("Notifications");
//        databaseRef = FirebaseDatabase.getInstance().getReference("Notifications");
//        loading = new ProgressBar(this);
//
//        choosePhotoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openChoosePhoto();
//            }
//        });
//
//
//        sendPhotoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadFile();
//            }
//        });
//
//
//    }
//
//
//    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//    private void openChoosePhoto() {
//
//        Intent intent =new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,pick_image_Request);
//
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == pick_image_Request && resultCode == RESULT_OK && data != null && data.getData() != null){
//            ImageUri = data.getData();
//            //Picasso.with(this).load(ImageUri);
//
//            notificationPhoto.setImageURI(ImageUri);
//        }
//
//    }
//    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//    // here we will create two methods
//    //First
//    private String getfileExtension(Uri uri){
//        ContentResolver cR = getContentResolver();
//        MimeTypeMap Mime = MimeTypeMap.getSingleton();
//        return Mime.getExtensionFromMimeType(cR.getType(uri));
//
//    }
//
//    // Second
//    private void uploadFile() {
//
//
//        if (ImageUri != null) {
//            storageRef.putFile(ImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//                    return storageRef.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        Uri downloadUri = task.getResult();
//
//
//                        Log.e("logt", "then: " + downloadUri.toString());
//
//
//                        Notifications upload = new Notifications(notificationLines.getText().toString().trim(), downloadUri.toString(), image, date, category);
//
//                        databaseRef.push().setValue(upload);
//                        String uploadId = databaseRef.push().getKey();
//                        databaseRef.child(uploadId).setValue(upload);
//
//                    } else {
//                        Toast.makeText(tongzhiLines.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//
//    }
//
//
//
//
//
//}

