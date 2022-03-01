package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;

public class PetNamingActivity extends AppCompatActivity {

    User user;
    Pet userPet;

    EditText petNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_naming);

        user = (User) getIntent().getSerializableExtra("user");
        userPet = (Pet) getIntent().getSerializableExtra("pet");

        petNameEditText = findViewById(R.id.petNameEditText);
    }

    public void changePetName(View view) {
        String petName = petNameEditText.getText().toString();
        userPet.setPetName(petName);

        Intent intent = new Intent(this, TasksActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("pet", userPet);
        startActivity(intent);
    }
}