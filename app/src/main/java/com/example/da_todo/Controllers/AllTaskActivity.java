package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.da_todo.R;
import com.example.da_todo.Task.Task;

import java.util.ArrayList;

public class AllTaskActivity extends AppCompatActivity {
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);
        recyclerView = findViewById(R.id.recyclerView_allTaskActivity);

        taskList = new ArrayList<>();

        setTaskInfo();
        setAdapter();
    }

    private void setAdapter() {
        allTaskRecyclerAdapter adapter = new allTaskRecyclerAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTaskInfo() {
        taskList.add(new Task(null, "Eat breakfast", 10, 10, null));
        taskList.add(new Task(null, "Eat breakfast", 10, 10, null));
        taskList.add(new Task(null, "Eat breakfast", 10, 10, null));
        taskList.add(new Task(null, "Eat soup", 10, 10, null));

    }

    public void goToAddTaskActivity(View v){
        Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);
        startActivity(goToAddTaskActivity);
    }

    public void backButton(View v){
        Intent goBackIntent = new Intent(this, TasksActivity.class);
        startActivity(goBackIntent);
    }
}