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

    public void selectedAction(View v)
    {
        switch (v.getId())
        {
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

    public void restoreImage()
    {
        switch (currentAction)
        {
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

    public void enlargeImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = 200;
        v.getLayoutParams().width = 200;
    }

    public void reduceImage(View v)
    {
        v.requestLayout();
        v.getLayoutParams().height = 100;
        v.getLayoutParams().width = 100;
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

                switch (eventType)
                {
                    case MotionEvent.ACTION_DOWN:
                        petImageView.requestLayout();
                        petImageView.getLayoutParams().height = petImageView.getLayoutParams().height + 100;
                        petImageView.getLayoutParams().width = petImageView.getLayoutParams().height + 100;
                        petPushinP();
                        break;

                    case MotionEvent.ACTION_UP:
                        petImageView.requestLayout();
                        petImageView.getLayoutParams().height = petImageView.getLayoutParams().height - 100;
                        petImageView.getLayoutParams().width = petImageView.getLayoutParams().height - 100;
                }
                return true;
            }
        });
    }

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

    public void petPushinP()
    {
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

    public void updateUser()
    {
        firestore.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .set(user).addOnCompleteListener(task ->
        {
        });
    }

    public void updatePoints()
    {
        user.getPoints();
    }

    public void updateUserTeddy()
    {
        if (pet.getTeddyBear().getAmount() >= 1)
        {
            happinessProgressBar.incrementProgressBy(10);
            pet.getTeddyBear().setAmount(pet.getTeddyBear().getAmount() - 1);
            user.setPet(pet);
            teddyBearCountTextView.setText(String.valueOf(pet.getTeddyBear().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough teddy bears!", Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    public void updateUserBanana()
    {
        if (pet.getBanana().getAmount() >= 1)
        {
            hungerProgressBar.incrementProgressBy(10);
            pet.getBanana().setAmount(pet.getBanana().getAmount() - 1);
            user.setPet(pet);
            bananaCountTextView.setText(String.valueOf(pet.getBanana().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough bananas", Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    public void updateUserSoap()
    {
        if (pet.getSoap().getAmount() >= 1)
        {
            cleanProgressBar.incrementProgressBy(10);
            pet.getSoap().setAmount(pet.getSoap().getAmount() - 1);
            user.setPet(pet);
            soapCountTextView.setText(String.valueOf(pet.getSoap().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough soap!", Toast.LENGTH_LONG).show();
        }

        updateUser();
    }

    public void buyTeddyBears()
    {
        if (pet.getPoints() - pet.getTeddyBear().getPrice() >= 0)
        {
            pet.getTeddyBear().setAmount(pet.getTeddyBear().getAmount() + 1);
            pet.minusPoints(pet.getTeddyBear().getPrice());
            user.setPet(pet);
            moneyTextView.setText("" + pet.getPoints());
            teddyBearCountTextView.setText(String.valueOf(pet.getTeddyBear().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buyBananas()
    {
        if (pet.getPoints() - pet.getBanana().getPrice() >= 0)
        {
            pet.getBanana().setAmount(pet.getBanana().getAmount() + 1);
            pet.minusPoints(pet.getBanana().getPrice());
            user.setPet(pet);
            moneyTextView.setText("" + pet.getPoints());
            bananaCountTextView.setText(String.valueOf(pet.getBanana().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_LONG).show();
        }
    }

    public void buySoap()
    {
        if (pet.getPoints() - pet.getSoap().getPrice() >= 0)
        {
            pet.getSoap().setAmount(pet.getSoap().getAmount() + 1);
            pet.minusPoints(pet.getSoap().getPrice());
            user.setPet(pet);
            moneyTextView.setText("" + pet.getPoints());
            soapCountTextView.setText(String.valueOf(pet.getSoap().getAmount()));
        } else
        {
            Toast.makeText(getApplicationContext(), "You don't have enough money!", Toast.LENGTH_LONG).show();
        }
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

                if (counter == 100)
                {
                    timer.cancel();

                    progressNuts();
                }
            }
        };
        timer.schedule(timerTask, 0, 20);
    }

    public void progressNuts()
    {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(100);
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        happinessProgressBar.setProgress(happinessProgressBar.getProgress() - 1);
                        hungerProgressBar.setProgress(hungerProgressBar.getProgress() - 1);
                        cleanProgressBar.setProgress(cleanProgressBar.getProgress() - 1);
                    }
                });
                thread.start();
            }
        };
        timer.schedule(timerTask, 0, 10000);
    }

    public void backButton(View v)
    {
        Intent backButton = new Intent(this, TasksActivity.class);
        backButton.putExtra("user", user);
        startActivity(backButton);
    }
}