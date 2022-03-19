package com.example.da_todo.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.example.da_todo.util.PrefUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firestore.v1.WriteResult;

import java.util.HashMap;
import java.util.Map;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerActivity extends AppCompatActivity
{
    User user;
    String id;
    String taskID;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    int position;
    private CountDownTimer timer = null;
    private Long timerLengthSeconds = 0L;
    private Long secondsRemaining = 0L;
    private TimerState timerState = TimerState.Stopped;
    private FloatingActionButton start_button, pause_button, stop_button;
    private MaterialProgressBar progress_countdown;
    private TextView textView_countdown;
    Pet userPet;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private int timeInt;
    private String pointsRewarded;
    private String name;
    private String imageURL;
    private TextView nameTextView;
    private ImageView imageView;
    private TextView rewardTextView;
    private Intent intent;

    public enum TimerState
    {
        Stopped, Paused, Running
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        start_button = findViewById(R.id.startTimer_Button_TimerActivity);
        pause_button = findViewById(R.id.pauseTimer_Button_TimerActivity);
        stop_button = findViewById(R.id.stopTimer_Button_TimerActivity);
        progress_countdown = findViewById(R.id.countdown_ProgressBar_TimerActivity);
        textView_countdown = findViewById(R.id.timeDisplay_TextView_TimerActivity);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        taskID = (String) getIntent().getSerializableExtra("TaskID");
        id = (String) getIntent().getSerializableExtra("userID");
        user = (User) getIntent().getSerializableExtra("user");
        userPet = (Pet) getIntent().getSerializableExtra("pet");
        position = (int) getIntent().getSerializableExtra("position");
//        System.out.println("HEY ORIGINAL POINTS HERE");
//        userPet.getPoints();

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            pointsRewarded = extras.getString("Reward");
            name = extras.getString("Name");
            imageURL = extras.getString("Image");
        }
        nameTextView = findViewById(R.id.taskName_TextView_TimerActivity);
        nameTextView.setText(name);

        imageView = findViewById(R.id.imageView_timerActivity);
        Glide.with(getApplicationContext()).load(imageURL).centerCrop().into(imageView);

        rewardTextView = findViewById(R.id.points_TextView_TimerActivity);
        rewardTextView.setText(pointsRewarded);

        start_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startTimer();
                timerState = TimerState.Running;
                updateButtons();
            }
        });
        pause_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                timer.cancel();
                timerState = TimerState.Paused;
                updateButtons();
            }
        });
        stop_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                timer.cancel();
                onTimerFinished();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initTimer();
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (timerState == TimerState.Running)
        {
            timer.cancel();
        }
        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this);
        PrefUtil.setSecondsRemaining(secondsRemaining, this);
        PrefUtil.setTimerState(timerState, this);
    }

    private void initTimer()
    {
        timerState = PrefUtil.getTimerState(this);

        if (timerState == TimerState.Stopped)
        {
            setNewTimerLength();
        } else
        {
            setPreviousTimerLength();
        }

        secondsRemaining = (timerState == TimerState.Running || timerState == TimerState.Paused) ?
                PrefUtil.getSecondsRemaining(this) : timerLengthSeconds;

        if (secondsRemaining <= 0)
        {
            onTimerFinished();
        } else if (timerState == TimerState.Running)
        {
            startTimer();
        }
        updateButtons();
        updateCountdownUI();
    }

    private void onTimerFinished()
    {
        timerState = TimerState.Stopped;
        setNewTimerLength();
        progress_countdown.setProgress(0);
        PrefUtil.setSecondsRemaining(timerLengthSeconds, this);
        secondsRemaining = timerLengthSeconds;
        updateButtons();
        updateCountdownUI();
    }

    private void startTimer()
    {
        timerState = TimerState.Running;
        timer = new CountDownTimer(secondsRemaining * 1000, 1000)
        {
            @Override
            public void onTick(long l)
            {
                secondsRemaining = l / 1000;
                updateCountdownUI();
            }

            @Override
            public void onFinish()
            {
                onTimerFinished();
            }
        }.start();
    }

    private void setNewTimerLength()
    {
        String time = "0";
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            time = extras.getString("Time");
            timeInt = Integer.parseInt(time);
            System.out.println("TIME VALUE HERE" + timeInt);
        }
        timerLengthSeconds = (timeInt * 60L);
        progress_countdown.setMax(timerLengthSeconds.intValue());

//        int lengthInMinutes = PrefUtil.getTimerLength(this);
//        timerLengthSeconds = (lengthInMinutes * 60L);
//        progress_countdown.setMax(timerLengthSeconds.intValue());
    }

    private void setPreviousTimerLength()
    {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);
        progress_countdown.setMax(timerLengthSeconds.intValue());
    }

    private void updateCountdownUI()
    {
        int minutesUntilFinished = secondsRemaining.intValue() / 60;
        int secondsInMinuteUntilFinished = secondsRemaining.intValue() - minutesUntilFinished * 60;
        String secondsStr = Integer.toString(secondsInMinuteUntilFinished);
        String newStr = secondsStr.length() == 2 ? secondsStr : "0" + secondsStr;
        textView_countdown.setText(minutesUntilFinished + ":" + newStr);
        progress_countdown.setProgress((timerLengthSeconds.intValue() - secondsRemaining.intValue()));
    }

    private void updateButtons()
    {
        switch (timerState)
        {
            case Running:
                start_button.setEnabled(false);
                pause_button.setEnabled(true);
                stop_button.setEnabled(true);
                break;

            case Paused:
                start_button.setEnabled(true);
                pause_button.setEnabled(false);
                stop_button.setEnabled(true);
                break;

            case Stopped:
                start_button.setEnabled(true);
                pause_button.setEnabled(false);
                stop_button.setEnabled(false);
                break;
        }
    }

    public void updateTotalPoints(View v)
    {
        initTimer();
        Long secondsLeft = PrefUtil.getSecondsRemaining(this);
        Long originalSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);

        double secondsLeftInt = secondsLeft.intValue();
        double originalSecondsInt = originalSeconds.intValue();
        double taskPercentage = 1 - (((originalSecondsInt - secondsLeftInt) / originalSecondsInt));
        double pointsGiven = (taskPercentage + 1) * 50;
        int originalPoints = Integer.parseInt(pointsRewarded);
        int pointsGivenInt = (int) pointsGiven + originalPoints;

        userPet.setPoints(pointsGivenInt);
        System.out.println("USER POINTS HERE");
        System.out.println(userPet.getPoints());

        position = (int) getIntent().getSerializableExtra("position");
        String positionString = String.valueOf(position);

//        firestore.collection("users").document(user.getID()).update(positionString, FieldValue.delete());
//        firestore.collection("users").document(user.getID()).update("tasks/0", FieldValue.delete());
//        firestore.collection("users").document(user.getID()).collection("tasks").document(positionString).update(positionString, FieldValue.delete());

//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getID()).child("tasks").child(positionString);
//        ref.removeValue();

//        firestore.collection("users").document(user.getID()).collection("tasks").document("0").delete();

        //remove task from firebase

        firestore.collection("users").document(user.getID()).update("tasks", FieldValue.arrayRemove(position));

        try
        {
            System.out.println("TASK ID HERE");
            System.out.println(taskID);
            System.out.println(position);

//            DocumentReference documentReference = firestore.collection("/users").document(mUser.getUid());
//            documentReference.delete().wait();

            DocumentReference documentReference = firestore.collection("/users").document(mUser.getUid())
                    .collection("tasks").document(positionString);
            documentReference.delete().wait();

            firestore.collection("/users").document(mUser.getUid())
                    .collection("tasks").document(positionString).delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                System.out.println("NO WAY IT WORKS");
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error getting user", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
        intent.putExtra("newPet", userPet);
        startActivity(intent);
    }

    public void goBack(View v)
    {
        Intent goBack = new Intent(this, TasksActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }
}