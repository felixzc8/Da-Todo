package com.example.da_todo.Task;

import android.widget.ImageView;

import java.io.Serializable;

public class Task implements Serializable
{
    private ImageView taskImage;
    private String taskName;
    private String taskDescription;
    private int timeRequired;
    private int pointsRewarded;

    public Task()
    {

    }

    public Task(ImageView taskImage, String taskName, String taskDescription, int timeRequired, int pointsRewarded)
    {
        this.taskImage = taskImage;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.timeRequired = timeRequired;
        this.pointsRewarded = pointsRewarded;
    }

    public ImageView getTaskImage()
    {
        return taskImage;
    }

    public void setTaskImage(ImageView taskImage)
    {
        this.taskImage = taskImage;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public int getTimeRequired()
    {
        return timeRequired;
    }

    public void setTimeRequired(int timeRequired)
    {
        this.timeRequired = timeRequired;
    }

    public int getPointsRewarded()
    {
        return pointsRewarded;
    }

    public void setPointsRewarded(int pointsRewarded)
    {
        this.pointsRewarded = pointsRewarded;
    }

    @Override
    public String toString()
    {
        return "Task:" +
                "\nTask Image = " + taskImage +
                "\nTask Name = " + taskName +
                "\nTask Description = " + taskDescription +
                "\nTime Required = " + timeRequired +
                "\nPoints Rewarded = " + pointsRewarded;
    }
}
