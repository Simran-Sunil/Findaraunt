package com.example.findaraunt.Home_Page;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;

import com.example.findaraunt.LoginPage;
import com.example.findaraunt.R;

import com.example.findaraunt.RegisterRestaurants;
import com.example.findaraunt.SearchPage.SearchBar;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

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

//    private ListView mListView;  // Listview for displaying recommendations in home page.

    // Creating a variable for recommendation list view,arraylist and firebase Firestore.
//    ListView recommendations;
//    ArrayList<RecommendationModel> recommendationModelArrayList;

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




//        recommendations = findViewById(R.id.listview);
//        recommendationModelArrayList = new ArrayList<>();
//        db = FirebaseFirestore.getInstance();
//        // here we are calling a method to load data in our list view.
//        loadDatainListview();

//***********************************************************************************//
        //Drawer code
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    // Code for navigating through pages in Sidemenu Bar
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Log.d(TAG, "This is Drawer code");
        int id = menuItem.getItemId();

        if(id == R.id.home_menu){
            Intent intent1 = new Intent(this, HomePage.class);
            startActivity(intent1);
        }else if(id == R.id.profile_menu){
            Toast.makeText(this, "Profile button clicked", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.register_menu){
            Intent intent3 = new Intent(this, RegisterRestaurants.class);
            startActivity(intent3);
        }else if(id == R.id.login_menu){
            Intent intent4 = new Intent(this, LoginPage.class);
            startActivity(intent4);
        }else if(id == R.id.logout_menu){
            Toast.makeText(this, "Logout button clicked", Toast.LENGTH_SHORT).show();
        }
        drawerLayout = findViewById(R.id.drawerlayout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


//        switch (id){
//
//            case R.id.home_menu:
//                Intent intent1 = new Intent(this, HomePage.class);
//                startActivity(intent1);
//                break;
//
//            case R.id.profile_menu:
//                Toast.makeText(this, "Profile button clicked", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.register_menu:
//                Intent intent3 = new Intent(this, RegisterRestaurants.class);
//                startActivity(intent3);
//                break;
//
//            case R.id.login_menu:
//                Intent intent4 = new Intent(this, LoginPage.class);
//                startActivity(intent4);
//                break;
//
//            case R.id.logout_menu:
//                Toast.makeText(this, "Logout button clicked", Toast.LENGTH_SHORT).show();
//                break;
//
//            default:
//                return true;
//        }
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
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











