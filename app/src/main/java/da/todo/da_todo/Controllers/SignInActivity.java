package da.todo.da_todo.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.da_todo.R;
import da.todo.da_todo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class allows users to sign in to the app with their email and password
 *
 * @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */

public class SignInActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        emailInput = findViewById(R.id.emailInputEditTextSI);
        passwordInput = findViewById(R.id.passwordInputEditTextSI);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            String email = currentUser.getEmail();
            System.out.println(String.format("Current User - email: %s", email));
            goTaskActivity();
        }
    }

    /**
     * Signs user into firebase with their email and password
     *
     * @param v button click
     */
    public void signIn(View v)
    {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        System.out.println(String.format("Log In - Email: %s, Password: %s", email, password));

        if (!email.equals("") && !password.equals(""))
        {
            try
            {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task ->
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SIGN IN", "signInWithEmail:success");
                                goTaskActivity();
                            } else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w("SIGN IN", "signInWithEmail:failure",
                                        task.getException());
                                Toast.makeText(SignInActivity.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } else
        {
            Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Goes to sign up
     *
     * @param view button click
     */
    public void goToSignUpActivity(View view)
    {
        Intent goToSignUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(goToSignUpActivity);
    }

    /**
     * Goes to the tasks main page
     */
    public void goTaskActivity()
    {
        Intent goToTasksActivity = new Intent(this, TasksActivity.class);
        startActivity(goToTasksActivity);
    }
}