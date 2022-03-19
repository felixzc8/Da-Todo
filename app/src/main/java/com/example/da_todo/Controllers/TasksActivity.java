package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.Task.Task;
import com.example.da_todo.User.User;
import com.example.da_todo.tasksRecyclerAdapter;
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
    private tasksRecyclerAdapter.RecyclerViewClickListener listener;
    private TextView noItems;
    private ImageView noItemsImage;

    tasksRecyclerAdapter adapter;
    Pet userPet;
    Pet newUserPet;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;
    String intentTime;
    ImageView goPetActivityButton;

    boolean hasItems;
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
        recyclerView = findViewById(R.id.recyclerView_allTaskActivity);
        newUserPet = (Pet) getIntent().getSerializableExtra("newPet");
        intentTime = (String) getIntent().getSerializableExtra("Time");
        goPetActivityButton = findViewById(R.id.goPetActivityButton);
        noItems = findViewById(R.id.noItems_TextView_TasksActivity);
        noItemsImage = findViewById(R.id.noItems_ImageView_TasksActivity);
        noItemsImage.setVisibility(View.INVISIBLE);
        getUser();
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
                            userPet = user.getPet();
                            Log.d("USER OBJECT", "user name: " + user.getName());
                            taskList = user.getTasks();
                            if (taskList.size() == 0)
                            {
                                hasItems = false;
                                showNoItems();
                                noItemsImage.setVisibility(View.VISIBLE);
                            } else
                            {
                                hasItems = true;
                            }
                            setAdapter();
                            setPetImage(user);
                        }
                    });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error getting user", Toast.LENGTH_SHORT).show();
        }
    }

    public void showNoItems()
    {
        if (hasItems == false)
        {
            noItems.setText("Yay!!! \nYou have completed all your tasks :) ");
        } else
        {
            noItems.setText(null);
        }
    }

    private void setAdapter()
    {
        setOnClickListener();
        adapter = new tasksRecyclerAdapter(taskList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
//        setUpListViewListerner();
    }

//    private void setUpListViewListerner()
//    {
//        recyclerView.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//            }
//        });
//    }

    private void setOnClickListener()
    {
        listener = (v, position) ->
        {
            Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
            intent.putExtra("Time", String.valueOf(taskList.get(position).getTimeRequired()));
            intent.putExtra("Reward", String.valueOf(taskList.get(position).getPointsRewarded()));
            intent.putExtra("Name", taskList.get(position).getName());
            intent.putExtra("Image", taskList.get(position).getImage());
            intent.putExtra("TaskID", taskList.get(position).getTaskUUID());
            intent.putExtra("user", user);
            intent.putExtra("pet", userPet);
            intent.putExtra("userID", user.getID());
            intent.putExtra("position", position);
            System.out.println("POSITION HERE");
            System.out.println(position);
            startActivity(intent);
        };
    }

    public void setPetImage(User user)
    {
        switch (user.getSelectedPet())
        {
            case "dog":
                goPetActivityButton.setImageResource(R.drawable.dogpet);
                break;
            case "cat":
                goPetActivityButton.setImageResource(R.drawable.catpet);
                break;
            case "unicorn":
                goPetActivityButton.setImageResource(R.drawable.unicornpet);
                break;
            case "dragon":
                goPetActivityButton.setImageResource(R.drawable.dragonpet);
                break;
            case "hamster":
                goPetActivityButton.setImageResource(R.drawable.hamsterpet);
                break;
            case "panda":
                goPetActivityButton.setImageResource(R.drawable.pandapet);
                break;
        }
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

    public void goToAddTaskPinActivity(View view)
    {
        Intent goToAddTaskPinActivity = new Intent(this, AddTaskPinActivity.class);
        goToAddTaskPinActivity.putExtra("user", user);
        startActivity(goToAddTaskPinActivity);
    }

    public void goPetActivity(View view)
    {
        Intent goToRewardsActivity = new Intent(this, PetActivity.class);
        goToRewardsActivity.putExtra("user", user);
        goToRewardsActivity.putExtra("newUserPet", newUserPet);
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