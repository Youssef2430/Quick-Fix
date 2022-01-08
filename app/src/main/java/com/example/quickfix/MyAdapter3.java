package com.example.quickfix;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder3>{

    Context context;
    ArrayList<Branch> list;
    private boolean isChanged = false;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    FirebaseUser user;
    DatabaseReference reference;
    String userID;


    public MyAdapter3(Context context, ArrayList<Branch> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.branchitem,parent,false);
        return  new MyViewHolder3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        Branch branch = list.get(position);
        holder.address.setText(branch.getAddress());
        holder.number.setText(branch.getPhone());
        ArrayList<String> srv = branch.getServices();
        String ser = srv.toString();
        holder.services.setText(ser);
        holder.choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users");
                userID = user.getUid();

                //DatabaseReference db = branch.getBranchkey();
                String add = branch.getAddress();
                reference.child(userID).child("branch").setValue(add);
                setChanged(true);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder3 extends RecyclerView.ViewHolder{

        TextView address, services, number;
        ImageView choice;
        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.addressbranchid);
            services = itemView.findViewById(R.id.services);
            number = itemView.findViewById(R.id.number);
            choice = itemView.findViewById(R.id.choice);
        }
    }
}
