package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Activity showing the user's pet
 *
 * @author @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */

public class PetActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    User user;
    Pet pet;
    String currentAction = "";
    int counter = 0;
    TextView petNameTextView, moneyTextView, teddyBearCountTextView, bananaCountTextView, soapCountTextView;
    ImageView petImageView, teddyBearImageView, bananaImageView, soapImageView;
    Button buyButton;
    ProgressBar happinessProgressBar;
    ProgressBar hungerProgressBar;
    ProgressBar cleanProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = (User) getIntent().getSerializableExtra("user");
        pet = user.getPet();
        petNameTextView = findViewById(R.id.petName_TextView_RewardsActivity);
        moneyTextView = findViewById(R.id.money_TextView_RewardsActivity);
        teddyBearCountTextView = findViewById(R.id.teddyBearCountTextView);
        bananaCountTextView = findViewById(R.id.bananaCountTextView);
        soapCountTextView = findViewById(R.id.soapCountTextView);
        petImageView = findViewById(R.id.petImageView);
        teddyBearImageView = findViewById(R.id.teddyBearImageView);
        bananaImageView = findViewById(R.id.bananaImageView);
        soapImageView = findViewById(R.id.soapImageView);
        buyButton = findViewById(R.id.buyButton);
        happinessProgressBar = findViewById(R.id.happinessProgressBar);
        hungerProgressBar = findViewById(R.id.hungerProgressBar);
        cleanProgressBar = findViewById(R.id.cleanProgressBar);
        petNameTextView.setText(pet.getName());
        teddyBearCountTextView.setText(String.valueOf(pet.getTeddyBear().getAmount()));
        bananaCountTextView.setText(String.valueOf(pet.getBanana().getAmount()));
        soapCountTextView.setText(String.valueOf(pet.getSoap().getAmount()));
        moneyTextView.setText(Integer.toString(pet.getPoints()));
        setPetImage(user);
        progressBar();
        petPressed();
    }

    /**
     *Finds which pet the user selected and displays it accordingly
     *
     * @param user User object
     */
    public void setPetImage(User user)
    {
        switch (user.getSelectedPet())
        {
            //depending on what pet the user selects, the image displayed is different
            case "dog":
                petImageView.setImageResource(R.drawable.dogpet);
                break;
            case "cat":
                petImageView.setImageResource(R.drawable.catpet);
                break;
            case "unicorn":
                petImageView.setImageResource(R.drawable.unicornpet);
                break;
            case "dragon":
                petImageView.setImageResource(R.drawable.dragonpet);
                break;
            case "hamster":
                petImageView.setImageResource(R.drawable.hamsterpet);
                break;
            case "panda":
                petImageView.setImageResource(R.drawable.pandapet);
                break;
        }
    }

    /**
     * Finds which action the user has selected
     *
     * @param v icon clicked (teddy bear, banana, soap)
     */
    public void selectedAction(View v)
    {
        switch (v.getId())
        {
            //create a variable which shows what is selected
            case R.id.teddyBearImageView:
                enlargeImage(findViewById(R.id.teddyBearImageView));
                restoreImage();
                buyButton.setText("buy teddy bears");
                currentAction = "teddy bear";
                break;

            case R.id.bananaImageView:
                enlargeImage(findViewById(R.id.bananaImageView));
                restoreImage();
                buyButton.setText("buy bananas");
                currentAction = "banana";
                break;

            case R.id.soapImageView:
                enlargeImage(findViewById(R.id.soapImageView));
                restoreImage();
                buyButton.setText("buy soap");
                currentAction = "soap";
                break;
        }
    }

    /**
     * Finds which image to restore to the original size
     */
    public void restoreImage()
    {
        switch (currentAction)
        {
            //reduce the image to it's original size
            case "":
                break;
            case "teddy bear":
                reduceImage(findViewById(R.id.teddyBearImageView));
                break;
            case "banana":
                reduceImage(findViewById(R.id.bananaImageView));
                break;
            case "soap":
                reduceImage(findViewById(R.id.soapImageView));
                break;
        }
    }

    /**
     * Increases the size of the icon
     * @param v icon view
     */
    public void enlargeImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = 200;
        v.getLayoutParams().width = 200;
    }

    /**
     * Decreases the size of the icon
     * @param v icon view
     */
    public void reduceImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = 100;
        v.getLayoutParams().width = 100;
    }

    /**
     * Reads the user's touch on the pet. It will increase the size of the pet when it is pressed,
     * and decrease the size when it is released.
     */
    @SuppressLint("ClickableViewAccessibility")
    public void petPressed()
    {
        petImageView.setOnTouchListener((view, motionEvent) ->
        {
            Log.i("TouchEvent", "touch detected");

            int eventType = motionEvent.getActionMasked();

            switch (eventType)
            {
                case MotionEvent.ACTION_DOWN:
                    petImageView.requestLayout();
                    petImageView.getLayoutParams().height =
                            petImageView.getLayoutParams().height + 100;
                    petImageView.getLayoutParams().width =
                            petImageView.getLayoutParams().height + 100;
                    petPushinP();
                    break;

                case MotionEvent.ACTION_UP:
                    petImageView.requestLayout();
                    petImageView.getLayoutParams().height =
                            petImageView.getLayoutParams().height - 100;
                    petImageView.getLayoutParams().width =
                            petImageView.getLayoutParams().height - 100;
            }
            return true;
        });
    }

    /**
     * Buys the correct item that the user has selected
     *
     * @param v buy button clicked
     */
    public void buyItem(View v)
    {
        switch (currentAction)
        {
            case "teddy bear":
                buyTeddyBears();
                updateUser();
                break;
            case "banana":
                buyBananas();
                updateUser();
                break;
            case "soap":
                buySoap();
                updateUser();
                break;
        }
    }

    /**
     * PUSHIN P
     *
     * method for if an item is clicked and then the pet is clicked
     */
    public void petPushinP()
    {
        //Pushin P less goo
        switch (currentAction)
        {
            case "":
                break;
            case "teddy bear":
                updateUserTeddy();
                break;
            case "banana":
                updateUserBanana();
                break;
            case "soap":
                updateUserSoap();
                break;
        }
    }

    /**
     * Updates the user object on firebase
     */
    public void updateUser()
    {
        firestore.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .set(user).addOnCompleteListener(task ->
        {
        });
    }

    /**
     * Updates the amount of teddy bears that the user has, given that the user has enough, and
     * purchases the teddy bear and updates the progress bar
     */
    public void updateUserTeddy()
    {
        //make sure the user has one or more teddy bears
        if (pet.getTeddyBear().getAmount() >= 1)
        {
            //increase progress of happinessProgressBar by 10 and remove 1 teddy bear from user
            happinessProgressBar.incrementProgressBy(10);
            pet.getTeddyBear().setAmount(pet.getTeddyBear().getAmount() - 1);
            user.setPet(pet);

            //remove 1 teddy bear from the counter on screen
            teddyBearCountTextView.setText(String.valueOf(pet.getTeddyBear().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough teddy bears!",
                    Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    /**
     * Updates the amount of bananas that the user has, given that the user has enough, and
     * purchases the teddy bear and updates the progress bar
     */
    public void updateUserBanana()
    {
        //make sure the user has one or more bananas
        if (pet.getBanana().getAmount() >= 1)
        {
            //increase progress of hungerProgressBar by 10 and remove 1 banana from user
            hungerProgressBar.incrementProgressBy(10);
            pet.getBanana().setAmount(pet.getBanana().getAmount() - 1);
            user.setPet(pet);

            //remove 1 banana from the counter on screen
            bananaCountTextView.setText(String.valueOf(pet.getBanana().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough bananas",
                    Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    /**
     * Updates the amount of soap that the user has, given that the user has enough, and
     * purchases the teddy bear and updates the progress bar
     */
    public void updateUserSoap()
    {
        //make sure the user has one or more soaps
        if (pet.getSoap().getAmount() >= 1)
        {
            //increase progress of cleanProgressBar by 10 and remove 1 soap from user
            cleanProgressBar.incrementProgressBy(10);
            pet.getSoap().setAmount(pet.getSoap().getAmount() - 1);
            user.setPet(pet);

            //remove 1 soap from the counter on screen
            soapCountTextView.setText(String.valueOf(pet.getSoap().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough soap!",
                    Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    /**
     * Adds the amount of teddy bears that the user has by 1 given that the user has enough money
     */
    public void buyTeddyBears()
    {
        //get the users points
        if (pet.getPoints() - pet.getTeddyBear().getPrice() >= 0)
        {
            //increase the amount of bears
            pet.getTeddyBear().setAmount(pet.getTeddyBear().getAmount() + 1);
            //subtract money from the user
            pet.minusPoints(pet.getTeddyBear().getPrice());
            user.setPet(pet);
            moneyTextView.setText("" + pet.getPoints());
            //display the new money view
            teddyBearCountTextView.setText(String.valueOf(pet.getTeddyBear().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough money!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Adds the amount of bananas that the user has by 1 given that the user has enough money
     */
    public void buyBananas()
    {
        //get the users points
        if (pet.getPoints() - pet.getBanana().getPrice() >= 0)
        {
            //increase the amount of bananas
            pet.getBanana().setAmount(pet.getBanana().getAmount() + 1);
            pet.minusPoints(pet.getBanana().getPrice());
            user.setPet(pet);
            moneyTextView.setText("" + pet.getPoints());
            //display the new money view
            bananaCountTextView.setText(String.valueOf(pet.getBanana().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough money!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Adds the amount ofsoap that the user has by 1 given that the user has enough money
     */
    public void buySoap()
    {
        //get the users points
        if (pet.getPoints() - pet.getSoap().getPrice() >= 0)
        {
            //increase the amount of soaps
            pet.getSoap().setAmount(pet.getSoap().getAmount() + 1);
            pet.minusPoints(pet.getSoap().getPrice());
            user.setPet(pet);
            moneyTextView.setText("" + pet.getPoints());
            //display the new money view
            soapCountTextView.setText(String.valueOf(pet.getSoap().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough money!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sets up the progress bar
     */
    public void progressBar()
    {
        //initial setup of the progress bars
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                //the counter is constantly increasing and the bars move with it
                counter++;
                happinessProgressBar.setProgress(counter);
                hungerProgressBar.setProgress(counter);
                cleanProgressBar.setProgress(counter);

                if (counter == 100)
                {
                    //stops when the bars are full
                    timer.cancel();

                    //begin the decay of the bars
                    progressNuts();
                }
            }
        };

        //counter increases every 20 milliseconds
        timer.schedule(timerTask, 0, 20);
    }

    /**
     * Decreases the progress bar over time
     */
    public void progressNuts()
    {
        //setting the decay of the progress bars
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Thread thread = new Thread(() ->
                {
                    try
                    {
                        //starting the method 100 milliseconds after activity is launched
                        Thread.sleep(100);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    //makes it so that it is a constant decay rate instead of setting to a var
                    happinessProgressBar.setProgress(happinessProgressBar.getProgress() - 1);
                    hungerProgressBar.setProgress(hungerProgressBar.getProgress() - 1);
                    cleanProgressBar.setProgress(cleanProgressBar.getProgress() - 1);
                });
                thread.start();
            }
        };

        //the decay only occurs once every 10 seconds
        timer.schedule(timerTask, 0, 10000);
    }

    /**
     * Brings the user back to their task list on click
     */
    public void backButton(View v)
    {
        Intent backButton = new Intent(this, TasksActivity.class);
        backButton.putExtra("user", user);
        startActivity(backButton);
    }
}