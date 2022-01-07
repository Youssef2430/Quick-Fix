package com.example.quickfix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {


    Context context;
    ArrayList<User> list;
    boolean isDeleted = false;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public MyAdapter2(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.employeeitem,parent,false);
        return  new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {

        User user = list.get(position);
        holder.address.setText(user.getBranch());
        holder.fullname.setText(user.getFirstname()+" "+user.getLastname()+" ");
        holder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = user.getBranchkey();
                db.removeValue();
                setDeleted(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView fullname, address;
        ImageView deletebutton;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.employeefull);
            address = itemView.findViewById(R.id.employeebranch);
            deletebutton = itemView.findViewById(R.id.deleteemployee);
        }
    }
}
