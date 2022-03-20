package com.example.da_todo.Reward;

import java.io.Serializable;

public class TeddyBear implements Serializable
{
    String name;
    Integer price;
    Integer amount;

    public TeddyBear()
    {
        this.name = "teddy bear";
        this.price = 50;
        this.amount = 0;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getPrice()
    {
        return price;
    }

    public void setPrice(Integer price)
    {
        this.price = price;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
}
