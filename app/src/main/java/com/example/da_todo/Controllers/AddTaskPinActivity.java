package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da_todo.R;
import com.example.da_todo.User.User;

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

    public void backButton(View v)
    {
        Intent goBackIntent = new Intent(this, TasksActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }
}