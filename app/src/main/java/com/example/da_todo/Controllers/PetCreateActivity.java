package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PetCreateActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    User user;
    Pet userPet;
    String selectedPet = "";
    EditText petNameEditText;
    ImageView dogImageView, catImageView, unicornImageView, dragonImageView, hamsterImageView, pandaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_create);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        user = (User) getIntent().getSerializableExtra("user");
        userPet = user.getPet();

        petNameEditText = findViewById(R.id.petNameInput_EditText_PetNamingActivity);

        dogImageView = findViewById(R.id.dogImageView);
        catImageView = findViewById(R.id.catImageView);
        unicornImageView = findViewById(R.id.unicornImageView);
        dragonImageView = findViewById(R.id.dragonImageView);
        hamsterImageView = findViewById(R.id.hamsterImageView);
        pandaImageView = findViewById(R.id.pandaImageView);
    }

    public void selectedPet(View v)
    {
        switch (v.getId())
        {
            case R.id.dogImageView:
                enlargeImage(findViewById(R.id.dogImageView));
                restoreImage();
                selectedPet = "dog";
                break;

            case R.id.catImageView:
                enlargeImage(findViewById(R.id.catImageView));
                restoreImage();
                selectedPet = "cat";
                break;

            case R.id.unicornImageView:
                enlargeImage(findViewById(R.id.unicornImageView));
                restoreImage();
                selectedPet = "unicorn";
                break;

            case R.id.dragonImageView:
                enlargeImage(findViewById(R.id.dragonImageView));
                restoreImage();
                selectedPet = "dragon";
                break;

            case R.id.hamsterImageView:
                enlargeImage(findViewById(R.id.hamsterImageView));
                restoreImage();
                selectedPet = "hamster";
                break;

            case R.id.pandaImageView:
                enlargeImage(findViewById(R.id.pandaImageView));
                restoreImage();
                selectedPet = "panda";
                break;
        }
    }

    public void enlargeImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = 500;
        v.getLayoutParams().width = 500;
    }

    public void reduceImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = 400;
        v.getLayoutParams().width = 400;
    }

    public void restoreImage()
    {
        switch(selectedPet)
        {
            case "":
                break;
            case "dog":
                reduceImage(findViewById(R.id.dogImageView));
                break;
            case "cat":
                reduceImage(findViewById(R.id.catImageView));
                break;
            case "unicorn":
                reduceImage(findViewById(R.id.unicornImageView));
                break;
            case "dragon":
                reduceImage(findViewById(R.id.dragonImageView));
                break;
            case "hamster":
                reduceImage(findViewById(R.id.hamsterImageView));
                break;
            case "panda":
                reduceImage(findViewById(R.id.pandaImageView));
                break;
        }
    }

    public void changePetName(View view)
    {
        String name = petNameEditText.getText().toString();
        //error here because should be type Pet, not string

        userPet.setName(name);
        user.setPet(userPet);
        user.setSelectedPet(selectedPet);

        firestore.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .set(user).addOnCompleteListener(task ->
        {
            //to be completed
        });

        goTaskActivity();
    }

    public void goTaskActivity()
    {
        Intent intent = new Intent(this, TasksActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}