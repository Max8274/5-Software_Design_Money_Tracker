package tickets;

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
        userPriceMap = involvedUserPriceMap;
    }
}
