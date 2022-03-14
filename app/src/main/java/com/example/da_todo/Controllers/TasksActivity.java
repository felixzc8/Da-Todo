package com.example.da_todo.Controllers;

import androidx.annotation.NonNull;
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
import com.example.da_todo.tasksRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity
{
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerAdapter;
    private tasksRecyclerAdapter.RecyclerViewClickListener listener;
    Pet userPet;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;
    String intentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();

        getUser();
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_allTaskActivity);
        userPet = (Pet) getIntent().getSerializableExtra("pet");
        intentTime = (String) getIntent().getSerializableExtra("Time");
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
        tasksRecyclerAdapter adapter = new tasksRecyclerAdapter(taskList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener()
    {
        listener = new tasksRecyclerAdapter.RecyclerViewClickListener()
        {
            @Override
            public void onClick(View v, int position)
            {
                //smth to do with task id
                firestore.collection("tasks")
                        .whereEqualTo("taskUUID", "5594cab7-4ac0-4b90-8273-fc07ff7d3538")
                        .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Log.d("GET DOCUMENTS", "SUCCESS");

                        for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult())
                        {
                            Task taskInfo = queryDocumentSnapshot.toObject(Task.class);
                            int timeInt = taskInfo.getTimeRequired();
                            String timeString = String.valueOf(timeInt);

                            Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                            intent.putExtra("Time", timeString);
//                            intent.putExtra("TaskID", )
                            intent.putExtra("pet", userPet);
                            startActivity(intent);
                        }
                    }
                    else
                    {
//                        Log.d("Error getting documents:", task.getException());
                    }
                });

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

    public void goToAllTaskActivity(View view)
    {
        Intent goToAllTaskActivity = new Intent(this, AllTaskActivity.class);
        goToAllTaskActivity.putExtra("user", user);
        startActivity(goToAllTaskActivity);
    }

    public void goToRewardsActivity(View view)
    {
        Intent goToRewardsActivity = new Intent(this, RewardsActivity.class);
        goToRewardsActivity.putExtra("user", user);
        startActivity(goToRewardsActivity);
    }

    public void signOut(View v)
    {
        mAuth.signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        this.finish();
    }
}