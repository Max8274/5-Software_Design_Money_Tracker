package tickets;

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
            this.userPriceMap.put(ID, paidPrice/(involvedUsers.size()+1));
        }
    }
}
