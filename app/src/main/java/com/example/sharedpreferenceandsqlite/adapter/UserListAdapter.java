package com.example.sharedpreferenceandsqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedpreferenceandsqlite.R;
import com.example.sharedpreferenceandsqlite.admin.OnUserActionListener;
import com.example.sharedpreferenceandsqlite.model.UserModel;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    ArrayList<UserModel> userList;
    private OnUserActionListener listener;
    public UserListAdapter(ArrayList<UserModel> userList, OnUserActionListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.password.setText(user.getPassword());

        holder.updateButton.setOnClickListener(v -> listener.onUpdate(user));
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void updateList(ArrayList<UserModel> updatedList) {
        this.userList = updatedList;
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,password;
        Button updateButton, deleteButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvNameRv);
            email = itemView.findViewById(R.id.tvEmailRv);
            password = itemView.findViewById(R.id.tvPasswordRv);

            updateButton = itemView.findViewById(R.id.btnUpdateUserRv);
            deleteButton = itemView.findViewById(R.id.btnDeleteUserRv);
        }
    }
}
