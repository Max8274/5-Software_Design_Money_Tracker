package factories;

import tickets.EvenlySplitTicket;
import tickets.Ticket;

import java.util.UUID;

public class EvenlySplitTicketFactory implements TicketFactory
{
    @Override
    public Ticket addTicket(String typeOfTicket, UUID paidUser)
    {
        return new EvenlySplitTicket(typeOfTicket, paidUser);
    }
}
