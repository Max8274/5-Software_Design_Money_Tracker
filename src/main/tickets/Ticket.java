package tickets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Ticket
{
    private String typeOfTicket;
    private UUID paidUser;
    protected double paidPrice;
    protected HashMap<UUID, Double> userPriceMap;

    public Ticket(String typeOfTicket, UUID paidUser)
    {
        this.typeOfTicket = typeOfTicket;
        this.paidUser = paidUser;
        this.userPriceMap = new HashMap<>();
    }

    public String getTypeOfTicket()
    {
        return typeOfTicket;
    }

    public void setTypeOfTicket(String typeOfTicket)
    {
        this.typeOfTicket = typeOfTicket;
    }

    public UUID getPaidUser()
    {
        return paidUser;
    }

    public void setPaidUser(UUID paidUser)
    {
        this.paidUser = paidUser;
    }

    public double getPaidPrice()
    {
        return paidPrice;
    }

    public void setPaidPrice(double paidPrice)
    {
        this.paidPrice = paidPrice;
    }

    public HashMap<UUID, Double> getUserPriceMap()
    {
        return userPriceMap;
    }

    public void setUserPriceMap(HashMap<UUID, Double> userPriceMap)
    {
        this.userPriceMap = userPriceMap;
    }

    public void setUserPriceMap(ArrayList<UUID> involvedUsers) {}

    public void addInvolvedUser(UUID user)
    {
        boolean isUserInMap = false;
        for (UUID key : userPriceMap.keySet())
        {
            if (key == user) {
                isUserInMap = true;
                break;
            }
        }
        if (!isUserInMap)
        {
            this.userPriceMap.put(user, 0.0);
        }
    }

    public void setPrices(UUID paidUser, Double paidPrice) {}

    @Override
    public String toString()
    {
        return userPriceMap.toString();
    }
}
