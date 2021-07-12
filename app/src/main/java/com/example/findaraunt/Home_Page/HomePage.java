package com.example.findaraunt.Home_Page;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;

import com.example.findaraunt.LoginPage;
import com.example.findaraunt.R;

import com.example.findaraunt.RegisterRestaurants;
import com.example.findaraunt.SearchPage.SearchBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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

    // Drawer initializations
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


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

        //-----------------------------------------------------------------//
        // on clicking search tab, it shud move to search page
        EditText searchView = findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("SearchBar Clicked");

                Intent searchActivity = new Intent(getApplicationContext(), SearchBar.class);
                startActivity(searchActivity);
            }
        });

//***********************************************************************************//
        // Recommendation Backend
        recommendations = findViewById(R.id.listview);
        recommendationModelArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        // here we are calling a method to load data in our list view.
        loadDatainListview();

//***********************************************************************************//
        //Drawer code
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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

    // Code for navigating through pages in Sidemenu Bar
    public void goToHomePage(View view){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void goToRegResPage(View view){
        Intent intent = new Intent(this, RegisterRestaurants.class);
        startActivity(intent);
    }

    public void goToLoginPage(View view){
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }

}







