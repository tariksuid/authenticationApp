package com.example.authenticationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
 import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore db;
     FirebaseUser user_id;
    Button add;
    EditText editText;
     RecyclerView rc ;
    ArrayList<TaskModel> ar ;
    TaskAdapter ta ;
   // ArrayList <CheckBox> ar2 ;
    //RecyclerView.LayoutManager rvl ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rc= findViewById(R.id.task_list);
       // rc.setHasFixedSize(true);

       // rc.setItemViewCacheSize(5);
        rc.setLayoutManager(new LinearLayoutManager(this));
       // configRV();
        ar = new ArrayList<>();
ar.clear();
        db = FirebaseFirestore.getInstance();
        add = findViewById(R.id.addBtn);
        editText = findViewById(R.id.taskTxt);


      //  ar = TaskModel.createContactsList(20);


       //todo = findViewById(R.id.taskA);


        //read the DB
        add.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                writeOnDB();
            }
        });
          user_id = FirebaseAuth.getInstance().getCurrentUser();

         readFromDB();

    }

    public void logOUT(View view) {
        startActivity(new Intent(this, MainActivity.class)); //Go back to home page
        finish();
    }


    public void writeOnDB() {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();

         //  user.put("username", "Tarik");
        user.put("tID", user_id.getUid());
        user.put("task", editText.getText().toString());

        user.put("status",false) ;

        // Add a new document with a generated ID
        db.collection("tasks")
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


    void readFromDB() {
        ta = new TaskAdapter(ar);

          db.collection("tasks")
                .whereEqualTo("tID", user_id.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) {
                            // Log.e(TAG, "fetchTodos failed", task.getException());
                            return;
                        }

                        for (QueryDocumentSnapshot todo : task.getResult()) {
                            String name = todo.get("task").toString();

                            Log.d("TASK" , name) ;
                            boolean c  =(boolean) todo.get("status" ) ;
                            Log.d("STATUS", String.valueOf(c)) ;

                            insertTodoUi(name , false);

                        }
                    }
                });

        //configRV();


     }

    private void configRV() {

        TaskAdapter ta = new TaskAdapter(ar);
        rc.setAdapter(ta);
    }

    private void insertTodoUi(String name , boolean b) {


         rc.setAdapter(ta);
    ar.add(new TaskModel(name , b)) ;



    }


}
