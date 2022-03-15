package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.example.da_todo.util.PrefUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerActivity extends AppCompatActivity
{
    private CountDownTimer timer = null;
    private Long timerLengthSeconds = 0L;
    private Long secondsRemaining = 0L;
    private TimerState timerState = TimerState.Stopped;
    private FloatingActionButton start_button, pause_button, stop_button;
    private MaterialProgressBar progress_countdown;
    private TextView textView_countdown;
    private Pet userPet;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private int timeInt;
    private Button finishButton;
    private Long totalPoints = 0L;

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
        finishButton = findViewById(R.id.finish_Button_TimerActivity);

        userPet = (Pet) getIntent().getSerializableExtra("pet");

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
//        totalPoints = PrefUtil.getSecondsRemaining();
        onPause();
        Long secondsLeft = PrefUtil.getSecondsRemaining(this);
        Long originalSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);
        double secondsLeftInt = secondsLeft.intValue();
        double originalSecondsInt = originalSeconds.intValue();
        double taskPercentage = 100 - (((originalSecondsInt - secondsLeftInt) / originalSecondsInt) * 100);
        int taskPercentageInt = (int) taskPercentage;
        System.out.println("Still had % left");
        System.out.println(taskPercentageInt);

        System.out.println(totalPoints);
//        userPet.setTotalPoints(totalPoints);
        Intent goToTasksActivity = new Intent(this, TasksActivity.class);
        startActivity(goToTasksActivity);
    }

    public void goBack(View v)
    {
        Intent goBack = new Intent(this, TasksActivity.class);
        startActivity(goBack);
    }

    public void pointsRewarded(Long time)
    {

    }
}