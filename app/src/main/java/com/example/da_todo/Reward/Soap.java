package com.example.da_todo.Reward;

public class Soap
{
    String name;
    Integer price;
    Integer amount;

    public Soap()
    {
        this.name = "soap";
        this.price = 100;
        this.amount = 0;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
