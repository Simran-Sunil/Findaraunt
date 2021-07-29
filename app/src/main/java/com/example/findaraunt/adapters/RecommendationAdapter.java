package com.example.findaraunt.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.findaraunt.activities.CategoryActivity;
import com.example.findaraunt.models.RecommendationModel;
import com.example.findaraunt.R;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;


public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder>{

    private static final String TAG = "RecommendationAdapter";

    ArrayList<RecommendationModel> recommendationModels;
    Context context;

    public RecommendationAdapter(Context context,ArrayList<RecommendationModel> recommendationModels){
        this.context = context;
        this.recommendationModels = recommendationModels;
    }

    @NonNull
    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create View
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.card, parent,false);
//        RecommendationAdapter.ViewHolder holder = new RecommendationAdapter.ViewHolder(view);
//        return holder;

        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        // set logo to ImageView
        Picasso.get().load(recommendationModels.get(position).getImgUrl()).into(holder.rImg);
        // set Name to TextView
        holder.rName.setText(recommendationModels.get(position).getName());
        holder.rCategory.setText(recommendationModels.get(position).getHcategory());
        holder.rRating.setText(recommendationModels.get(position).getrRating());
        //holder.parentLayout.setOnClickListener((view -> {
            //Log.d(TAG," onClick: clicked on: "+ recommendationModels.get(position).getName());
           // Toast.makeText(context, recommendationModels.get(position).getName(),Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(context, CategoryActivity.class);
//            intent.putExtra("category_name", categoryModels.get(position).getCategoryName());
//            context.startActivity(intent);
       // }));
    }

    @Override
    public int getItemCount() {
        return recommendationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        ImageView rImg;
        TextView rName;
        TextView rRating;
        TextView rCategory;
       // RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rImg = itemView.findViewById(R.id.imageview);
            rName = itemView.findViewById(R.id.textview);
            rRating = itemView.findViewById(R.id.textview2);
            rCategory = itemView.findViewById(R.id.textview3);
           // parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

