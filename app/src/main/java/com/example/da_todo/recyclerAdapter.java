package com.example.da_todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.da_todo.Task.Task;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {
    private ArrayList<Task> taskList;

    public recyclerAdapter(ArrayList<Task> taskList)
    {
        this.taskList = taskList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView taskName;
        private TextView taskTime;
        private TextView taskReward;

        public MyViewHolder(final View view)
        {
             super(view);
             imageView = view.findViewById(R.id.taskImage_ImageView_tasksItems);
             taskName = view.findViewById(R.id.taskName_TextView_TasksItems);
             taskTime = view.findViewById(R.id.taskTime_TextView_TasksItems);
             taskReward = view.findViewById(R.id.points_TextView_TasksItems);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        ImageView taskImage = taskList.get(position).getTaskImage();
        String taskName = taskList.get(position).getTaskName();
        String taskDescription = taskList.get(position).getTaskDescription();
        int taskTime = taskList.get(position).getTimeRequired();
        int taskReward = taskList.get(position).getPointsRewarded();

        holder.taskName.setText(taskName);
        holder.taskTime.setText(String.valueOf(taskTime));
        holder.taskReward.setText(String.valueOf(taskReward));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void openTimerActivity(View v)
    {

    }
}
