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
    String selectedPet;

    EditText petNameEditText;

    ImageView dogImageView, catImageView, unicornImageView;


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
    }

    public void selectedPet(View v)
    {
        switch (v.getId())
        {
            case R.id.dogImageView:
                if (!selectedPet.equals("dog"))
                {
                    enlargeImage(findViewById(R.id.dogImageView));
                    restoreImage(findViewById(R.id.catImageView));
                    restoreImage(findViewById(R.id.unicornImageView));
                    selectedPet = "dog";
                }
                break;

            case R.id.catImageView:
                if (!selectedPet.equals("cat"))
                {
                    enlargeImage(findViewById(R.id.catImageView));
                    restoreImage(findViewById(R.id.dogImageView));
                    restoreImage(findViewById(R.id.unicornImageView));
                    selectedPet = "cat";
                }
                break;

            case R.id.unicornImageView:
                if(!selectedPet.equals("unicorn"))
                {
                    enlargeImage(findViewById(R.id.unicornImageView));
                    restoreImage(findViewById(R.id.catImageView));
                    restoreImage(findViewById(R.id.dogImageView));
                    selectedPet = "unicorn";
                }
                break;
        }
    }

    public void enlargeImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = v.getLayoutParams().height + 100;
        v.getLayoutParams().width = v.getLayoutParams().height + 100;
    }

    public void restoreImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = v.getLayoutParams().height - 100;
        v.getLayoutParams().width = v.getLayoutParams().height - 100;
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