package com.example.authenticationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity<list> extends AppCompatActivity {
    FirebaseFirestore db;

    List<CheckBox> list ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = FirebaseFirestore.getInstance();
     }

    public void logOUT(View view) {
        startActivity(new Intent(this, MainActivity.class)); //Go back to home page
        finish();
    }


    public void writeOnDB(View view) {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();

        FirebaseUser user_id = FirebaseAuth.getInstance().getCurrentUser();

        user.put("username", "Tarik");
        user.put("uId", user_id.getUid());
        user.put("complete", false);

        // Add a new document with a generated ID
        db.collection("todos")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("onSuccess", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("onFailure", "Error adding document", e);
                    }
                });


    }
}
