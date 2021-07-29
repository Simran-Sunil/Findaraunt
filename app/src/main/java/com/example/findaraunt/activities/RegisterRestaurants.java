package com.example.findaraunt.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.findaraunt.R;
import com.example.findaraunt.models.RRegistrationDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterRestaurants extends AppCompatActivity {

    private static final String TAG = "Register Restaurants";

    CircleImageView hotelImageView;
    EditText name, address, phoneno, ratings, timings;
    Spinner category;
    Button submitBtn;
    TextView cameraBtn;

    private Uri imageUri;
    private static final int PICK_IMAGE = 1;
    UploadTask uploadTask;

    FirebaseAuth auth;
    FirebaseStorage storage;
    private FirebaseFirestore database;
    StorageReference storageReference;
    FirebaseDatabase db;
    CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_restaurants);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        hotelImageView = findViewById(R.id.circular_view);
        name = findViewById(R.id.inputName);
        category = (Spinner) findViewById(R.id.inputCategory);
        address = findViewById(R.id.inputAddress);
        phoneno = findViewById(R.id.inputPhone);
        timings = findViewById(R.id.inputTime);
        ratings = findViewById(R.id.inputRating);
        submitBtn = findViewById(R.id.btnSubmit);

        db.getReference().child("Restaurants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RRegistrationDetails rRegistrationDetails = snapshot.getValue(RRegistrationDetails.class);

                if (rRegistrationDetails.getImgUrl() != null) {
                    Glide.with(RegisterRestaurants.this).load(rRegistrationDetails.getImgUrl()).into(hotelImageView);
                }

                name.setText(rRegistrationDetails.getName());
                //category.(rRegistrationDetails.getCategory().toString());
                address.setText(rRegistrationDetails.getAddress());
                phoneno.setText(rRegistrationDetails.getPhone());
                timings.setText(rRegistrationDetails.getTiming());
                ratings.setText(rRegistrationDetails.getRating());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 33);
                Log.d(TAG, "Image chosen from gallery");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rName = name.getText().toString();
               String rCategory = category.getSelectedItem().toString();
               String rAddress = address.getText().toString();
               String rPhoneno = phoneno.getText().toString();
               String rRating = ratings.getText().toString();
               String rTimings = timings.getText().toString();
                if(!TextUtils.isEmpty(rName) || !TextUtils.isEmpty(rCategory) || !TextUtils.isEmpty(rAddress) ||
              !TextUtils.isEmpty(rPhoneno) || !TextUtils.isEmpty(rRating) || !TextUtils.isEmpty(rTimings) || imageUri != null) {
                    CollectionReference registrationDetails = database.collection("Restaurants");
                    RRegistrationDetails rRegistrationDetails = new RRegistrationDetails(rName,rCategory,rAddress,rPhoneno,rRating,rTimings,imageUri.toString());

                    registrationDetails.add(rRegistrationDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegisterRestaurants.this,"Restaurant Details added",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {           // Exception
                            Toast.makeText(RegisterRestaurants.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    UploadData();
                }
            }
        });
    }

    private void UploadData() {
        //auth.signOut();
        Log.d(TAG, "Uplaod Data method called");
//
//       String rName = name.getText().toString();
//       String rCategory = category.getSelectedItem().toString();
//       String rAddress = address.getText().toString();
//       String rPhoneno = phoneno.getText().toString();
//       String rRating = ratings.getText().toString();
//       String rTimings = timings.getText().toString();
//
//       if(!TextUtils.isEmpty(rName) || !TextUtils.isEmpty(rCategory) || !TextUtils.isEmpty(rAddress) ||
//               !TextUtils.isEmpty(rPhoneno) || !TextUtils.isEmpty(rRating) || !TextUtils.isEmpty(rTimings) || imageUri != null){
//            final StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUri));
//
//            uploadTask = reference.putFile(imageUri);
//
//            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if(!task.isSuccessful()){
//                        throw task.getException();
//                    }
//                    return reference.getDownloadUrl();
//                }
//            }) .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if(task.isSuccessful()){
//                        Uri downloadUri = task.getResult();
//
//                        RRegistrationDetails rRegistrationDetails = new RRegistrationDetails(rName,rCategory,rAddress,rPhoneno,rTimings,rRating,downloadUri.toString());
//
//                        collectionReference.add(rRegistrationDetails) .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Toast.makeText(RegisterRestaurants.this, "Registered Successfully!",Toast.LENGTH_SHORT);
//
//                                Intent intent = new Intent(RegisterRestaurants.this, HomePage.class);
//                                startActivity(intent);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(RegisterRestaurants.this, "Registered Failed!",Toast.LENGTH_SHORT);
//                            }
//                        });
//                    }
//                }
        Intent intent = new Intent(RegisterRestaurants.this, HomePage.class);
        Toast.makeText(RegisterRestaurants.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            Log.d(TAG,"Data is not equal to null");
            Uri imageUri = data.getData();
            hotelImageView.setImageURI(imageUri);

            final StorageReference reference = storage.getInstance().getReference("Restaurant_Images");

            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RegisterRestaurants.this, "Submitted Successfully!", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {

                            db.getReference().child("Restaurants").child("imgUrl").setValue(uri.toString());
                            Toast.makeText(RegisterRestaurants.this, "Restaurant Registered", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }
}


//    private void UploadData(){
//        Log.d(TAG, "Uplaod Data method called");
//
//       String rName = name.getText().toString();
//       String rCategory = category.getSelectedItem().toString();
//       String rAddress = address.getText().toString();
//       String rPhoneno = phoneno.getText().toString();
//       String rRating = ratings.getText().toString();
//       String rTimings = timings.getText().toString();
//
//       if(!TextUtils.isEmpty(rName) || !TextUtils.isEmpty(rCategory) || !TextUtils.isEmpty(rAddress) ||
//               !TextUtils.isEmpty(rPhoneno) || !TextUtils.isEmpty(rRating) || !TextUtils.isEmpty(rTimings) || imageUri != null){
//            final StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUri));
//
//            uploadTask = reference.putFile(imageUri);
//
//            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if(!task.isSuccessful()){
//                        throw task.getException();
//                    }
//                    return reference.getDownloadUrl();
//                }
//            }) .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if(task.isSuccessful()){
//                        Uri downloadUri = task.getResult();
//
//                        RRegistrationDetails rRegistrationDetails = new RRegistrationDetails(rName,rCategory,rAddress,rPhoneno,rTimings,rRating,downloadUri.toString());
//
//                        collectionReference.add(rRegistrationDetails) .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Toast.makeText(RegisterRestaurants.this, "Registered Successfully!",Toast.LENGTH_SHORT);
//
//                                Intent intent = new Intent(RegisterRestaurants.this, HomePage.class);
//                                startActivity(intent);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(RegisterRestaurants.this, "Registered Failed!",Toast.LENGTH_SHORT);
//                            }
//                        });
//                    }
//                }
//            })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//       }
//       else{
//           Toast.makeText(RegisterRestaurants.this, "All fields required!",Toast.LENGTH_SHORT);
//       }
//
//
//    }
//}

        // init
//        mAuth = FirebaseAuth.getInstance();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurants");
//        storageHotelPicsRef = FirebaseStorage.getInstance().getReference().child("Restaurants");
//
//        hotelImageView = findViewById(R.id.circular_view);
//
//        submit = findViewById(R.id.btnSubmit);
//        cameraBtn = findViewById(R.id.cameraBtn);
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                uploadHotelImage();
//            }
//        });
//
//        cameraBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CropImage.activity().setAspectRatio(1,1).start(RegisterRestaurants.this);
//            }
//        });

  //  }

//    @Override
//    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            imageUrl = result.getUri();
//
//            hotelImageView.setImageURI(imageUrl);
//        }
//        else {
//            Toast.makeText(this,"Error, Try again",Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void uploadHotelImage(){
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Add Restaurant Image");
//        progressDialog.setMessage("Please wait, while we are setting your data");
//        progressDialog.show();
//
//        if(imageUrl != null){
//            final StorageReference fileRef = storageHotelPicsRef.child(mAuth.getCurrentUser().getUid()+".jpg");
//
//            uploadTask = fileRef.putFile(imageUrl);
//
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception {
//                    if(!task.isSuccessful())
//                    {
//                        throw task.getException();
//                    }
//
//                    return fileRef.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener() {
//                @Override
//                public void onComplete(@NonNull Task task) {
//
//                }
//            });
//        }
//    }

