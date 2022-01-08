package com.example.quickfix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Branchlistchoice extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter3 myAdapter3;
    ArrayList<Branch> list;
    boolean changed = false;
    ImageView banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branchlistchoice);

        banner = (ImageView) findViewById(R.id.imageView7);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Branchlistchoice.this, EmployeeWelcomePage.class));
            }
        });

        recyclerView = findViewById(R.id.listbranch);
        databaseReference = FirebaseDatabase.getInstance().getReference("Branch");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter3 = new MyAdapter3(this,list);
        recyclerView.setAdapter(myAdapter3);
        Log.i("Info","I'm here");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(myAdapter3.isChanged()){
                    myAdapter3.setChanged(false);
                    changed = true;
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Branch branch  = dataSnapshot.getValue(Branch.class);
                    branch.setBranchkey(dataSnapshot.getRef());
                    list.add(branch);
                }
                myAdapter3.notifyDataSetChanged();
                if(changed){
                    changed = false;
                    startActivity(new Intent(Branchlistchoice.this, EmployeeWelcomePage.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}