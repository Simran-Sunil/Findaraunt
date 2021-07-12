package com.example.findaraunt.Home_Page;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View.OnClickListener;
import android.widget.Toast;

//import com.example.findaraunt.Category_List.CategoryActivity;
import com.example.findaraunt.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static final String TAG = "MainAdapter";

    ArrayList<CategoryModel> categoryModels;
    Context context;

    public  MainAdapter(Context context,ArrayList<CategoryModel> categoryModels){
        this.context = context;
        this.categoryModels = categoryModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // set logo to ImageView
        holder.imageView.setImageResource(categoryModels.get(position).getCategoryLogo());
        // set Name to TextView
        holder.textView.setText(categoryModels.get(position).getCategoryName());
//        holder.parentLayout.setOnClickListener((view -> {
//            Log.d(TAG," onClick: clicked on: "+ categoryModels.get(position).getCategoryName());
//            Toast.makeText(context, categoryModels.get(position).getCategoryName(),Toast.LENGTH_SHORT).show();
//
//            Intent intent = new Intent(context, CategoryActivity.class);
//            intent.putExtra("imageUrl", categoryModels.get(position).getCategoryLogo());
//            intent.putExtra("category_name", categoryModels.get(position).getCategoryName());
//            context.startActivity(intent);
//    }));
    }

@Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        ImageView imageView;
        TextView textView;
       // RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
           // parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
