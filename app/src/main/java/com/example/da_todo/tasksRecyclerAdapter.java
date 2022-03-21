package com.example.da_todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.da_todo.Task.Task;

import java.util.ArrayList;

/**
 * RecyclerView adapter of the TasksActivity page, displaying the list of task objects the user has
 * previously selected and is in their todo list
 *
 * @author Felix Chen, Daniel Yang, Lucas Yan, Aidan Yu
 * @version 1.0
 */
public class tasksRecyclerAdapter extends RecyclerView.Adapter<tasksRecyclerAdapter.MyViewHolder>
{
    private ArrayList<Task> taskList;
    private RecyclerViewClickListener listener;

    /**
     * Receives the tasklist to be displayed from the previous AllTaskActivity page
     * @param taskList list of default task objects
     * @param listener onclick listener
     */
    public tasksRecyclerAdapter(ArrayList<Task> taskList, RecyclerViewClickListener listener)
    {
        this.taskList = taskList;
        this.listener = listener;
    }

    /**
     * Sets the XML items and onclick listener
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView taskName;
        private TextView taskTime;
        private TextView taskReward;
        private ImageView imageView;

        public MyViewHolder(final View view)
        {
            super(view);
            imageView = view.findViewById(R.id.taskImage_ImageView_AllTasksItems);
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

    /**
     * Creates the recycler view
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @NonNull
    @Override
    public tasksRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_items, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Sets the various characteristics of the task to the correct places to be displayed on the
     * recyclerView
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull tasksRecyclerAdapter.MyViewHolder holder, int position)
    {
        System.out.println("checkss");
        System.out.println("has tasks");
        String taskImage = taskList.get(position).getImage();
        String taskName = taskList.get(position).getName();
        int taskTime = taskList.get(position).getTimeRequired();
        int taskReward = taskList.get(position).getPointsRewarded();

        holder.taskName.setText(taskName);
        holder.taskTime.setText(String.valueOf(taskTime));
        holder.taskReward.setText(String.valueOf(taskReward));
        Glide.with(holder.imageView.getContext()).load(taskImage).centerCrop().into(holder.imageView);
    }

    /**
     * Gets the # of items in tasklist
     * @return int of taskList size
     */
    @Override
    public int getItemCount()
    {
        return taskList.size();
    }

    /**
     * Checks for onclick
     */
    public interface RecyclerViewClickListener
    {
        void onClick(View v, int position);
    }
}
