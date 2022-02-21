package com.example.da_todo.User;

import java.util.ArrayList;

public class User
{
    private String userID;
    private String userName;
    private String parentEmail;
    private String parentPassword;
    private String petID;
    ArrayList<String> taskIDs;

    public User()
    {

    }

    public User(String userID, String userName, String parentEmail, String parentPassword, String petID, ArrayList<String> taskIDs) {
        this.userID = userID;
        this.userName = userName;
        this.parentEmail = parentEmail;
        this.parentPassword = parentPassword;
        this.petID = petID;
        this.taskIDs = taskIDs;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getParentPassword() {
        return parentPassword;
    }

    public void setParentPassword(String parentPassword) {
        this.parentPassword = parentPassword;
    }

    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public ArrayList<String> getTaskIDs() {
        return taskIDs;
    }

    public void setTaskIDs(ArrayList<String> taskIDs) {
        this.taskIDs = taskIDs;
    }

    @Override
    public String toString() {
        return "User:" +
                "User ID = " + userID +
                "\nUser Name = " + userName +
                "\nParent Email = " + parentEmail +
                "\nParent Password = " + parentPassword +
                "\nPet ID = " + petID +
                "\nTask IDs = " + taskIDs;
    }
}
