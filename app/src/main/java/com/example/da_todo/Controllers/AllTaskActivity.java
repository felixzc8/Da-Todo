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
import com.example.da_todo.User.User;
import com.example.da_todo.allTaskRecyclerAdapter;

import java.util.ArrayList;

public class AllTaskActivity extends AppCompatActivity {
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private String brushTeethImageURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fbrush-teeth.png?alt=media&token=b123da70-8537-4952-a400-4dcfc557e983";
    private String eatBreakfastURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Feat-breakfast.png?alt=media&token=e0e1ac3c-c0bc-48d2-b01c-50e4f05170b5";
    private String packSchoolBagURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fpack-school-bag.png?alt=media&token=e14dfa80-29b0-4d7d-8141-504a37c1e1b9";
    private String changeClothesURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fchange-clothes.png?alt=media&token=d0605e20-0f2e-42f6-970b-3c060b583bbc";

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);
        recyclerView = findViewById(R.id.recyclerView_allTaskActivity);

        user = (User) getIntent().getSerializableExtra("user");

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

    private void setTaskInfo()
    {
        taskList.add(new Task(brushTeethImageURL, "Brush Teeth", 5, 10, null));
        taskList.add(new Task(eatBreakfastURL, "Eat Breakfast", 15, 20, null));
        taskList.add(new Task(packSchoolBagURL, "Pack School Bag", 5, 10, null));
        taskList.add(new Task(changeClothesURL, "Change Clothes", 5, 5, null));

    }

    public void goToAddTaskActivity(View v){
        Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);
        goToAddTaskActivity.putExtra("user", user);
        startActivity(goToAddTaskActivity);
    }

    public void backButton(View v){
        Intent goBackIntent = new Intent(this, TasksActivity.class);
        startActivity(goBackIntent);
    }
}