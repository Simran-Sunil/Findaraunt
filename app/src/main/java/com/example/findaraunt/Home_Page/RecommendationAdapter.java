package com.example.findaraunt.Home_Page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.findaraunt.R;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;

public class RecommendationAdapter extends ArrayAdapter<RecommendationModel> {

    // constructor for our list view adapter.
    public RecommendationAdapter(@NonNull Context context, ArrayList<RecommendationModel> recommendationModalArrayList) {
        super(context, 0, recommendationModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card, parent, false);
        }

        // after inflating an item of listview item we are getting data from array list inside our modal class.
        RecommendationModel recommendationModal = getItem(position);

        // initializing our UI components of list view item.
        TextView name = listitemView.findViewById(R.id.textview);
        ImageView image = listitemView.findViewById(R.id.imageview);
        TextView hcategory = listitemView.findViewById(R.id.textview3);

        // after initializing our items we are setting data to our view. below line is use to set data to our text view.
        name.setText(recommendationModal.getName());
        hcategory.setText(recommendationModal.getHcategory());
        // in below line we are using Picasso to load image from URL in our Image VIew.
        Picasso.get().load(recommendationModal.getImgUrl()).into(image);

        // below line is use to add item click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + recommendationModal.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}

