package com.pubchat.pubchat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    Button signUpButton;
    EditText email;
    EditText password;

    FirebaseAuth auth;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);



        email = (EditText) findViewById(R.id.emailSignUp);
        password = (EditText) findViewById(R.id.passwordSignUp);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();

            }
        });

        auth = FirebaseAuth.getInstance();

    }



    public void attemptSignUp(){
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        if(isEmailValid(emailString) && isPasswordValid(passwordString)){
            createUser();
        }else
            Toast.makeText(this, "Your email or password inputs are invalid.", Toast.LENGTH_SHORT).show();
    }
    private boolean isEmailValid(String email){
        return (email.contains("@"));
    }
    private boolean isPasswordValid(String password){
        return (password.length()>6);
    }

    public void createUser(){
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        auth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("PubChat", "createUserWithEmail:Success!");
                    FirebaseUser user = auth.getCurrentUser();
                    Toast.makeText(SignUpActivity.this, "User successfully created!", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                // If sign in fails, display a message to the user.
                Log.w("PubChat", "signInWithEmail:failure", task.getException());
                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();

            }

        }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
