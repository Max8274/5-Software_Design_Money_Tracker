package tickets;

import user.User;

import java.util.HashMap;
import java.util.UUID;

public class NotEvenlySplitTicket extends Ticket
{
    public NotEvenlySplitTicket(String typeOfTicket, UUID paidUser)
    {
        super(typeOfTicket, paidUser);
    }

}
