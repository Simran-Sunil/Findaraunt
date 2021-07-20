package com.example.findaraunt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterRestaurants extends AppCompatActivity {

    private CircleImageView hotelImageView;
    private Button submit;
    private TextView cameraBtn;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private Uri imageUrl;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageHotelPicsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_restaurants);

        // init
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurants");
        storageHotelPicsRef = FirebaseStorage.getInstance().getReference().child("Restaurants");

        hotelImageView = findViewById(R.id.circularImageView);

        submit = findViewById(R.id.btnSubmit);
        cameraBtn = findViewById(R.id.cameraBtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadHotelImage();
            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(RegisterRestaurants.this);
            }
        });

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUrl = result.getUri();

            hotelImageView.setImageURI(imageUrl);
        }
        else {
            Toast.makeText(this,"Error, Try again",Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadHotelImage(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Add Restaurant Image");
        progressDialog.setMessage("Please wait, while we are setting your data");
        progressDialog.show();

        if(imageUrl != null){
            final StorageReference fileRef = storageHotelPicsRef.child(mAuth.getCurrentUser().getUid()+".jpg");

            uploadTask = fileRef.putFile(imageUrl);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                }
            });
        }
    }
}
