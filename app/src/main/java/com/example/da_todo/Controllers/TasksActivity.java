package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.Task.Task;
import com.example.da_todo.recyclerAdapter;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity
{
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerAdapter;
    Pet userPet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_acvitiy);
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_tasksActivity);

        userPet = (Pet) getIntent().getSerializableExtra("pet");

        setTaskInfo();
        setAdapter();
    }

    private void setAdapter()
    {
        recyclerAdapter adapter = new recyclerAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTaskInfo()
    {
        taskList.add(new Task(null, "Eat Dinner", "Eat at 8pm, 1 bowl", 30, 5));
        taskList.add(new Task(null, "Shower", "Save water", 10, 1));
        taskList.add(new Task(null, "Brush Teeth", "Right before bed", 5, 3));
        taskList.add(new Task(null, "Pack bag", "Check schedule beforehand", 10, 5));
    }

    public void goToAddTaskActivity(View view)
    {
        Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);
        startActivity(goToAddTaskActivity);
    }

    public void goToRewardsActivity(View view)
    {
        Intent goToRewardsActivity = new Intent(this, RewardsActivity.class);
        goToRewardsActivity.putExtra("pet", userPet);
        startActivity(goToRewardsActivity);
    }

}