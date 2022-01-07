package com.example.quickfix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminWelcomePage extends AppCompatActivity implements View.OnClickListener {

    private Button addbranch, branchaccess, employeeaccess;
    private ImageView ban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome_page);

        addbranch = (Button) findViewById(R.id.button5);
        addbranch.setOnClickListener(this);

        branchaccess = (Button) findViewById(R.id.branchesbutton);
        branchaccess.setOnClickListener(this);

        employeeaccess = (Button) findViewById(R.id.employeebutton);
        employeeaccess.setOnClickListener(this);

        ban = (ImageView) findViewById(R.id.imageView2);
        ban.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, BranchAdd.class));
                break;
            case R.id.branchesbutton:
                startActivity(new Intent(this, Branchlist.class));
                break;
            case R.id.employeebutton:
                startActivity(new Intent(this, Employeelist.class));
                break;
        }
    }
}