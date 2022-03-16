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
import java.util.HashMap;
import java.util.List;

public class TasksActivity extends AppCompatActivity
{
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private RecyclerView recyclerAdapter;
    private tasksRecyclerAdapter.RecyclerViewClickListener listener;

    tasksRecyclerAdapter adapter;

    Pet userPet;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;
    String intentTime;

    String imageURLString;
    String nameString;
    int timeInt;
    int pointsRewardedInt;
    String idString;

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
        setAdapter();
        getTasks();
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

    public void getTasks()
    {
        firestore.collection("vehicles")
                .whereEqualTo("open", true)
                .whereGreaterThan("seatsLeft", 0)
                .get()
                .addOnCompleteListener(task ->
                {
                    if (task.isSuccessful())
                    {
                        Log.d("get documents ", "success");

                        for (QueryDocumentSnapshot qds : task.getResult())
                        {
                            Task t = qds.toObject(Task.class);
                            taskList.add(t);
                        }
                        System.out.println("TASKS: " + taskList);
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Log.d("Error getting documents: ", "" + task.getException());
                    }
                });
    }

    private void setAdapter()
    {
        setOnClickListener();
        adapter = new tasksRecyclerAdapter(taskList, listener);
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
                        .whereEqualTo("taskUUID", "4ddbea4d-e5de-4312-ad51-a031b41336bd")
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
                            intent.putExtra("user", user);
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

//        taskList.add(new Task(null, "Eat Dinner", 30, 5, null));
//        taskList.add(new Task(null, "Shower", 10, 1, null));
//        taskList.add(new Task(null, "Brush Teeth", 5, 3, null));
//        taskList.add(new Task(null, "Pack bag",  10, 5, null));
//        try{
//            firestore.collection("users").document(mUser.getUid()).get().addOnCompleteListener(task -> {
//                if(task.isSuccessful()){
//                    DocumentSnapshot ds = task.getResult();
//                    List<Task> tasksList = (List<Task>)ds.getData().get("tasks");
//                    for(Task taskX: tasksList){
//                        imageURLString = taskX.getImage();
//                        nameString = taskX.getName();
//                        timeInt = taskX.getTimeRequired();
//                        pointsRewardedInt = taskX.getPointsRewarded();
//                        idString = taskX.getTaskUUID();
//                        taskList.add(new Task(imageURLString, nameString, timeInt, pointsRewardedInt, idString));
//                    }
//                }
//            });
//        } catch (Exception err){
//            err.printStackTrace();
//        }
    }

    public void goToAllTaskActivity(View view)
    {
        Intent goToAllTaskActivity = new Intent(this, AllTaskActivity.class);
        goToAllTaskActivity.putExtra("user", user);
        startActivity(goToAllTaskActivity);
    }

    public void goToRewardsActivity(View view)
    {
        Intent goToRewardsActivity = new Intent(this, PetActivity.class);
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