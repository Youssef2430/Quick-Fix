package com.example.quickfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BranchAdd extends AppCompatActivity implements View.OnClickListener {

    private EditText addressEditText, phoneEditText, priceEditText;
    private CheckBox car,truck, moving;
    private Button add;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_add);

        addressEditText = (EditText) findViewById(R.id.addressadd);
        phoneEditText = (EditText) findViewById(R.id.numberadd);
        priceEditText = (EditText) findViewById(R.id.price);

        car = (CheckBox) findViewById(R.id.carcheckbox);
        truck = (CheckBox) findViewById(R.id.truckcheckbox);
        moving = (CheckBox) findViewById(R.id.movingcheckbox);

        add = (Button) findViewById(R.id.button);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                addbranch();
                startActivity(new Intent(this, AdminWelcomePage.class));
                break;
        }
    }

    private void addbranch() {
        String address = addressEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();
        ArrayList<String> services = new ArrayList<>();
        if(address.isEmpty()){
            addressEditText.setError("Address required!");
            addressEditText.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            phoneEditText.setError("Phone number required!");
            phoneEditText.requestFocus();
            return;
        }
        if(price.isEmpty()){
            priceEditText.setError("Price required!");
            priceEditText.requestFocus();
            return;
        }
        if(car.isChecked()){
            services.add("Car rental");
        }
        if(truck.isChecked()){
            services.add("Truck rental");
        }
        if(moving.isChecked()){
            services.add("Moving assistance");
        }
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Branch.class.getSimpleName());
        Branch branch = new Branch(address, phone, price, services);
        databaseReference.push().setValue(branch).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(BranchAdd.this, "Branch added!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(BranchAdd.this, "Branch could not be added!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}