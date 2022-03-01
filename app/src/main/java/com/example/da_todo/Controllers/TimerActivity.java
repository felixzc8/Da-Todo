package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.da_todo.R;
import com.example.da_todo.util.PrefUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class TimerActivity extends AppCompatActivity
{
    public enum TimerState
    {
        Stopped, Paused, Running
    }

    private CountDownTimer timer = null;
    private Long timerLengthSeconds = 0L;
    private Long secondsRemaining = 0L;
    private TimerState timerState = TimerState.Stopped;
    private FloatingActionButton start_button, pause_button, stop_button;
    private MaterialProgressBar progress_countdown;
    private TextView textView_countdown;

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
        }
        else
        {
            setPreviousTimerLength();
        }

        secondsRemaining = (timerState == TimerState.Running || timerState == TimerState.Paused)?
                PrefUtil.getSecondsRemaining(this):timerLengthSeconds;

        if (secondsRemaining <= 0)
        {
            onTimerFinished();
        }
        else if (timerState == TimerState.Running)
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
        int lengthInMinutes = PrefUtil.getTimerLength(this);
        timerLengthSeconds = (lengthInMinutes * 60L);
        progress_countdown.setMax(timerLengthSeconds.intValue());
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
}