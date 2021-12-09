package user;

import java.util.UUID;

public class User
{
    private String name;
    private final UUID ID; // when there are two or more same names in a Ticket an ID is handy

    public User(String name)
    {
        this.name = name;
        this.ID = UUID.randomUUID();
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public UUID getID()
    {
        return ID;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
