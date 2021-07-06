package com.example.findaraunt.Registration_Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findaraunt.Home_Page.HomePage;
import com.example.findaraunt.LoginPage;
import com.example.findaraunt.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationPage extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private Spinner spinner;
    private Button Register;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);


        username = findViewById(R.id.inputUsername);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        spinner = (Spinner)findViewById(R.id.inputLocation);
        Register = findViewById(R.id.btnRegister);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString().trim();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_location = spinner.getSelectedItem().toString();
                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText( RegistrationPage.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                }else if(txt_password.length()<6){
                    Toast.makeText(RegistrationPage.this,"Password too short",Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(txt_email, txt_password);
                    
                }

                if(!validateInputs(txt_username,txt_email,txt_password)){
                    CollectionReference dbUserDetails = db.collection("Users");  // Creating collection inside the firestore having name Users
                    // UserDetails object
                    UserDetails userDetails = new UserDetails(txt_username,txt_email,txt_password,txt_location);
                    // Saving the product to firestore
                    dbUserDetails.add(userDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegistrationPage.this,"User Details added",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {           // Exception
                            Toast.makeText(RegistrationPage.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        TextView btn = findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationPage.this, LoginPage.class));
            }
        });
    }

    // Validating the inputs
    private boolean validateInputs(String txt_username, String txt_email, String txt_password) {
        if(txt_username.isEmpty()){
            username.setError("Name required");
            username.requestFocus();
            return true;
        }
        if(txt_email.isEmpty()){
            email.setError("Email required");
            email.requestFocus();
            return true;
        }
        if(txt_password.isEmpty()){
            password.setError("Password required");
            password.requestFocus();
            return true;
        }
        return false;
    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationPage.this,"Registering user successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationPage.this, HomePage.class));
                }else{
                    Toast.makeText(RegistrationPage.this,"Registeration failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
