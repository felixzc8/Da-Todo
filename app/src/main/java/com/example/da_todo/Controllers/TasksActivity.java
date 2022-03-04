package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.Task.Task;
import com.example.da_todo.User.User;
import com.example.da_todo.recyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity
{
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerAdapter;
    private com.example.da_todo.recyclerAdapter.RecyclerViewClickListener listener;
    Pet userPet;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_tasksActivity);
        userPet = (Pet) getIntent().getSerializableExtra("pet");
        setTaskInfo();
        setAdapter();
    }

    public void getUser()
    {
        try
        {
            firestore.collection("/users").document(mUser.getUid()).get()
                    .addOnCompleteListener(task ->
                    {
                        DocumentSnapshot ds = task.getResult();
                        if (task.isSuccessful())
                        {
                            user = ds.toObject(User.class);
                            Log.d("USER OBJECT", "user name: " + user.getName());
                        }
                    });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error getting user", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter()
    {
        setOnClickListener();
        recyclerAdapter adapter = new recyclerAdapter(taskList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener()
    {
        listener = new recyclerAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                intent.putExtra("Time", "12");
                startActivity(intent);
            }
        };
    }

    private void setTaskInfo()
    {
        taskList.add(new Task(null, "Eat Dinner", 30, 5, null));
        taskList.add(new Task(null, "Shower", 10, 1, null));
        taskList.add(new Task(null, "Brush Teeth", 5, 3, null));
        taskList.add(new Task(null, "Pack bag",  10, 5, null));
    }

    public void goToAddTaskActivity(View view)
    {
        Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);
        goToAddTaskActivity.putExtra("user", user);
        startActivity(goToAddTaskActivity);
    }

    public void goToRewardsActivity(View view)
    {
        Intent goToRewardsActivity = new Intent(this, RewardsActivity.class);
        goToRewardsActivity.putExtra("user", user);
        startActivity(goToRewardsActivity);
    }

    public void signOut(View view)
    {

    }
}