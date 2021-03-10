package com.example.haizi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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
import com.example.haizi.ViewHolder.food_type_Adapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class add_food_type_list extends AppCompatActivity {


    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private TextView closeBtn ;
    private Button uploadBtn, sendBtn;
    private EditText plate_description;
    private EditText plate_name;
    private EditText plate_price;
    private EditText howManyPersonCanEatIt;
    private String saveCurrentDate;
    private String saveCurrentTime;

    private ImageView PlatePhoto;
    private ProgressBar progressBar;

    private static final int pick_image_request =1;
    private Uri imageUri;

    private String name ,description ,  howManyPersons;
    private String price;

    private ProgressDialog loadingBar;
    private food_type adap;
    private String productRandomKey;
    private String CategoryName;
    private String downloadImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_type_list);



        CategoryName = getIntent().getExtras().get("category").toString();
        storageRef = FirebaseStorage.getInstance().getReference("food type").child("images");
        databaseRef = FirebaseDatabase.getInstance().getReference("food_list_model");

        uploadBtn = (Button) findViewById(R.id.uploadPhoto);
        sendBtn = (Button) findViewById(R.id.sendButton);
        plate_description = (EditText) findViewById(R.id.plate_description);
        plate_name = (EditText) findViewById(R.id.plate_name);

        plate_price = (EditText) findViewById(R.id.plate_price);
        howManyPersonCanEatIt = (EditText) findViewById(R.id.howManyPersonCanEatIt);
        PlatePhoto = (ImageView) findViewById(R.id.plate_image);
        closeBtn = (TextView) findViewById(R.id.close);
        progressBar = new ProgressBar(this);
        loadingBar = new ProgressDialog(this);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGAllery();
            }
        });






        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatemethod();

            }
        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_food_type_list.this, food_type.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void validatemethod()
    {      description = plate_description.getText().toString();
        name = plate_name.getText().toString();
        price = plate_price.getText().toString();
        howManyPersons = howManyPersonCanEatIt.getText().toString();

        if (imageUri == null)
        {
            Toast.makeText(this, "请你上载图", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "请你输入菜描述...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(price))
        {
            Toast.makeText(this, "请你输入菜价格...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "请你输入菜名字...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(howManyPersons))
        {
            Toast.makeText(this, "请你输入几个人能吃...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            sendImage();
        }
    }


    private void openGAllery() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,pick_image_request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == pick_image_request && resultCode == RESULT_OK && data != null && data.getData() != null)
            imageUri = data.getData();
        PlatePhoto.setImageURI(imageUri);
    }

/////////////////////////////////////////////////////////////////////////////////////

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr =getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    private void sendImage()
    {
        loadingBar.setTitle("添加菜");
        loadingBar.setMessage("教师，请您稍等 ！");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = storageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        if(imageUri != null)
        {
            StorageReference fileReference =storageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");
            fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);

                        }
                    },500);
                    Toast.makeText(add_food_type_list.this,"图上载好了 ！",Toast.LENGTH_LONG).show();



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

                                Toast.makeText(add_food_type_list.this, "菜图添加成功", Toast.LENGTH_SHORT).show();

                                SaveProductInfoToDatabase();
                            }
                        }
                    });





                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(add_food_type_list.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }




            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);


                }
            });
        }

        else {
            Toast.makeText(add_food_type_list.this, "图没上载好 ！" , Toast.LENGTH_SHORT).show();

        }
    }



    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("id", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("foodDescription", description);
        productMap.put("imageUri", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("foodName", name);
        productMap.put("foodPrice", price);
        productMap.put("howManyPersonsCanEatIt", howManyPersons);






        databaseRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(add_food_type_list.this, food_type.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(add_food_type_list.this, "添加成功 ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(add_food_type_list.this, "错误 。。" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        getIncomingIntent();
    }


    private void getIncomingIntent()
    {
        if (getIntent().hasExtra("category") )
        {
            String cate = getIntent().getStringExtra("category");
        }
    }
}



