//package com.example.haizi;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import de.hdodenhof.circleimageview.CircleImageView;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.haizi.preValent.preValent;
//import com.google.android.gms.tasks.Continuation;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;
//import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;
//
//import java.util.HashMap;
//import static android.app.Activity.RESULT_OK;
//
//public class settings  extends Fragment {
//
//   // public static final int RESULT_OK = -1;
//    private Button save ;
//    private EditText name , phone , id , pass , classId , major ;
//    private CircleImageView profileImageView ;
//    private TextView changePic, close ;
//
//
//
//    private Uri imageUri;
//    private String myUrl = "";
//    private StorageTask uploadTask;
//    private StorageReference storageProfilePrictureRef;
//    private String checker = "";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_settings, container, false);
//
//
//        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");
//
//        save = (Button) view.findViewById(R.id.save);
//
//        name = (EditText) view.findViewById(R.id.settings_full_name);
//        phone = (EditText) view.findViewById(R.id.settings_phone);
//        classId = (EditText) view.findViewById(R.id.settings_classId);
//        id = (EditText) view.findViewById(R.id.settings_id);
//        pass = (EditText) view.findViewById(R.id.settings_password);
//        major = (EditText) view.findViewById(R.id.settings_major);
//
//        profileImageView = (CircleImageView) view.findViewById(R.id.circleImageView);
//
//
//        changePic = (TextView) view.findViewById(R.id.changePic);
//        close = (TextView) view.findViewById(R.id.close);
//
//
//        userInfoDisplay(name , phone , classId, id , pass , major , profileImageView);
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),HomeActivity.class);
//                startActivity(intent);
//           getActivity();
//                //   .finish();
//            }
//        });
//
//
//    save.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v)
//        {
//            if (checker.equals("clicked"))
//            {
//                userInfoSaved();
//            }
//            else
//            {
//                updateOnlyUserInfo();
//            }
//        }
//    });
//
//    changePic.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v)
//        {
//            checker = "clicked";
////            CropImage.activity(imageUri)
////                    .setAspectRatio(1, 1)
////                    .start(getActivity());
//
//
//            Intent intent = CropImage.activity()
//                    .setAspectRatio(16,9)
//                    .getIntent(getContext());
//
//            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
//
//        }
//    });
//
//
//        return view;
//
//    }
//
//    private void updateOnlyUserInfo()
//    {
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
//
//        HashMap<String, Object> userMap = new HashMap<>();
//        userMap. put("name", name.getText().toString());
//        userMap. put("phoneOrder", phone.getText().toString());
//        userMap. put("major", major.getText().toString());
//        userMap. put("password", pass.getText().toString());
//        userMap. put("studentId", id.getText().toString());
//        userMap. put("classNum", classId.getText().toString());
//
//
//        ref.child(preValent.onLineUser.getPhone()).updateChildren(userMap);
//
//
//        startActivity(new Intent(getActivity(), HomeActivity.class));
//        Toast.makeText(getActivity(), "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
//        getActivity();
//        ///////////////////////////////////////////.finish();
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        //super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
//        {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUri = result.getUri();
//
//            profileImageView.setImageURI(imageUri);
//        }
//        else
//        {
//            Toast.makeText(getActivity(), "Error, Try Again.", Toast.LENGTH_SHORT).show();
//
//            startActivity(new Intent(getActivity(), settings.class));
//            getActivity();
//            ///////////////////////////////.finish();
//        }
//
//    }
//
//
//
//    private void userInfoSaved()
//    {
//        if (TextUtils.isEmpty(name.getText().toString()))
//        {
//            Toast.makeText(getActivity(), "请输入名字", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(phone.getText().toString()))
//        {
//            Toast.makeText(getActivity(), "请输入手机号", Toast.LENGTH_SHORT).show();
//        }
//
//        else if (TextUtils.isEmpty(id.getText().toString()))
//        {
//            Toast.makeText(getActivity(), "请输入学号", Toast.LENGTH_SHORT).show();
//        }
//
//        else if (TextUtils.isEmpty(pass.getText().toString()))
//        {
//            Toast.makeText(getActivity(), "请输入密码", Toast.LENGTH_SHORT).show();
//        }
//
//        else if (TextUtils.isEmpty(classId.getText().toString()))
//        {
//            Toast.makeText(getActivity(), "请输入班级号", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(major.getText().toString()))
//        {
//            Toast.makeText(getActivity(), "请输入专业", Toast.LENGTH_SHORT).show();
//        }
//
//        else if(checker.equals("clicked"))
//        {
//            uploadImage();
//        }
//    }
//
//    private void uploadImage()
//    {
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle("Update Profile pic");
//        progressDialog.setMessage("请你稍等，在升级过程中");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        if (imageUri != null)
//        {
//            final StorageReference fileRef = storageProfilePrictureRef
//                    .child(preValent.onLineUser.getPhone() + ".jpg");
//            uploadTask = fileRef.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception
//                {
//                    if (!task.isSuccessful())
//                    {
//                        throw task.getException();
//                    }
//                    return fileRef.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful())
//                    {
//                        Uri downloadUrl = task.getResult();
//                        myUrl = downloadUrl.toString();
//
//                        //name, phone, password, image, studentId, classNum, major
//                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
//                        HashMap<String, Object> userMap = new HashMap<>();
//                        userMap. put("name", name.getText().toString());
//                        userMap. put("phoneOrder", phone.getText().toString());
//                        userMap. put("major", major.getText().toString());
//                        userMap. put("password", pass.getText().toString());
//                        userMap. put("studentId", id.getText().toString());
//                        userMap. put("classNum", classId.getText().toString());
//                        userMap. put("image", myUrl);
//
//                        ref.child(preValent.onLineUser.getPhone()).updateChildren(userMap);
//                        progressDialog.dismiss();
//
//                        startActivity(new Intent(getActivity(), HomeActivity.class));
//                        Toast.makeText(getActivity(), "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
//                        getActivity();
//                        ////////////////////////.finish();
//
//                    }
//                    else
//                        {
//                            progressDialog.dismiss();
//                            Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
//                        }
//                }
//            });
//        }
//        else
//        {
//            Toast.makeText(getActivity(),"图呢 ?", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//
//
//
//    private void userInfoDisplay(final EditText name, final EditText phone, final EditText classId, final EditText id, final EditText pass, final EditText major, final CircleImageView profileImageView)
//    {
//        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(preValent.onLineUser.getPhone());
//
//        UsersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if (dataSnapshot.exists())
//                {
//                    if (dataSnapshot.child("image").exists())
//                    {
//                        String image =dataSnapshot.child("image").getValue().toString();
//                        String nameStu =dataSnapshot.child("name").getValue().toString();
//                        String phoneStu =dataSnapshot.child("phone").getValue().toString();
//                        String classIdStu =dataSnapshot.child("classNum").getValue().toString();
//                        String IdStu =dataSnapshot.child("studentId").getValue().toString();
//                        String passStu =dataSnapshot.child("password").getValue().toString();
//                        String majorStu =dataSnapshot.child("major").getValue().toString();
//
//                        Picasso.get().load(image).into(profileImageView);
//                        name.setText(nameStu);
//                        phone.setText(phoneStu);
//                        classId.setText(classIdStu);
//                        id.setText(IdStu);
//                        pass.setText(passStu);
//                        major.setText(majorStu);
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//
//            }
//        });
//
//
//    }
//}
