package com.example.findaraunt.activities;
import com.example.findaraunt.R;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findaraunt.models.FeedbackModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedBackPage extends AppCompatActivity {

    EditText title, suggestion;
    Button submit;

    FirebaseFirestore db;

    private static final String TAG ="Feedback Page";

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        db = FirebaseFirestore.getInstance();

        title = findViewById(R.id.feedbackET1);
        suggestion = findViewById(R.id.feedbackET2);
        submit = findViewById(R.id.fbSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               submitFeedback();
               showAlertDialog(R.layout.dialog_layout);
            }
        });
    }

    private void submitFeedback(){
        String fTitle = title.getText().toString();
        String fSuggestion = suggestion.getText().toString();

        FeedbackModel feedbackModel = new FeedbackModel(fTitle, fSuggestion);

        db.collection("Feedback").add(feedbackModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void showAlertDialog(int layout){
        dialogBuilder = new AlertDialog.Builder(FeedBackPage.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        Button dialogButton = layoutView.findViewById(R.id.btnDone);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FeedBackPage.this, HomePage.class);
                startActivity(intent1);
            }
        });
    }

}
