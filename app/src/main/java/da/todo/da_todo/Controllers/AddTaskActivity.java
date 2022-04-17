package da.todo.da_todo.Controllers;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import da.todo.da_todo.R;
//import com.example.da_todo.R;
import da.todo.da_todo.Task.Task;
import da.todo.da_todo.User.User;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

/**
 * Allows users to add a customisable task to their list of tasks
 *
 * @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */
public class AddTaskActivity extends AppCompatActivity
{
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    User user;
    private EditText taskName, taskTime, taskPoints;
    private String userID, userEmail, userPin;
    String nameString, timeString, rewardString;
    private ImageView taskPhoto;
    public Uri imageUri;
    public String testURI;
    private String taskUUID;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        user = (User) getIntent().getSerializableExtra("user");

        Bundle intentInfo = getIntent().getExtras();
        if (intentInfo != null)
        {
            userID = intentInfo.getString("userID");
            userEmail = intentInfo.getString("parentEmail");
            userPin = intentInfo.getString("parentPin");
        }

        taskName = findViewById(R.id.taskTimeInputEditText);
        taskTime = findViewById(R.id.timeInputEditText);
        taskPoints = findViewById(R.id.pointsInputEditText);
        taskPhoto = findViewById(R.id.uploadPhoto_imageView_AddTaskActivity);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        taskPhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                choosePicture();
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->
            {
                if (result.getResultCode() == Activity.RESULT_OK)
                {
                    // There are no request codes
                    Intent data = result.getData();
                }
            });

    /**
     * When photo is clicked, page is changed to the photo page
     */
    private void choosePicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    /**
     * Collects the imageURI and sets the current photo's image URL to that URI
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            taskPhoto.setImageURI(imageUri);
        }
    }

    /**
     * Checks if user input is valid
     * @return boolean to see if the user's input is valid
     */
    public boolean formValid()
    {
        nameString = taskName.getText().toString();
        timeString = taskTime.getText().toString();
        rewardString = taskPoints.getText().toString();

        if (!nameString.equals("") && !timeString.equals("") && !rewardString.equals(""))
        {
            return true;
        } else
        {
            Toast.makeText(this, "missing info", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * When button is clicked, the input values and image URI is used to create a task and add
     * it to the user's list of tasks. If task is uploaded with image URL, it is updated. A progress
     * bar tracks how far along the task is to completing
     * @param view button onclick
     */
    public void addTask(View view)
    {
        if (formValid())
        {
            Integer timeInt = Integer.parseInt(timeString);
            Integer rewardInt = Integer.parseInt(rewardString);

            try
            {
                taskUUID = UUID.randomUUID().toString();

                final ProgressBar progressBar = new ProgressBar(this, null,
                        android.R.attr.progressBarStyleLarge);
                progressBar.setVisibility(View.VISIBLE);

                final String randomKey = UUID.randomUUID().toString();
                StorageReference riversRef = storageReference.child("task_images/" + randomKey);

                riversRef.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot ->
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Snackbar.make(findViewById(android.R.id.content),
                                    "Image Uploaded.", Snackbar.LENGTH_SHORT).show();

                            riversRef.getDownloadUrl().addOnSuccessListener(uri ->
                            {
                                testURI = String.valueOf(uri);
                                firestore.collection("tasks").
                                        document(taskUUID)
                                        .update("image", testURI);

                                for (Task t : user.getTasks())
                                {
                                    if (t.getTaskUUID().equals(taskUUID))
                                    {
                                        t.setImage(testURI);
                                    }
                                }

                                Task task = new
                                        Task(testURI, nameString, timeInt, rewardInt, taskUUID);
                                firestore.collection("tasks")
                                        .document(task.getTaskUUID()).set(task);
                                firestore.collection("users")
                                        .document(user.getID())
                                        .update("tasks", FieldValue.arrayUnion(task));
                                Toast.makeText(getApplicationContext(), "Added task",
                                        Toast.LENGTH_LONG).show();
                                clearPage();
                            });
                        })
                        .addOnFailureListener(e ->
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Failed to upload",
                                    Toast.LENGTH_SHORT).show();
                        })
                        .addOnProgressListener(snapshot ->
                        {
                            double progressPercent = (100 * snapshot.getBytesTransferred() /
                                    snapshot.getTotalByteCount());
                            int progress = (int) progressPercent;
                            progressBar.setProgress(progress, true);
                        });
            } catch (Exception err)
            {
                err.printStackTrace();
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            }
        } else
        {
            Toast.makeText(getApplicationContext(), "Fill in all fields correctly",
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Clears page when task is added
     */
    public void clearPage()
    {
        taskName.setText(null);
        taskTime.setText(null);
        taskPoints.setText(null);
        taskPhoto.setImageDrawable(null);
        back();
    }

    /**
     * When task is added, page goes back to AllTaskActivity
     */
    public void back()
    {
        Intent goBackIntent = new Intent(this, AllTaskActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }

    /**
     * When back button is clicked, page goes back to AllTaskActivity
     * @param v
     */
    public void backButton(View v)
    {
        Intent goBackIntent = new Intent(this, AllTaskActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }
}