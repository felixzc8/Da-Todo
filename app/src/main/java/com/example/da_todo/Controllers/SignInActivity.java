package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.da_todo.R;
import com.example.da_todo.User.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    User user;

    EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailInput = findViewById(R.id.emailInputEditText);
        passwordInput = findViewById(R.id.passwordInputEditText);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            String email = currentUser.getEmail();

            System.out.println(String.format("Current User - email: %s", email));
            goTaskActivity();
        }
    }

    public void logIn(View v){
        goTaskActivity();
    }

    public void goToSignUpActivity(View view){
        Intent goToSignUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(goToSignUpActivity);
    }

    public void goTaskActivity()
    {
        Intent goToTasksActivity = new Intent(this, TasksActivity.class);
        startActivity(goToTasksActivity);
    }
}