package com.example.da_todo.Controllers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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

import com.example.da_todo.R;
import com.example.da_todo.Task.Task;
import com.example.da_todo.User.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddTaskActivity extends AppCompatActivity
{
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    User user;

    private EditText taskName;
    private EditText taskTime;
    private EditText taskPoints;

    private String userID;
    private String userEmail;
    private String userPin;

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
        if(intentInfo != null){
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

        taskPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });
    }

//    public void addTask(View view){
//        String nameString = taskName.getText().toString();
//        int timeInt = Integer.parseInt(taskTime.getText().toString());
//        int rewardInt = Integer.parseInt(taskPoints.getText().toString());
//        taskUUID = UUID.randomUUID().toString();

//        Task task = new Task(null, nameString, timeInt, rewardInt, taskUUID);

//        uploadPicture();

//        user.addTask(task);
//        System.out.println("User ID" + user.getID());
//
////        firestore.collection("tasks").document(task.getTaskUUID()).set(task);
//        System.out.println("Checkkkk" + user.toString());
//        System.out.println(user.getID() + "Ttttt");



//        firestore.collection("users").document(user.getID()).update("tasks",
//                FieldValue.arrayUnion(task);

//        clearPage();
//    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                    }
                }
            });

    private void choosePicture()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            taskPhoto.setImageURI(imageUri);
        }
    }

    public void addTask(View view) {
        try{
            String nameString = taskName.getText().toString();
            int timeInt = Integer.parseInt(taskTime.getText().toString());
            int rewardInt = Integer.parseInt(taskPoints.getText().toString());

            taskUUID = UUID.randomUUID().toString();

            final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
            progressBar.setVisibility(View.VISIBLE);

            final String randomKey = UUID.randomUUID().toString();
            StorageReference riversRef = storageReference.child("task_images/" + randomKey);

            riversRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_SHORT).show();

                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    testURI = String.valueOf(uri);
                                    firestore.collection("tasks").document(taskUUID).update("image", testURI);

                                    for (Task t: user.getTasks())
                                    {
                                        if (t.getTaskUUID().equals(taskUUID))
                                        {
                                            t.setImage(testURI);
                                        }
                                    }

                                    Task task = new Task(testURI, nameString, timeInt, rewardInt, taskUUID);
                                    firestore.collection("tasks").document(task.getTaskUUID()).set(task);
                                    firestore.collection("users").document(user.getID()).update("tasks", FieldValue.arrayUnion(task));
                                    Toast.makeText(getApplicationContext(), "Added task", Toast.LENGTH_LONG).show();
                                    clearPage();
//                                firestore.collection("users").document(user.getID()).set(user);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progressPercent = (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            int progress = (int) progressPercent;
                            progressBar.setProgress(progress, true);
                        }
                    });
        } catch(Exception err){
            err.printStackTrace();
            Toast.makeText(getApplicationContext(), "Incorrect input. Only input numbers", Toast.LENGTH_LONG).show();
            taskTime.setText(null);
            taskPoints.setText(null);
        }
    }

    public void clearPage(){
        taskName.setText(null);
        taskTime.setText(null);
        taskPoints.setText(null);
        taskPhoto.setImageDrawable(null);
        back();
    }

    public void back(){
        Intent goBackIntent = new Intent(this, AllTaskActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }

    public void backButton(View v){
        Intent goBackIntent = new Intent(this, AllTaskActivity.class);
        goBackIntent.putExtra("user", user);
        startActivity(goBackIntent);
    }
}