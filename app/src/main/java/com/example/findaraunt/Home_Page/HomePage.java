package com.example.findaraunt.Home_Page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.findaraunt.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    //Initailizing variable
    RecyclerView recyclerView;

    ArrayList<CategoryModel> categoryModels;
    MainAdapter mainAdapter;
//    private ListView mListView;  // Listview for displaying recommendations in home page.

    // Creating a variable for recommendation list view,arraylist and firebase Firestore.
    ListView recommendations;
    ArrayList<RecommendationModel> recommendationModelArrayList;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.recycler_view);

        //Creating Integer array
        Integer[] categoryLogo = {R.drawable.chinese, R.drawable.fastfood, R.drawable.desserts};
        String[] categoryName = {"Chinese", "FastFood", "Desserts"};

        categoryModels = new ArrayList<>();
        for (int i = 0; i < categoryLogo.length; i++) {
            CategoryModel model = new CategoryModel(categoryLogo[i], categoryName[i]);
            categoryModels.add(model);
        }

        //Horizontal layout design
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                HomePage.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Initializing MainAdapter
        mainAdapter = new MainAdapter(HomePage.this, categoryModels);
        // Set MainAdapter to RecyclerView
        recyclerView.setAdapter(mainAdapter);

//***********************************************************************************//
        // Recommendation Backend
        recommendations = findViewById(R.id.listview);
        recommendationModelArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        // here we are calling a method to load data in our list view.
        loadDatainListview();

//        mListView = findViewById(R.id.listview);
//        MyAdapter adapter = new MyAdapter();
//        mListView.setAdapter(adapter);

    }

    private void loadDatainListview() {
        db.collection("Recommendations").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding
                            // our progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                RecommendationModel recommendationModel = d.toObject(RecommendationModel.class);
                                // after getting data from Firebase we are
                                // storing that data in our array list
                                recommendationModelArrayList.add(recommendationModel);
                            }
                            // after that we are passing our array list to our adapter class.
                            RecommendationAdapter adapter = new RecommendationAdapter(HomePage.this, recommendationModelArrayList);

                            // after passing this array list to our adapter
                            // class we are setting our adapter to our list view.
                            recommendations.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(HomePage.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(HomePage.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}







