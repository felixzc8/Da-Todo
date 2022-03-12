package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.da_todo.R;

public class AddAllTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_all_task);
        String image;
        String name;
        int timeRequired;
        int pointsRewarded;
        String taskUUID;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            image = extras.getString("image");
            name = extras.getString("name");
        }
    }
}