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

public class Branchlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<Branch> list;
    boolean changed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branchlist);

        recyclerView = findViewById(R.id.branchlistadmin);
        databaseReference = FirebaseDatabase.getInstance().getReference("Branch");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (myAdapter.isItemdeleted()){
                    list = new ArrayList<>();
                    myAdapter.setItemdeleted(false);
                    changed = true;
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Branch branch  = dataSnapshot.getValue(Branch.class);
                    branch.setBranchkey(dataSnapshot.getRef());
                    list.add(branch);
                }
                myAdapter.notifyDataSetChanged();
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