package com.example.authenticationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signIn, signUp;
    String email, password;
    EditText mEmail, mPassword;

    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //when u don't wanna put an activity on the back-screen :use finish() / /
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();


    }

    public void onStart() {
        super.onStart();

        String Email = sharedPreferences.getString("USER", "");

        boolean b = sharedPreferences.getBoolean("CHK", false);


        if (b) {
            mEmail.setText(Email);
            checkBox.setChecked(true);

        }
    }

    void updateUI(FirebaseUser currentUser) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (checkBox.isChecked() && email != null) {
            editor.putString("USER", email);
            editor.putBoolean("CHK", true);
            editor.commit();

        }
    }

    public void signUP(View view) {
       // FirebaseUser currentUser = mAuth.getCurrentUser();

        if (checkValidity()) {
            email = mEmail.getText()
                    .toString();
            password = mPassword.getText().toString();

            if (!isValidEmail(email)) {
                Toast.makeText(MainActivity.this, "invalid email format \n" + " maik@mail.com",
                        Toast.LENGTH_LONG).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // Sign in success, update UI with the signed-in user's information
                                    // Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    updateUI(user);
                                    Toast.makeText(MainActivity.this, "Sign Up Successfully.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.d("error", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Sign Up failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        }

    }


    public void signIN(View v) {


        if (checkValidity()) {
            email = mEmail.getText().toString();
            password = mPassword.getText().toString();

            if (!isValidEmail(email)) {
                Toast.makeText(MainActivity.this, "invalid email format \n " + " maike@mail.com",
                        Toast.LENGTH_LONG).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    //If sign in fails, display a message to the user.
                                    Log.d("FUCK", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "The Account Is \n" + "Not Created",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });

            }
        }
    }


    public boolean checkValidity() {

        if (TextUtils.isEmpty(mEmail.getText().toString()) || TextUtils.isEmpty(mPassword.getText().toString())) {
            String error = "";
            if (TextUtils.isEmpty(email))
                error += "Email Is Needed" + "\n";
            if (TextUtils.isEmpty(password))
                error += "Password Is Needed";

            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
