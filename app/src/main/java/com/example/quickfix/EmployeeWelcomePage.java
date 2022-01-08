package com.example.quickfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeWelcomePage extends AppCompatActivity implements View.OnClickListener{

    private Button logout, choose, applications, modify;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID, fullname, branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);

        logout = (Button) findViewById(R.id.logoutbutton);
        choose = (Button) findViewById(R.id.choosebranch);
        applications = (Button) findViewById(R.id.applications);
        modify = (Button) findViewById(R.id.modifybutton);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        final TextView welcome = (TextView) findViewById(R.id.welcomeemployee);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    fullname = userProfile.getFirstname() + " " + userProfile.getLastname();
                    branch = userProfile.getBranch();
                    if(!branch.equals("No branch")){
                        choose.setText("Change branch");
                        applications.setVisibility(View.VISIBLE);
                        modify.setVisibility(View.VISIBLE);
                    }

                    welcome.setText("Welcome "+fullname+"!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(this);
        applications.setOnClickListener(this);
        choose.setOnClickListener(this);
        modify.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.logoutbutton:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.choosebranch:
                startActivity(new Intent(this, Branchlistchoice.class));
                break;
        }

    }
}