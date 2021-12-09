package tickets;

import user.User;

import java.util.ArrayList;
import java.util.UUID;

public class EvenlySplitTicket extends Ticket
{
    public EvenlySplitTicket(String typeOfTicket, UUID paidUser)
    {
        super(typeOfTicket, paidUser);
    }

    /*@Override
    public void setPaidPrice(double paidPrice)
    {
        this.paidPrice = paidPrice;

    }*/

    @Override
    public void setUserPriceMap(ArrayList<UUID> involvedUsers)
    {
        for (UUID ID : involvedUsers)
        {
            this.userPriceMap.put(ID, this.paidPrice/involvedUsers.size());
        }
    }

}
