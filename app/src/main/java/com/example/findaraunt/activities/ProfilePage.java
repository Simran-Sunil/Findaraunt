package com.example.findaraunt.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findaraunt.R;
import com.firebase.ui.auth.ui.ProgressDialogHolder;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfilePage extends AppCompatActivity {

    private static final String TAG = "Profile Page";

    CircleImageView profileImg;
    TextView username, email, location, phoneno;
    Button update;
    TextView cameraBtn;

    FirebaseFirestore db;
    FirebaseAuth authUser;
    FirebaseUser auth;
    FirebaseStorage storage;
    FirebaseDatabase database;

    //added
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(TAG, "cc started Profile Display Screen.");

        username = findViewById(R.id.userNameId);
        email = findViewById(R.id.emailId);
        location = findViewById(R.id.addressId);
        phoneno = findViewById(R.id.phonenoId);
        profileImg = findViewById(R.id.circular_view);
        update = findViewById(R.id.btnUpdate);
        cameraBtn = findViewById(R.id.cameraBtn);

        //added
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfilePic();
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1, 1).start(ProfilePage.this);
            }
        });

        getUserinfo();
    }

    private void getUserinfo(){
//        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists() && snapshot.getChildrenCount()>0){
//
//                    String name = snapshot.child("username").getValue().toString();
//                    username.setText(name);
//
//                    String emailId = snapshot.child("email").getValue().toString();
//                    email.setText(emailId);
//
//                    String address = snapshot.child("location").getValue().toString();
//                    location.setText(address);
//
//                    String phoneNo = snapshot.child("phoneno").getValue().toString();
//                    phoneno.setText(phoneNo);
//                   // String
//                 //   username.setText((CharSequence) document.get("username"));
////                                email.setText((CharSequence) document.get("email"));
////                                location.setText((CharSequence) document.get("location"));
////                                phoneno.setText((CharSequence) document.get("phoneno"));
//
//                    if(snapshot.hasChild("image"))
//                    {
//                        String image = snapshot.child("image").getValue().toString();
//                        Picasso.get().load(image).into(profileImg);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        db = FirebaseFirestore.getInstance();
        authUser = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance().getCurrentUser();
       String currentEmail = auth.getEmail();
                db.collection("Users").whereEqualTo("email", currentEmail).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                username.setText((CharSequence) document.get("username"));
                                email.setText((CharSequence) document.get("email"));
                                location.setText((CharSequence) document.get("location"));
                                phoneno.setText((CharSequence) document.get("phoneno"));

                                if (document.get("profileImg") != null) {
                                    Picasso.get().load((String) document.get("imgUrl")).into(profileImg);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);

    if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        imageUri = result.getUri();

        profileImg.setImageURI(imageUri);
    }else{
        Toast.makeText(this,"Error ,Try again", Toast.LENGTH_SHORT).show();
    }
    }

    private void uploadProfilePic(){
        if(imageUri != null){
            final StorageReference fileRef = storageProfilePicsRef.child(mAuth.getCurrentUser().getUid()+".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image",myUri);

                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
                    }
                }
            });
        }else{
            Toast.makeText(this, "Image not selected",Toast.LENGTH_SHORT).show();
        }
    }
}

//        db = FirebaseFirestore.getInstance();
//        authUser = FirebaseAuth.getInstance();
//        auth = FirebaseAuth.getInstance().getCurrentUser();
//        String currentEmail = auth.getEmail();

//        db.collection("Users").whereEqualTo("email", currentEmail).get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                username.setText((CharSequence) document.get("username"));
//                                email.setText((CharSequence) document.get("email"));
//                                location.setText((CharSequence) document.get("location"));
//                                phoneno.setText((CharSequence) document.get("phoneno"));
//
//                                if (document.get("profileImg") != null) {
//                                    Picasso.get().load((String) document.get("imgUrl")).into(profileImg);
//                                }
//                            }
//                        } else {
//                            Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                });
//
//        cameraBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                //startActivityForResult(intent, 33);
//                someActivityResultLauncher.launch(intent);
//            }
//        });
//
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateUserProfile();
//            }
//        });
//
//    }
//

//
//    private void updateUserProfile() {
//        authUser.signOut();
//        Intent intent = new Intent(this, LoginPage.class);
//        Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//    }
//
//
//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Intent data = result.getData();
//                    if (data.getData() != null) {
//                        Uri profileUri = data.getData();
//                        profileImg.setImageURI(profileUri);
//
//                        final StorageReference reference = storage.getReference().child("profile_picture")
//                                .child(FirebaseAuth.getInstance().getUid());
//
//                        reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                Toast.makeText(ProfilePage.this, "uploaded ", Toast.LENGTH_SHORT).show();
//
//                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                    @Override
//                                    public void onSuccess(Uri uri) {
//
//                                        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                                                .child("profileImg").setValue(uri.toString());
//                                        Toast.makeText(ProfilePage.this, "Profile pic Uploaded", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        });
//                    }
//                }
//            });
//}
