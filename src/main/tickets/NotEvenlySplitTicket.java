package tickets;

import database.RegistrationDB;

import java.util.HashMap;
import java.util.UUID;

public class NotEvenlySplitTicket extends Ticket
{
    public NotEvenlySplitTicket(String typeOfTicket, UUID paidUser)
    {
        super(typeOfTicket, paidUser);
    }

    @Override
    public void setPriceInvolvedUsers(HashMap<UUID,Double> involvedUserPriceMap)
    {
        userPriceMap.clear();
        for (UUID ID : involvedUserPriceMap.keySet())
        {
            RegistrationDB.getUserDatabase().getValueDBHashmap(ID).addInUserHashmap(this.paidUser, involvedUserPriceMap.get(ID));
            this.userPriceMap.put(ID, involvedUserPriceMap.get(ID));
        }
    }
}
