package com.example.da_todo.Reward;

public class Pet
{
    private String petName;
    private int totalPoints;
    private int petPoints;

    public Pet()
    {

    }

    public Pet(String petName, int totalPoints, int petPoints) {
        this.petName = petName;
        this.totalPoints = totalPoints;
        this.petPoints = petPoints;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getPetPoints() {
        return petPoints;
    }

    public void setPetPoints(int petPoints) {
        this.petPoints = petPoints;
    }

    @Override
    public String toString() {
        return "Pet:" +
                "\nPet Name = " + petName +
                "\nTotal Points = " + totalPoints +
                "\nPet Points = " + petPoints;
    }
}
