package com.example.da_todo.Reward;

import java.io.Serializable;

public class Pet implements Serializable
{
    private String name;
    private String ID;
    TeddyBear teddyBear;
    Banana banana;
    Soap soap;

    public Pet()
    {

    }

    public Pet(String name, String petID)
    {
        this.teddyBear = new TeddyBear();
        this.banana = new Banana();
        this.soap = new Soap();
        this.name = name;
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


    public TeddyBear getTeddyBear() {
        return teddyBear;
    }

    public void setTeddyBear(TeddyBear teddyBear) {
        this.teddyBear = teddyBear;
    }

    public Banana getBanana() {
        return banana;
    }

    public void setBanana(Banana banana) {
        this.banana = banana;
    }

    public Soap getSoap() {
        return soap;
    }

    public void setSoap(Soap soap) {
        this.soap = soap;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", ID='" + ID + '\'' +
                ", teddyBear=" + teddyBear +
                ", banana=" + banana +
                ", soap=" + soap +
                '}';
    }
}
