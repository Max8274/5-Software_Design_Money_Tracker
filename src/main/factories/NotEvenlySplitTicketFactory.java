package factories;

import user.User;
import tickets.NotEvenlySplitTicket;
import tickets.Ticket;

import java.util.UUID;

public class NotEvenlySplitTicketFactory implements TicketFactory
{
    @Override
    public Ticket addTicket(String typeOfTicket, UUID paidUser)
    {
        return new NotEvenlySplitTicket(typeOfTicket, paidUser);
    }
}
