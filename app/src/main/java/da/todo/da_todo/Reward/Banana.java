package da.todo.da_todo.Reward;

import java.io.Serializable;

public class Banana implements Serializable
{
    String name;
    Integer price;
    Integer amount;

    public Banana()
    {
        this.name = "banana";
        this.price = 100;
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
