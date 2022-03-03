package com.example.da_todo.User;

import com.example.da_todo.Reward.Pet;
import com.example.da_todo.Task.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class User implements Serializable
{
    private String ID;
    private String name;
    private String email;
    private String pin;
    private Pet pet;
    ArrayList<Task> tasks;

    public User()
    {
    }

    public User(String ID, String name, String email, String pin)
    {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.pin = pin;
        this.pet = new Pet("", 0, 0, UUID.randomUUID().toString());
        this.tasks = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "User{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pin='" + pin + '\'' +
                ", pet=" + pet +
                ", tasks=" + tasks +
                '}';
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPin()
    {
        return pin;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
    }

    public Pet getPet()
    {
        return pet;
    }

    public void setPet(Pet pet)
    {
        this.pet = pet;
    }

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
    }
}
