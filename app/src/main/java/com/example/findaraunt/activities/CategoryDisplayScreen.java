package com.example.findaraunt.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.findaraunt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CategoryDisplayScreen extends AppCompatActivity {

    private TextView resRating;
    private TextView resAddress;
    private TextView resPhone;
    private TextView resTiming;
    private ImageView resImage;
    private TextView resCategory;

    private Button btnCall;
    private Button btnDirection;


    private static final String TAG = "Category Display Screen";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorydisplay);
        Log.d(TAG, "cc started Category Display Screen.");

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: category display page");
        if (getIntent().hasExtra("restaurant_name")) {
            Log.d(TAG, "getIncomingIntent: found intent extras in category display page.");

            String restaurantName = getIntent().getStringExtra("restaurant_name");
            restaurantDetails(restaurantName);
        }
    }

    private void restaurantDetails(String restaurantName){
        Log.d(TAG, "Setting up category name in CategoryDisplayScreen");

        TextView resName = findViewById(R.id.displayTxt);
        resName.setText(restaurantName);
        resRating = findViewById(R.id.displayTxt1);
        resCategory = findViewById(R.id.displayTxt5);
        resAddress = findViewById(R.id.displayTxt2);
        resPhone = findViewById(R.id.displayTxt3);
        resTiming = findViewById(R.id.displayTxt4);
        resImage = findViewById(R.id.displayImg1);
        db = FirebaseFirestore.getInstance();

        if(!restaurantName.isEmpty()){
            db.collection("Restaurants").whereEqualTo("name", restaurantName).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for(DocumentSnapshot document : task.getResult()){
                            resRating.setText((CharSequence) document.get("rating"));
                            resCategory.setText((CharSequence) document.get("category"));
                            resAddress.setText((CharSequence) document.get("address"));
                            resPhone.setText((CharSequence) document.get("phone"));
                            resTiming.setText((CharSequence) document.get("timing"));

                            Picasso.get().load((String) document.get("imgUrl")).into(resImage);

                            callRestaurant(resPhone.getText().toString());
                            Log.d(TAG, "The value of phoneno is :"+resPhone.getText().toString());

                            getDirection(resAddress.getText().toString());
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"No such document", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void callRestaurant(String phoneno){

        btnCall = findViewById(R.id.btnCall);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phoneno));
                startActivity(callIntent);
            }
        });
    }

    private void getDirection(String address){

        btnDirection = findViewById(R.id.btnDirection);

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://maps.google.com/maps?daddr="+address;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });


    }

}
