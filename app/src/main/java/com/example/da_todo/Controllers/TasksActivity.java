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

    String hi;

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
        taskList = new ArrayList<>();

        setContentView(R.layout.activity_tasks);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();


        getUser();
        recyclerView = findViewById(R.id.recyclerView_allTaskActivity);
        userPet = (Pet) getIntent().getSerializableExtra("pet");
        intentTime = (String) getIntent().getSerializableExtra("Time");
//        getTasks();
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
                            taskList = user.getTasks();
                            System.out.println(taskList);
                            setAdapter();
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
        listener = (v, position) ->
        {
            Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
            intent.putExtra("Time", String.valueOf(taskList.get(position).getTimeRequired()));
            intent.putExtra("user", user);
            intent.putExtra("pet", userPet);
            startActivity(intent);
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