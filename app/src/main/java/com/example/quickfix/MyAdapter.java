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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Branch> list;
    boolean itemdeleted = false;

    public boolean isItemdeleted() {
        return itemdeleted;
    }

    public void setItemdeleted(boolean itemdeleted) {
        this.itemdeleted = itemdeleted;
    }

    public MyAdapter(Context context, ArrayList<Branch> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.branchitemadmin,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Branch branch = list.get(position);
        holder.address.setText(branch.getAddress());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = branch.getBranchkey();
                db.removeValue();
                setItemdeleted(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView address;
        ImageView delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.addressbranch);
            delete = itemView.findViewById(R.id.deletebranch);
        }
    }
}
