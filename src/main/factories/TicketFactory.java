package factories;

import tickets.Ticket;

import java.util.UUID;

public interface TicketFactory
{
    Ticket addTicket(String typeOfTicket, UUID paidUser);
}
