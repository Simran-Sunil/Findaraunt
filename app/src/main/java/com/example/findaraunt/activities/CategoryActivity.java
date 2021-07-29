package com.example.findaraunt.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findaraunt.adapters.CategoryAdapter;
import com.example.findaraunt.models.CategoryDisplayModel;
import com.example.findaraunt.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "CategoryActivity";
    private CategoryAdapter adapter;
    FirebaseFirestore db;

    RecyclerView categoryRV;
    ArrayList<CategoryDisplayModel> categoryModelArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent(); // Gets the category name selected in HomePage

    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");
        if(getIntent().hasExtra("category_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String category = getIntent().getStringExtra("category_name");

            setCategoryList(category);
        }
    }

    private void setCategoryList(String category){
        Log.d(TAG, "Setting up category name in CategoryActivity");

        TextView categoryName = findViewById(R.id.catName);
        categoryName.setText(category);

        // Display the list of restaurants based on their category name
        categoryRV = findViewById(R.id.categoryRV);
        categoryModelArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        categoryRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                CategoryActivity.this, LinearLayoutManager.VERTICAL, false
        );
        categoryRV.setLayoutManager(linearLayoutManager);

        adapter = new CategoryAdapter(this,categoryModelArrayList);
        categoryRV.setAdapter(adapter);

        categoryList(category);  //function call
    }

    public void categoryList(String category){
        if(!category.isEmpty()){
            db.collection("Restaurants").whereEqualTo("category", category).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    CategoryDisplayModel categoryDisplayModel = d.toObject(CategoryDisplayModel.class);
                                    categoryModelArrayList.add(categoryDisplayModel);
                                   // adapter.notifyDataSetChanged();
                                }
                                adapter.notifyDataSetChanged();

                            } else {
                                Toast.makeText(CategoryActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CategoryActivity.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }
}
