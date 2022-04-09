package da.todo.da_todo.Controllers;

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
import da.todo.da_todo.Reward.Pet;
import da.todo.da_todo.Task.Task;
import da.todo.da_todo.User.User;
import da.todo.da_todo.tasksRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Shows a recyclerview of all the tasks in the user's tasklist
 *
 * @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */

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
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;
    String intentTime;
    ImageView goPetActivityButton;
    boolean hasItems;

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
        goPetActivityButton = findViewById(R.id.goPetActivityButton);
        noItems = findViewById(R.id.noItems_TextView_TasksActivity);
        noItemsImage = findViewById(R.id.noItems_ImageView_TasksActivity);
        noItemsImage.setVisibility(View.INVISIBLE);
        intentTime = (String) getIntent().getSerializableExtra("Time");
        getUser();
    }

    /**
     * When user logs in, their user object is retrieved
     */
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
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error getting user", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * If user has no tasks in list, then a task appears indicating that
     */
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

    /**
     * Sets the recyclerview adapter with the list of tasks the user has and a listener
     */
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

    /**
     * When a task in their recyclerview is clicked, page turns into the TimerActivity page
     */
    private void setOnClickListener()
    {
        listener = (v, position) ->
        {
            Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
            intent.putExtra("Time", String.valueOf(taskList.get(position)
                    .getTimeRequired()));
            intent.putExtra("Reward", String.valueOf(taskList.get(position)
                    .getPointsRewarded()));
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

    /**
     * Sets the pet images shown on screen
     * @param user object
     */
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

    /**
     * When button is clicked, user can go to the pin-protected AddTask pages
     * @param view button onclick
     */
    public void goToAddTaskPinActivity(View view)
    {
        Intent goToAddTaskPinActivity = new Intent(this, AddTaskPinActivity.class);
        goToAddTaskPinActivity.putExtra("user", user);
        startActivity(goToAddTaskPinActivity);
    }

    /**
     * When button is clicked, user can go to the pet pages
     * @param view button onclick
     */
    public void goPetActivity(View view)
    {
        Intent goToRewardsActivity = new Intent(this, PetActivity.class);
        goToRewardsActivity.putExtra("user", user);
        startActivity(goToRewardsActivity);
    }

    /**
     * When button is clicked, user can sign out of the main application and return to the
     * SignInActivity page and be logged out of this account
     * @param v button onclick
     */
    public void signOut(View v)
    {
        mAuth.signOut();
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        this.finish();
    }
}