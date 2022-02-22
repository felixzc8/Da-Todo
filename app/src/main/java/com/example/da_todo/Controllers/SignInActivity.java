package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.da_todo.R;

public class SignInActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void logIn(View v){
        goTaskActivity();
    }

    public void goToSignUpActivity(View view){
        Intent goToSignUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(goToSignUpActivity);
    }

    public void goTaskActivity(){
        Intent goToTasksActivity = new Intent(this, TasksActivity.class);
        startActivity(goToTasksActivity);
    }
}