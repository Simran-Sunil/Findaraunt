package com.example.findaraunt.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findaraunt.models.CategoryDisplayModel;
import com.example.findaraunt.activities.CategoryDisplayScreen;
import com.example.findaraunt.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private static final String TAG = "CategoryAdapter";

    ArrayList<CategoryDisplayModel> categoryDisplayModels;
    Context context;

    // constructor for our list view adapter.
    public CategoryAdapter(Context context, ArrayList<CategoryDisplayModel> categoryDisplayModels) {
        this.context = context;
        this.categoryDisplayModels = categoryDisplayModels;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        // set logo to ImageView
        Picasso.get().load(categoryDisplayModels.get(position).getImgUrl()).into(holder.imgUrl);
        // set Name to TextView
        holder.name.setText(categoryDisplayModels.get(position).getName());
        holder.address.setText(categoryDisplayModels.get(position).getAddress());
        holder.rating.setText(categoryDisplayModels.get(position).getRating());
        holder.parentLayout.setOnClickListener((view -> {
        Log.d(TAG," onClick: clicked on: "+ categoryDisplayModels.get(position).getName());
        Toast.makeText(context, categoryDisplayModels.get(position).getName(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, CategoryDisplayScreen.class);
        intent.putExtra("restaurant_name", categoryDisplayModels.get(position).getName());
        context.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return categoryDisplayModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        ImageView imgUrl;
        TextView name;
        TextView rating;
        TextView address;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUrl = itemView.findViewById(R.id.catImg1);
            name = itemView.findViewById(R.id.catTxt1);
            address = itemView.findViewById(R.id.catTxt2);
            rating = itemView.findViewById(R.id.catTxt3);
            parentLayout = itemView.findViewById(R.id.parentId);
        }
    }
}


