package com.example.findaraunt.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;

import com.example.findaraunt.adapters.MainAdapter;
import com.example.findaraunt.adapters.RecommendationAdapter;
import com.example.findaraunt.models.RecommendationModel;
import com.example.findaraunt.R;

import com.example.findaraunt.models.CategoryModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    //Initailizing variable
    RecyclerView recyclerView;

    RecyclerView recommendationView;
    ArrayList<RecommendationModel> recommendationModels;
    RecommendationAdapter adapter;
    private FirebaseFirestore db;
    ProgressBar loadingPB;

    private static final String TAG ="Home Page Drawer";

    ArrayList<CategoryModel> categoryModels;
    MainAdapter mainAdapter;

    // Drawer initializations
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.recycler_view);

        //Creating Integer array
        Integer[] categoryLogo = {R.drawable.chinese, R.drawable.fastfood, R.drawable.desserts};
        String[] categoryName = {"Chinese", "Fast food", "Desserts"};

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
        recommendationView = findViewById(R.id.recommendation_view);
        loadingPB = findViewById(R.id.idProgressBar);
        db = FirebaseFirestore.getInstance();
        recommendationModels = new ArrayList<>();
        recommendationView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                HomePage.this, LinearLayoutManager.VERTICAL, false
        );
        recommendationView.setLayoutManager(linearLayoutManager);

        adapter = new RecommendationAdapter(this,recommendationModels);
        recommendationView.setAdapter(adapter);
        loadDatainRecyclerview();

//***********************************************************************************//
        //Drawer code
        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setNavigationViewListener();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //navigationView.setNavigationItemSelectedListener(this);
       // setupDrawerContent(navigationView);

    }


    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void  setNavigationViewListener(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id=item.getItemId();

        if (id == R.id.home_menu) {
            Intent intent1 = new Intent(this, HomePage.class);
            startActivity(intent1);
            return true;
        }
        else if (id == R.id.profile_menu) {
            Toast.makeText(this, "Profile button clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.feedback_menu) {
            Intent intent3 = new Intent(this, FeedBackPage.class);
            startActivity(intent3);
            return true;
        }
        else if (id == R.id.login_menu) {
            Intent intent4 = new Intent(this, LoginPage.class);
            startActivity(intent4);
            return true;
        }
        else if (id == R.id.logout_menu) {
            Intent in = getIntent();
            String string = in.getStringExtra("message");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Hey there!").
                    setMessage("You sure, that you want to logout?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(getApplicationContext(),
                                    LoginPage.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }
                    });
            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder.create();
            alert11.show();
            Toast.makeText(this, "Logout button clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private void loadDatainRecyclerview() {
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
                            loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                RecommendationModel recommendationModel = d.toObject(RecommendationModel.class);
                                // after getting data from Firebase we are
                                // storing that data in our array list
                                recommendationModels.add(recommendationModel);
                            }
                           adapter.notifyDataSetChanged();
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











