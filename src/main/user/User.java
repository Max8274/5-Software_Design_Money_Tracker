package user;

import database.RegistrationDB;

import java.util.HashMap;
import java.util.UUID;

public class User
{
    private String name;
    private final UUID ID; // when there are two or more same names in a Ticket an ID is handy
    private HashMap<UUID, Double> userHashmap; // Double=price that this user needs to pay to the user with the UUID

    public User(String name)
    {
        this.name = name;
        this.ID = UUID.randomUUID();
        this.userHashmap = new HashMap<>();
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

    public HashMap<UUID, Double> getUserHashmap()
    {
        return userHashmap;
    }

    public void setUserHashmap(HashMap<UUID, Double> userHashmap)
    {
        this.userHashmap = userHashmap;
    }

    public void addInUserHashmap(UUID paidUser, Double price)
    {
        if (this.userHashmap.containsKey(paidUser))
        {
            Double priceT = this.userHashmap.get(paidUser);
            priceT += price;
            this.userHashmap.put(paidUser, priceT);
        }
        else
        {
            this.userHashmap.put(paidUser, price);
        }

        // if user A needs to pay user B and vice versa -> make calculation
        if (this.userHashmap.containsKey(paidUser) &&
                RegistrationDB.getUserDatabase().getValueDBHashmap(paidUser).getUserHashmap().containsKey(this.ID))
        {
            Double priceForPaidUser = this.userHashmap.get(paidUser);
            Double priceForThisUser = RegistrationDB.getUserDatabase().getValueDBHashmap(paidUser).getUserHashmap().get(this.ID);

            if (priceForPaidUser > priceForThisUser)
            {
                this.userHashmap.put(paidUser, priceForPaidUser - priceForThisUser);
                RegistrationDB.getUserDatabase().getValueDBHashmap(paidUser).getUserHashmap().remove(this.ID);
            }
            else if (priceForPaidUser.equals(priceForThisUser))
            {
                this.userHashmap.remove(paidUser);
                RegistrationDB.getUserDatabase().getValueDBHashmap(paidUser).getUserHashmap().remove(this.ID);
            }
            else
            {
                this.userHashmap.remove(paidUser);
                RegistrationDB.getUserDatabase().getValueDBHashmap(paidUser).getUserHashmap().put(this.ID, priceForThisUser - priceForPaidUser);
            }
        }
    }

    public String toPay()
    {
        StringBuilder text = new StringBuilder(this.getName() + " paylist:");
        for (UUID paidUser : this.userHashmap.keySet())
        {
            text.append("  To ").append(RegistrationDB.getUserDatabase().getValueDBHashmap(paidUser).getName()).append(": ").append(this.userHashmap.get(paidUser)).append(" euro");
        }
        return text.toString();
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
