package com.example.quickfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register_activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String role;
    private ImageView banner;
    private Button registerbutton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private EditText Editfirstname, Editlastname, Editemail, Editpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Spinner for role
        Spinner spinner = findViewById(R.id.rolespinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        ///////////////////////////

        banner = (ImageView) findViewById(R.id.banner);
        banner.setOnClickListener(this);
        registerbutton = (Button) findViewById(R.id.registerbutton);
        registerbutton.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        Editfirstname = findViewById(R.id.firstname);
        Editlastname = findViewById(R.id.lastname);
        Editemail = findViewById(R.id.emailregister);
        Editpassword = findViewById(R.id.passwordsignin);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        role = text;
        if(!text.equals("Choose your role")){
            Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerbutton:
                Log.i("MyApp", "Got here");
                registerUser();

        }

    }

    private void registerUser() {

        String email = Editemail.getText().toString().trim();
        String firstname = Editfirstname.getText().toString().trim();
        String lastname = Editlastname.getText().toString().trim();
        String password = Editpassword.getText().toString();
        String r = role;
        String branch = "No branch";

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
        if(firstname.isEmpty()){
            Editfirstname.setError("Firstname is required!");
            Editfirstname.requestFocus();
            return;
        }
        if(lastname.isEmpty()){
            Editlastname.setError("Lastname is required!");
            Editlastname.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Editpassword.setError("Password is required!");
            Editpassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            Editpassword.setError("Minimum password length should be 6 character");
            Editpassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(firstname, lastname, email, r, branch);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register_activity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }else{
                                        Toast.makeText(register_activity.this, "Failed to register ! Try again.", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(register_activity.this, "Failed to register!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}