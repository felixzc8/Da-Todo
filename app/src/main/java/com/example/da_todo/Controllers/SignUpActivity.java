package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.da_todo.R;

public class SignUpActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void goSignInActivity()
    {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void goTaskActivity()
    {
        Intent intent = new Intent(this, TasksActivity.class);
        startActivity(intent);
    }
}