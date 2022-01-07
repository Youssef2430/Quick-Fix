package com.example.quickfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Employeelist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter2 myAdapter2;
    ArrayList<User> list;
    boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeelist);

        recyclerView = findViewById(R.id.employeerecycler);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter2 = new MyAdapter2(this,list);
        recyclerView.setAdapter(myAdapter2);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (myAdapter2.isDeleted()){
                    list = new ArrayList<>();
                    myAdapter2.setDeleted(false);
                    changed = true;
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user  = dataSnapshot.getValue(User.class);
                    if(user.getRole().equals("Employee")){
                        user.setBranchkey(dataSnapshot.getRef());
                        list.add(user);
                    }
                }
                myAdapter2.notifyDataSetChanged();
                if(changed){
                    changed = false;
                    refresh();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void refresh(){
        recreate();
    }
}