package com.example.findaraunt.SearchPage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import com.example.findaraunt.Category_List.CategoryAdapter;
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
    private CategoryAdapter adapter;
    FirebaseFirestore db;

    ListView searchLV;
    ArrayList<CategoryDisplayModel> categoryModelsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Displays lisview of restaurants
        searchLV = findViewById(R.id.searchLV);
        categoryModelsArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        loadDatainSearchListView();

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
                Log.d(TAG, "SearchBox has changed to: " + editable.toString());
                if(editable.toString().isEmpty()){
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
                                        adapter = new CategoryAdapter(SearchBar.this, categoryModelsArrayList);
                                        searchLV.setAdapter(adapter);
                                    } else {
                                        Toast.makeText(SearchBar.this, "No data found in search Database", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SearchBar.this, "Fail to load data..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    db.collection("Restaurants").whereEqualTo("name", editable.toString()).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            CategoryDisplayModel categoryDisplayModel = d.toObject(CategoryDisplayModel.class);
                                            categoryModelsArrayList.add(categoryDisplayModel);
                                        }
                                        //adapter.notifyDataSetChanged();
                                        adapter = new CategoryAdapter(SearchBar.this, categoryModelsArrayList);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(SearchBar.this, "No data found in Search DB", Toast.LENGTH_SHORT).show();
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
    });
    }


    private void loadDatainSearchListView(){
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
                            adapter = new CategoryAdapter(SearchBar.this, categoryModelsArrayList);
                            searchLV.setAdapter(adapter);
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
