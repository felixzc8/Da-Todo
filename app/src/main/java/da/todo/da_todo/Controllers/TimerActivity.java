package da.todo.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import da.todo.da_todo.R;
//import com.example.da_todo.R;
import da.todo.da_todo.Reward.Pet;
import da.todo.da_todo.User.User;
import da.todo.da_todo.util.PrefUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import da.todo.da_todo.Task.Task;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Enable the Timer to run after click on a task
 *
 * @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */
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

    /**
     * Takes in the inputs from TasksActivity
     */
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

        //take in information from TasksActivity about the task
        taskID = (String) getIntent().getSerializableExtra("TaskID");
        id = (String) getIntent().getSerializableExtra("userID");
        user = (User) getIntent().getSerializableExtra("user");
        position = (int) getIntent().getSerializableExtra("position");
        userPet = user.getPet();

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

        start_button.setOnClickListener(view ->
        {
            startTimer();
            timerState = TimerState.Running;
            updateButtons();
        });
        pause_button.setOnClickListener(view ->
        {
            timer.cancel();
            timerState = TimerState.Paused;
            updateButtons();
        });
        stop_button.setOnClickListener(view ->
        {
            timer.cancel();
            onTimerFinished();
        });
    }

    /**
     * Enum of the possible timer states
     */
    public enum TimerState
    {
        Stopped, Paused, Running
    }

    /**
     * Resume timer function
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        initTimer();
    }

    /**
     * Pause timer function
     */
    @Override
    protected void onPause()
    {
        super.onPause();

        if (timerState == TimerState.Running)
        {
            timer.cancel();
        }
        //allow the timer to continue from the original time after leaving the app
        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this);
        PrefUtil.setSecondsRemaining(secondsRemaining, this);
        PrefUtil.setTimerState(timerState, this);
    }

    /**
     * Initalize timer function
     */
    private void initTimer()
    {
        //initiating the timer for a new task
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

    /**
     * Finished timer function
     */
    private void onTimerFinished()
    {
        //setting the countdown to end when the timer finishes
        timerState = TimerState.Stopped;
        setNewTimerLength();
        progress_countdown.setProgress(0);
        PrefUtil.setSecondsRemaining(timerLengthSeconds, this);
        secondsRemaining = timerLengthSeconds;
        updateButtons();
        updateCountdownUI();
    }

    /**
     * Start timer function
     */
    private void startTimer()
    {
        //set a new timer and calculate the number of seconds for the task
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

    /**
     * Set new timer function
     */
    private void setNewTimerLength()
    {
        //taking in the time input for each task to be projected on the progress bar
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
    }

    /**
     * Continue the same timer when the user leaves the task
     */
    private void setPreviousTimerLength()
    {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);
        progress_countdown.setMax(timerLengthSeconds.intValue());
    }

    /**
     * Updating the timer progress bar
     */
    private void updateCountdownUI()
    {
        int minutesUntilFinished = secondsRemaining.intValue() / 60;
        int secondsInMinuteUntilFinished = secondsRemaining.intValue() - minutesUntilFinished * 60;
        String secondsStr = Integer.toString(secondsInMinuteUntilFinished);
        String newStr = secondsStr.length() == 2 ? secondsStr : "0" + secondsStr;
        textView_countdown.setText(minutesUntilFinished + ":" + newStr);
        progress_countdown
                .setProgress((timerLengthSeconds.intValue() - secondsRemaining.intValue()));
    }

    /**
     * Setting the timer buttons to be displayed when clicked
     */
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

    /**
     * Calculating the users points after completing a task
     */
    public void updateTotalPoints(View v)
    {
        initTimer();
        //take the number of seconds left & original seconds of the task
        Long secondsLeft = PrefUtil.getSecondsRemaining(this);
        Long originalSeconds = PrefUtil.getPreviousTimerLengthSeconds(this);
        //convert the time into a decimal
        double secondsLeftInt = secondsLeft.intValue();
        double originalSecondsInt = originalSeconds.intValue();
        double taskPercentage = 1 - (((originalSecondsInt - secondsLeftInt) / originalSecondsInt));
        //multiply it by the original number of points
        int originalPoints = Integer.parseInt(pointsRewarded);
        double pointsGiven = taskPercentage * originalPoints;
        //add the points multiplier to the original number of points
        int pointsGivenInt = (int) pointsGiven + originalPoints;
        userPet.setPoints(pointsGivenInt);

        //go through the database and remove the task that has been finished
        String taskID = (String) getIntent().getSerializableExtra("TaskID");
        for (Task t : user.getTasks())
        {
            if (taskID.equals(t.getTaskUUID()))
            {
                user.getTasks().remove(t);
                firestore.collection("users").document(user.getID()).set(user);
                break;
            }
        }
        Intent intent = new Intent(getApplicationContext(), TasksActivity.class);
        startActivity(intent);
    }

    public void goBack(View v)
    {
        Intent goBack = new Intent(this, TasksActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }
}