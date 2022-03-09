package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class RewardsActivity extends AppCompatActivity
{
    FirebaseFirestore firestore;
    TextView petNameTextView, moneyTextView;
    User user;
    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        firestore = FirebaseFirestore.getInstance();

        user = (User) getIntent().getSerializableExtra("user");
        pet = user.getPet();

        petNameTextView = findViewById(R.id.petName_TextView_RewardsActivity);
        moneyTextView = findViewById(R.id.money_TextView_RewardsActivity);

        moneyTextView.setText(Integer.toString(pet.getTotalPoints()));
        petNameTextView.setText(pet.getName());
    }

    public void feedPet(View v)
    {
        int totalPoints = pet.getTotalPoints() - 50;
        int petPoints = pet.getPoints() + 50;

        pet.setTotalPoints(totalPoints);
        pet.setPoints(petPoints);

        firestore.collection("Pets").document(pet.getID()).set(pet);

        moneyTextView.setText(totalPoints);
    }

    public void backButton(View v)
    {
        Intent backButton = new Intent(this, TasksActivity.class);
        startActivity(backButton);
    }

    public void renamePet(View v)
    {
        Intent intent = new Intent(this, PetNamingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}