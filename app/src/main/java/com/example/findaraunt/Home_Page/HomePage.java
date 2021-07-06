package com.example.findaraunt.Home_Page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.findaraunt.R;

import java.util.ArrayList;


public class HomePage extends AppCompatActivity {
    //Initailizing variable
    RecyclerView recyclerView;

    ArrayList<CategoryModel> categoryModels;
    MainAdapter mainAdapter;

    private ListView mListView;  // Listview for displaying recommendations in home page.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        recyclerView = findViewById(R.id.recycler_view);

        //Creating Integer array
        Integer[] categoryLogo = {R.drawable.chinese, R.drawable.fastfood, R.drawable.desserts};
        String[] categoryName = {"Chinese","FastFood","Desserts"};

        categoryModels = new ArrayList<>();
        for(int i=0;i<categoryLogo.length;i++){
            CategoryModel model = new CategoryModel(categoryLogo[i], categoryName[i]);
            categoryModels.add(model);
        }

        //Horizontal layout design
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                HomePage.this, LinearLayoutManager.HORIZONTAL,false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Initializing MainAdapter
        mainAdapter = new MainAdapter(HomePage.this,categoryModels);
        // Set MainAdapter to RecyclerView
        recyclerView.setAdapter(mainAdapter);

        mListView = findViewById(R.id.listview);

        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);
    }


    // This adapter is used for displaying cards in recommendation section.
    private String[] names = {"Hao Ming Chinese","Sagar Ratna","Capella Patisserie","London Street"};
    private int[] images = {R.drawable.hotel1, R.drawable.hotel3, R.drawable.hotel4, R.drawable.hotel5};
    private String[] category = {"Chinese","Indian","Desserts","Mocktails"};

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.card, viewGroup, false);
            ImageView mImageView = view.findViewById(R.id.imageview);
            TextView mTextView = view.findViewById(R.id.textview);
            TextView mCategoryView = view.findViewById(R.id.textview3);

            mTextView.setText(names[i]);
            mImageView.setImageResource(images[i]);
            mCategoryView.setText(category[i]);
            return view;
        }
    }

}



