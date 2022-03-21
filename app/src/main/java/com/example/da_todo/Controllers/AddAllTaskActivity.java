package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.da_todo.R;
import com.example.da_todo.Task.Task;
import com.example.da_todo.User.User;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddAllTaskActivity extends AppCompatActivity
{
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private TextView taskName;
    private EditText taskTime;
    private EditText taskPoints;
    private ImageView taskPhoto;
    User user;
    String inputName;
    String image;
    String name;
    int timeRequired;
    int pointsRewarded;
    String taskUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_all_task);
        user = (User) getIntent().getSerializableExtra("user");

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            image = extras.getString("image");
            inputName = extras.getString("name");
        }
        taskName = findViewById(R.id.taskName_TextView_AddAllTaskActivity);
        taskTime = findViewById(R.id.taskTimeInputEditText);
        taskPoints = findViewById(R.id.taskPointsInputEditText);
        taskPhoto = findViewById(R.id.taskImage_ImageView_AddAllTaskActivity);
        taskName.setText(inputName);
        Glide.with(taskPhoto.getContext()).load(image).centerCrop().into(taskPhoto);
    }

    public void addTask(View view)
    {
        try
        {
            name = taskName.getText().toString();
            timeRequired = Integer.parseInt(taskTime.getText().toString());
            pointsRewarded = Integer.parseInt(taskPoints.getText().toString());
            taskUUID = UUID.randomUUID().toString();

            Task task = new Task(image, name, timeRequired, pointsRewarded, taskUUID);
            System.out.println(user.getID());
            firestore.collection("users").document(user.getID()).update("tasks",
                    FieldValue.arrayUnion(task));
            Toast.makeText(getApplicationContext(), "Added task", Toast.LENGTH_LONG).show();
            clearPage();
        } catch (Exception err)
        {
            err.printStackTrace();
            Toast.makeText(getApplicationContext(), "Incorrect input. Only input numbers",
                    Toast.LENGTH_LONG).show();
            taskTime.setText(null);
            taskPoints.setText(null);
        }
    }

    public void clearPage()
    {
        taskTime.setText(null);
        taskPoints.setText(null);
        taskPhoto.setImageDrawable(null);
        back();
    }

    private void back()
    {
        Intent goBack = new Intent(this, AllTaskActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }

    public void backButton(View view)
    {
        Intent goBack = new Intent(this, AllTaskActivity.class);
        goBack.putExtra("user", user);
        startActivity(goBack);
    }
}