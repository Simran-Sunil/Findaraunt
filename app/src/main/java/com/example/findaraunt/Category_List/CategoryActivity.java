package com.example.findaraunt.Category_List;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.example.findaraunt.R;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("imageUrl") && getIntent().hasExtra("category_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("imageUrl");
            String name = getIntent().getStringExtra("category_name");
        }
    }

    private void setImage(){

    }
}
