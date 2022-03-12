package com.example.da_todo;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da_todo.Controllers.AllTaskActivity;
import com.example.da_todo.Reward.Pet;
import com.example.da_todo.Task.Task;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class allTaskRecyclerAdapter extends RecyclerView.Adapter<allTaskRecyclerAdapter.MyViewHolder> {
    private ArrayList<Task> taskList;
    private RecyclerViewClickListener listener;

    public allTaskRecyclerAdapter(ArrayList<Task> taskList, RecyclerViewClickListener listener){
        this.taskList = taskList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameText;
        private ImageView taskImage;

        public MyViewHolder(final View view){
            super(view);
            nameText = view.findViewById(R.id.taskName_TextView_AllTasksItems);
            taskImage = view.findViewById(R.id.taskImage_ImageView_AllTasksItems);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public allTaskRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_tasks_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull allTaskRecyclerAdapter.MyViewHolder holder, int position) {
        String name = taskList.get(position).getName();
        String imageString = taskList.get(position).getImage();

        holder.nameText.setText(name);
        Glide.with(holder.taskImage.getContext()).load(imageString).centerCrop().into(holder.taskImage);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface RecyclerViewClickListener{
         void onClick(View view, int position);
    }
}
