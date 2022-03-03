package com.example.da_todo.Task;

import android.widget.ImageView;

import java.io.Serializable;

public class Task implements Serializable
{
    private ImageView image;
    private String name;
    private int timeRequired;
    private int pointsRewarded;

    public Task()
    {

    }

    public Task(ImageView taskImage, String taskName, int timeRequired, int pointsRewarded)
    {
        this.image = taskImage;
        this.name = taskName;
        this.timeRequired = timeRequired;
        this.pointsRewarded = pointsRewarded;
    }

    public ImageView getTaskImage()
    {
        return image;
    }

    public void setImage(ImageView taskImage)
    {
        this.image = taskImage;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
                "\nTask Image = " + image +
                "\nTask Name = " + name +
                "\nTime Required = " + timeRequired +
                "\nPoints Rewarded = " + pointsRewarded;
    }
}
