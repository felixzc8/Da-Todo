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

public class tasksRecyclerAdapter extends RecyclerView.Adapter<tasksRecyclerAdapter.MyViewHolder>
{
    private ArrayList<Task> taskList;
    private RecyclerViewClickListener listener;

    public tasksRecyclerAdapter(ArrayList<Task> taskList, RecyclerViewClickListener listener)
    {
        this.taskList = taskList;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView imageView;
        private TextView taskName;
        private TextView taskTime;
        private TextView taskReward;

        public MyViewHolder(final View view)
        {
            super(view);
            imageView = view.findViewById(R.id.taskImage_ImageView_tasksItems);
            taskName = view.findViewById(R.id.taskName_TextView_AllTasksItems);
            taskTime = view.findViewById(R.id.taskTime_TextView_TasksItems);
            taskReward = view.findViewById(R.id.points_TextView_TasksItems);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public tasksRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull tasksRecyclerAdapter.MyViewHolder holder, int position)
    {
        String taskImage = taskList.get(position).getImage();
        String taskName = taskList.get(position).getName();
        int taskTime = taskList.get(position).getTimeRequired();
        int taskReward = taskList.get(position).getPointsRewarded();

        holder.taskName.setText(taskName);
        holder.taskTime.setText(String.valueOf(taskTime));
        holder.taskReward.setText(String.valueOf(taskReward));
    }

    @Override
    public int getItemCount()
    {
        return taskList.size();
    }

    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }

    public void openTimerActivity(View v)
    {

    }
}
