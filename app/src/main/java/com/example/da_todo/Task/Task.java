package com.example.da_todo.Task;

public class Task {
    private String taskName;
    private String taskDescription;
    private int timeRequired;
    private int pointsRewarded;

    public Task()
    {

    }

    public Task(String taskName, String taskDescription, int timeRequired, int pointsRewarded) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.timeRequired = timeRequired;
        this.pointsRewarded = pointsRewarded;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getTimeRequired() {
        return timeRequired;
    }

    public void setTimeRequired(int timeRequired) {
        this.timeRequired = timeRequired;
    }

    public int getPointsRewarded() {
        return pointsRewarded;
    }

    public void setPointsRewarded(int pointsRewarded) {
        this.pointsRewarded = pointsRewarded;
    }

    @Override
    public String toString() {
        return "Task:" +
                "\nTask Name = " + taskName +
                "\nTask Description = " + taskDescription +
                "\nTime Required = " + timeRequired +
                "\nPoints Rewarded = " + pointsRewarded;
    }
}
