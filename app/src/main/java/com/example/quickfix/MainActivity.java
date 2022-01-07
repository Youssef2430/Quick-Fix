package com.example.quickfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private Button signin;
    private ProgressBar progressbar;
    private EditText Editemail, Editpassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        signin = (Button) findViewById(R.id.signinbutton);
        signin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        Editemail = (EditText) findViewById(R.id.email);
        Editpassword = (EditText) findViewById(R.id.passwordsignin);
        progressbar = (ProgressBar) findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, register_activity.class));
                break;
            case R.id.signinbutton:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = Editemail.getText().toString().trim();
        String password = Editpassword.getText().toString();

        if(email.equals("admin") && password.equals("admin")){
            //redirect to the admin private activity
            startActivity(new Intent(this, AdminWelcomePage.class));
            progressbar.setVisibility(View.INVISIBLE);
            return;
        }

        if(email.isEmpty()){
            Editemail.setError("Email is required!");
            Editemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Editemail.setError("Please enter a valid email!");
            Editemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Editpassword.setError("Password is required!");
            Editpassword.requestFocus();
            return;
        }


        progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to different activities depending on if it's an employee or customer
                    progressbar.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}