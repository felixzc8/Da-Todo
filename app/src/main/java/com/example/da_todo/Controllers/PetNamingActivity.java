package com.example.da_todo.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PetNamingActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

    User user;
    Pet userPet;
    EditText petNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_naming);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        user = (User) getIntent().getSerializableExtra("user");
        userPet = user.getPet();

        petNameEditText = findViewById(R.id.petNameInput_EditText_PetNamingActivity);
    }

    public void changePetName(View view)
    {
        String name = petNameEditText.getText().toString();
        //error here because should be type Pet, not string

        userPet.setName(name);
        user.setPet(userPet);

        firestore.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .set(user).addOnCompleteListener(task ->
        {
            //to be completed
        });

        goTaskActivity();
    }

    public void backButton(View view)
    {
        Intent backButton = new Intent(this, RewardsActivity.class);
        startActivity(backButton);
    }

    public void goTaskActivity()
    {
        Intent intent = new Intent(this, TasksActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}