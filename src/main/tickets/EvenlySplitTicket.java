package tickets;

import database.RegistrationDB;

import java.util.ArrayList;
import java.util.UUID;

public class EvenlySplitTicket extends Ticket
{
    public EvenlySplitTicket(String typeOfTicket, UUID paidUser)
    {
        super(typeOfTicket, paidUser);
    }

    @Override
    public void setPriceInvolvedUsers(ArrayList<UUID> involvedUsers)
    {
        userPriceMap.clear();
        for (UUID ID : involvedUsers)
        {
            RegistrationDB.getUserDatabase().getValueDBHashmap(ID).addInUserHashmap(this.paidUser, this.paidPrice/(involvedUsers.size()+1));
            this.userPriceMap.put(ID, paidPrice/(involvedUsers.size()+1));
        }
    }
}
