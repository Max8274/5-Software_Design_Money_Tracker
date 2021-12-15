package tickets;

import database.RegistrationDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Ticket
{
    protected String typeOfTicket;
    protected UUID paidUser;
    protected double paidPrice;
    protected HashMap<UUID, Double> userPriceMap; //UUID (=involved user) has to pay Double (=price) to paidUser

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

    public void setPriceInvolvedUsers(ArrayList<UUID> involvedUsers) {};

    public void setPriceInvolvedUsers(HashMap<UUID,Double> involvedUserPriceMap) {};

    @Override
    public String toString()
    {
        StringBuilder text = new StringBuilder(typeOfTicket + ": " + paidPrice + " euro paid by " + RegistrationDB.getUserDatabase().getValueDBHashmap(paidUser).get(0).getName()
                + ". Users who need to pay: ");
        for (UUID ID : this.userPriceMap.keySet())
        {
            text.append(RegistrationDB.getUserDatabase().getValueDBHashmap(ID).get(0).getName()).append(" -> ").append(this.userPriceMap.get(ID)).append(" euro  ");
        }
        return text.toString();
    }
}
