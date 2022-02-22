package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.da_todo.R;
import com.example.da_todo.Task.Task;
import com.example.da_todo.recyclerAdapter;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity
{
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_acvitiy);
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_tasksActivity);

        setTaskInfo();
        setAdapter();
    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTaskInfo() {
        taskList.add(new Task(null, "Eat Dinner", "Eat at 8pm, 1 bowl", 30, 5));
        taskList.add(new Task(null, "Shower", "Save water", 10, 1));
        taskList.add(new Task(null, "Brush Teeth", "Right before bed", 5, 3));
        taskList.add(new Task(null, "Pack bag", "Check schedule beforehand", 10, 5));
    }

}