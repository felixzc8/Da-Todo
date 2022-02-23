package com.example.da_todo.Controllers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.da_todo.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class AddTaskActivity extends AppCompatActivity
{
//    private ImageView taskPhoto;
//    Uri imageUri;
//    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

//        taskPhoto = findViewById(R.id.uploadPhoto_imageView_AddTaskActivity);
//
//        firestore = FirebaseFirestore.getInstance();
//
//        taskPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                choosePicture();
//            }
//        });
    }

//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//                    }
//                }
//            });
//
//    private void choosePicture() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        someActivityResultLauncher.launch(intent);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(resultCode == RESULT_OK && data != null && data.getData() != null)
//        {
//            imageUri = data.getData();
//            taskPhoto.setImageURI(imageUri);
//            uploadPicture();
//        }
//    }
//
//    private void uploadPicture() {
//    }
}