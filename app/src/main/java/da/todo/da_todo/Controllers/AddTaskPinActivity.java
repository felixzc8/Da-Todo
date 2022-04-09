package da.todo.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da_todo.R;
import da.todo.da_todo.User.User;

/**
 * When users try to enter the AddTask pages, they have to input a parent pin. If correct,
 * user can enter the restricted pages and can start adding tasks. This prevents kids from
 * accessing the restricted pages without permission
 *
 * @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */
public class AddTaskPinActivity extends AppCompatActivity
{
    private EditText pinInput;
    private int pinInt;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_pin);
        user = (User) getIntent().getSerializableExtra("user");
        pinInput = findViewById(R.id.pinNumber_EditText_AddTaskPinActivity);
    }

    /**
     * If inputted pin is correct, page goes to AllTaskActivity. If not, user can't continue
     * @param view button onclick
     */
    public void goToAllTaskActivity(View view)
    {
        try
        {
            pinInt = Integer.parseInt(pinInput.getText().toString());
            if (pinInt == Integer.valueOf(user.getPin()))
            {
                Toast.makeText(getApplicationContext(), "Correct Pin",
                        Toast.LENGTH_SHORT).show();
                Intent goToAllTaskActivity = new Intent(this, AllTaskActivity.class);
                goToAllTaskActivity.putExtra("user", user);
                startActivity(goToAllTaskActivity);
            } else
            {
                Toast.makeText(getApplicationContext(), "Wrong Pin, Please Retry",
                        Toast.LENGTH_LONG).show();
                pinInput.setText(null);
            }
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    /**
     * User can go back to TasksActivity page
     * @param v onclick
     */
    public void backButton(View v)
    {
        Intent goBackIntent = new Intent(this, TasksActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }
}