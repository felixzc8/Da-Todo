package com.example.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.da_todo.R;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.User.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class SignUpActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText nameInput, emailInput, passwordInput, pinInput;
    User user;
    Pet userPet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        nameInput = findViewById(R.id.nameInputEditText);
        emailInput = findViewById(R.id.emailInputEditText);
        passwordInput = findViewById(R.id.passwordInputEditText);
        pinInput = findViewById(R.id.pinInputEditText);

    }

    public void signUp(View v)
    {

        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String pin = pinInput.getText().toString();
        System.out.println(String.format("Sign Up - Email: %s, Password: %s", email, password));

        if (!name.equals("") && !email.equals("") && !password.equals(""))
        {
            try
            {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task ->
                        {
                            if (task.isSuccessful())
                            {
                                Log.d("SIGN UP", "createUserWithEmail:success");
                                FirebaseUser currUser = mAuth.getCurrentUser();
                                String uid = currUser.getUid();

                                user = new User(uid, name, email, pin);
                                firestore.collection("/users").document(uid).set(user);
                                goPetNamingActivity(user);
                            }
                            else
                            {
                                Log.w("SIGN UP", "createUserWithEmail:failure",
                                        task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void goSignInActivity(View v)
    {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void goPetNamingActivity(User user)
    {
        Intent intent = new Intent(this, PetNamingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}