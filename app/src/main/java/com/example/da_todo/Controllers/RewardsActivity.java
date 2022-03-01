package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.google.firebase.firestore.FirebaseFirestore;

public class RewardsActivity extends AppCompatActivity
{
    FirebaseFirestore firestore;

    TextView petNameTextView;
    TextView moneyTextView;

    Pet userPet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        firestore = FirebaseFirestore.getInstance();

        userPet = (Pet) getIntent().getSerializableExtra("pet");

        petNameTextView = findViewById(R.id.petNameTextView);
        moneyTextView = findViewById(R.id.moneyTextView);
    }

    public void backButton(View v)
    {
        Intent backButton = new Intent(this, TasksActivity.class);
        startActivity(backButton);
    }

    public void renamePetButton(View v)
    {
        Intent renamePetButton = new Intent(this, PetNamingActivity.class);
        startActivity(renamePetButton);
    }
}