package com.example.findaraunt.Category_List;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<CategoryDisplayModel> {
    // constructor for our list view adapter.
    public CategoryAdapter(@NonNull Context context, ArrayList<CategoryDisplayModel> categoryDisplayModelArrayList) {
        super(context, 0, categoryDisplayModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // below line is use to inflate the layout for our item of list view.
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.category_card, parent, false);
        }

        // after inflating an item of listview item we are getting data from array list inside our modal class.
        CategoryDisplayModel categoryDisplayModel = getItem(position);

        // initializing our UI components of list view item.
        TextView name = listitemView.findViewById(R.id.catTxt1);
        ImageView image = listitemView.findViewById(R.id.catImg1);
        TextView address = listitemView.findViewById(R.id.catTxt2);
        TextView rating = listitemView.findViewById(R.id.catTxt3);


        // after initializing our items we are setting data to our view. below line is use to set data to our text view.
        name.setText(categoryDisplayModel.getName());
        address.setText(categoryDisplayModel.getAddress());
        rating.setText(categoryDisplayModel.getRating());
        // in below line we are using Picasso to load image from URL in our Image VIew.
        Picasso.get().load(categoryDisplayModel.getImgUrl()).into(image);

        // below line is use to add item click listener for our item of list view.
        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on the item click on our list view we are displaying a toast message.
                Toast.makeText(getContext(), "Item clicked is : " + categoryDisplayModel.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return listitemView;
    }
}


