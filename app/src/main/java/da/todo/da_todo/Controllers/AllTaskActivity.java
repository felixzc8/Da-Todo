package da.todo.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.example.da_todo.R;
import da.todo.da_todo.R;
import da.todo.da_todo.Task.Task;
import da.todo.da_todo.User.User;
import da.todo.da_todo.allTaskRecyclerAdapter;

import java.util.ArrayList;

/**
 * Displays a recycler view of all the default tasks that we have created. User can click into the
 * tasks to add them to their lists
 *
 * @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */
public class AllTaskActivity extends AppCompatActivity
{
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private allTaskRecyclerAdapter.RecyclerViewClickListener listener;
    private String brushTeethImageURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fbrush-teeth.png?alt=media&token=b123da70-8537-4952-a400-4dcfc557e983";
    private String eatBreakfastURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Feat-breakfast.png?alt=media&token=e0e1ac3c-c0bc-48d2-b01c-50e4f05170b5";
    private String packSchoolBagURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fpack-school-bag.png?alt=media&token=e14dfa80-29b0-4d7d-8141-504a37c1e1b9";
    private String changeClothesURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fchange-clothes.png?alt=media&token=d0605e20-0f2e-42f6-970b-3c060b583bbc";
    private String doChoresURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fdo-chores.png?alt=media&token=2e4fc188-e0c7-4827-a0fd-29029b7302bf";
    private String doHomeworkURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fdo-homework.png?alt=media&token=15a8407b-dc3d-45cc-be7a-2c48f514f5bf";
    private String makeBedURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fmake-bed.png?alt=media&token=2ae774eb-c639-4850-a2ce-0551811c23d0";
    private String readBookURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fread-book.png?alt=media&token=cfad35bb-e18f-4f52-958c-07acb32c1813";
    private String takeShowerURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Ftake-shower.png?alt=media&token=2a540806-a6d4-4228-8412-1b464f718743";
    private String tidyUpURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Ftidy-up.png?alt=media&token=e63e1019-698b-41a3-9f35-51a69501b8fd";
    private String turnLightsOnOffURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fturn-lights-on-off.png?alt=media&token=2254db79-6bd2-4b65-b984-128e6d6dcceb";
    private String washHandsURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fwash-hands.png?alt=media&token=7e5943bc-33a7-4bb1-805d-d6bb4fad8157";
    private String washFaceURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fwash-face.png?alt=media&token=c3ba309b-e787-45f3-be5e-dd1986b7dcb7";
    private String useToiletURL = "https://firebasestorage.googleapis.com/v0/b/da-todo.appspot.com/o/default_task_images%2Fuse-toilet.png?alt=media&token=bb3360b1-1fac-4632-8b4c-8d4b6a790501";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);
        recyclerView = findViewById(R.id.recyclerView_allTaskActivity);
        user = (User) getIntent().getSerializableExtra("user");
        taskList = new ArrayList<>();
        setTaskInfo();
        setAdapter();
    }

    /**
     * Sets the recyclerview adapter using the list of tasks we created and a listner
     */
    private void setAdapter()
    {
        setOnClickListener();
        allTaskRecyclerAdapter adapter = new allTaskRecyclerAdapter(taskList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * When a recyclerview item is selected, the page goes into the AddAllTaskActivity page
     * for users to add task to their list
     */
    private void setOnClickListener()
    {
        listener = (view, position) ->
        {
            Intent goToAddAllTaskActivity = new Intent(getApplicationContext(),
                    AddAllTaskActivity.class);
            goToAddAllTaskActivity.putExtra("image", taskList.get(position).getImage());
            goToAddAllTaskActivity.putExtra("name", taskList.get(position).getName());
            goToAddAllTaskActivity.putExtra("user", user);
            startActivity(goToAddAllTaskActivity);
        };
    }

    /**
     * Adds all the default tasks to the arrayList
     */
    private void setTaskInfo()
    {
        taskList.add(new Task(brushTeethImageURL, "Brush Teeth", 0,
                0, null));
        taskList.add(new Task(eatBreakfastURL, "Eat Breakfast", 0,
                0, null));
        taskList.add(new Task(packSchoolBagURL, "Pack School Bag", 0,
                0, null));
        taskList.add(new Task(changeClothesURL, "Change Clothes", 0,
                0, null));
        taskList.add(new Task(doChoresURL, "Do Chores", 0,
                0, null));
        taskList.add(new Task(doHomeworkURL, "Do Homework", 0,
                0, null));
        taskList.add(new Task(makeBedURL, "Make Bed", 0,
                0, null));
        taskList.add(new Task(readBookURL, "Read Book", 0,
                0, null));
        taskList.add(new Task(takeShowerURL, "Take Shower", 0,
                0, null));
        taskList.add(new Task(tidyUpURL, "Tidy Up", 0,
                0, null));
        taskList.add(new Task(turnLightsOnOffURL, "Turn Lights On/Off", 0,
                0, null));
        taskList.add(new Task(washHandsURL, "Wash Hands", 0,
                0, null));
        taskList.add(new Task(washFaceURL, "Wash Face", 0,
                0, null));
        taskList.add(new Task(useToiletURL, "Use Toilet", 0,
                0, null));
    }

    /**
     * When button is clicked, page goes to AddTaskActivity
     * @param v button onclick
     */
    public void goToAddTaskActivity(View v)
    {
        Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);
        goToAddTaskActivity.putExtra("user", user);
        startActivity(goToAddTaskActivity);
    }

    /**
     * When button is clicked, page goes back to TasksActivity
     * @param v button onclick
     */
    public void backButton(View v)
    {
        Intent goBackIntent = new Intent(this, TasksActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }
}