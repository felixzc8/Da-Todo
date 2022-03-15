package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class PetActivity extends AppCompatActivity
{
    FirebaseFirestore firestore;

    User user;
    Pet pet;

    String currentAction = "";

    int counter = 0;

    TextView petNameTextView, moneyTextView;
    ImageView petImageView, teddyBearImageView, bananaImageView, soapImageView;

    ProgressBar happinessProgressBar;
    ProgressBar hungerProgressBar;
    ProgressBar cleanProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        firestore = FirebaseFirestore.getInstance();

        user = (User) getIntent().getSerializableExtra("user");
        pet = user.getPet();

        petNameTextView = findViewById(R.id.petName_TextView_RewardsActivity);
        moneyTextView = findViewById(R.id.money_TextView_RewardsActivity);
        petImageView = findViewById(R.id.petImageView);
        teddyBearImageView = findViewById(R.id.teddyBearImageView);
        bananaImageView = findViewById(R.id.bananaImageView);
        soapImageView = findViewById(R.id.soapImageView);

        happinessProgressBar = findViewById(R.id.happinessProgressBar);
        hungerProgressBar = findViewById(R.id.hungerProgressBar);
        cleanProgressBar = findViewById(R.id.cleanProgressBar);

        moneyTextView.setText(Integer.toString(pet.getTotalPoints()));
        petNameTextView.setText(pet.getName());

        setPetImage(user);
        progressBar();
        petPressed();
    }

    public void setPetImage(User user)
    {
        switch (user.getSelectedPet())
        {
            case "dog":
                petImageView.setImageResource(R.drawable.dogpet);
                break;

            case "cat":
                petImageView.setImageResource(R.drawable.catpet);
                break;

            case "unicorn":
                petImageView.setImageResource(R.drawable.unicornpet);
                break;
        }
    }

    public void selectedAction(View v)
    {
        switch (v.getId())
        {
            case R.id.teddyBearImageView:
                switch (currentAction)
                {
                    case "":
                        enlargeImage(findViewById(R.id.teddyBearImageView));
                        currentAction = "teddy bear";
                        break;

                    case "banana":
                        enlargeImage(findViewById(R.id.teddyBearImageView));
                        restoreImage(findViewById(R.id.bananaImageView));
                        currentAction = "teddy bear";
                        break;

                    case "soap":
                        enlargeImage(findViewById(R.id.teddyBearImageView));
                        restoreImage(findViewById(R.id.soapImageView));
                        currentAction = "teddy bear";
                }
                break;

            case R.id.bananaImageView:
                switch (currentAction)
                {
                    case "":
                        enlargeImage(findViewById(R.id.bananaImageView));
                        currentAction = "banana";
                        break;

                    case "teddy bear":
                        enlargeImage(findViewById(R.id.bananaImageView));
                        restoreImage(findViewById(R.id.teddyBearImageView));
                        currentAction = "banana";
                        break;

                    case "soap":
                        enlargeImage(findViewById(R.id.bananaImageView));
                        restoreImage(findViewById(R.id.soapImageView));
                        currentAction = "banana";
                }
                break;

            case R.id.soapImageView:
                switch (currentAction)
                {
                    case "":
                        enlargeImage(findViewById(R.id.soapImageView));
                        currentAction = "soap";
                        break;

                    case "teddy bear":
                        enlargeImage(findViewById(R.id.soapImageView));
                        restoreImage(findViewById(R.id.teddyBearImageView));
                        currentAction = "soap";
                        break;

                    case "banana":
                        enlargeImage(findViewById(R.id.soapImageView));
                        restoreImage(findViewById(R.id.bananaImageView));
                        currentAction = "soap";
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

    @SuppressLint("ClickableViewAccessibility")
    public void petPressed()
    {
       petImageView.setOnTouchListener(new View.OnTouchListener()
       {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent)
           {
               Log.i("TouchEvent", "touch detected");

               int eventType = motionEvent.getActionMasked();

               switch(eventType)
               {
                   case MotionEvent.ACTION_DOWN:
                       enlargeImage(petImageView);
                       break;

                   case MotionEvent.ACTION_UP:
                       restoreImage(petImageView);
               }
               return true;
           }
       });
    }

    public void buyTeddyBears()
    {

    }

    public void buyBananas()
    {

    }

    public void buySoap()
    {

    }

    public void progressBar()
    {

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                counter++;
                happinessProgressBar.setProgress(counter);
                hungerProgressBar.setProgress(counter);
                cleanProgressBar.setProgress(counter);

                if(counter == 100)
                    timer.cancel();

            }
        };

        timer.schedule(timerTask, 0, 20);
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
}