package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.da_todo.R;
import com.example.da_todo.Task.Task;
import com.example.da_todo.User.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class AddAllTaskActivity extends AppCompatActivity {
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private TextView taskName;
    private EditText taskTime;
    private EditText taskPoints;
    private ImageView taskPhoto;

    User user;


    String image;
    String name;
    int timeRequired;
    int pointsRewarded;
    String taskUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_all_task);

        user = (User) getIntent().getSerializableExtra("user");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            image = extras.getString("image");
            name = extras.getString("name");
        }

        taskName = findViewById(R.id.taskName_TextView_AddAllTaskActivity);
        taskTime = findViewById(R.id.taskTimeInputEditText);
        taskPoints = findViewById(R.id.taskPointsInputEditText);
        taskPhoto  = findViewById(R.id.taskImage_ImageView_AddAllTaskActivity);

        Glide.with(taskPhoto.getContext()).load(image).centerCrop().into(taskPhoto);
    }

    public void addTask(View view){
        name = taskName.getText().toString();
        timeRequired = Integer.parseInt(taskTime.getText().toString());
        pointsRewarded = Integer.parseInt(taskPoints.getText().toString());
        taskUUID = UUID.randomUUID().toString();

        Task task = new Task(image, name, timeRequired, pointsRewarded, taskUUID);
        firestore.collection("users").document(user.getID()).set(task);
    }
}