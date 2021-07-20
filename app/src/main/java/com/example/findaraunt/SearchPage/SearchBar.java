package com.example.findaraunt.SearchPage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.findaraunt.Category_List.CategoryAdapter;
import com.example.findaraunt.Home_Page.HomePage;
import com.example.findaraunt.Home_Page.RecommendationAdapter;
import com.example.findaraunt.Home_Page.RecommendationModel;
import com.example.findaraunt.R;
import com.example.findaraunt.Category_List.CategoryDisplayModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchBar extends AppCompatActivity {

    private static final String TAG = "SearchBar Activity";
    CategoryAdapter adapter;
    FirebaseFirestore db;
    RecyclerView searchRV;
    ArrayList<CategoryDisplayModel> categoryModelsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Displays lisview of restaurants
        searchRV = findViewById(R.id.searchRV);
        categoryModelsArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        searchRV.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                SearchBar.this, LinearLayoutManager.VERTICAL, false
        );
        searchRV.setLayoutManager(linearLayoutManager);

        adapter = new CategoryAdapter(this,categoryModelsArrayList);
        searchRV.setAdapter(adapter);
        loadDatainSearchRV();

        //Search backend
        EditText searchBox = findViewById(R.id.searchbox);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    categoryModelsArrayList.clear();
                    loadDatainSearchRV();
                }else{
                    searchRestaurants(editable.toString());
                }
        }
    });
    }

    private void searchRestaurants(String type){
        if(!type.isEmpty()){
            db.collection("Restaurants").whereEqualTo("name", type.substring(0,1)+type.substring(1)).get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                categoryModelsArrayList.clear();
                                adapter.notifyDataSetChanged();
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot d : list) {
                                    CategoryDisplayModel categoryDisplayModel = d.toObject(CategoryDisplayModel.class);
                                    categoryModelsArrayList.add(categoryDisplayModel);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SearchBar.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }



    private void loadDatainSearchRV(){
        db.collection("Restaurants").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                CategoryDisplayModel categoryDisplayModel = d.toObject(CategoryDisplayModel.class);
                                categoryModelsArrayList.add(categoryDisplayModel);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(SearchBar.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SearchBar.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
