package com.example.da_todo.Reward;

import java.io.Serializable;

public class Pet implements Serializable
{
    private String name;
    private int totalPoints;
    private int points;
    private String ID;

    public Pet()
    {

    }

    public Pet(String name, int totalPoints, int petPoints, String petID) {
        this.name = name;
        this.totalPoints = totalPoints;
        this.points = petPoints;
        this.ID = petID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Pet:" +
                "\nPet Name = " + name +
                "\nTotal Points = " + totalPoints +
                "\nPet Points = " + points +
                "\nPet ID = ";
    }
}
