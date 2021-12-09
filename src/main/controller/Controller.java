package controller;

import database.RegistrationDB;
import factories.FactoryProvider;
import tickets.Ticket;

import java.util.ArrayList;
import java.util.UUID;

public class Controller
{
    private static Controller controller;

    private Controller() {}

    public static Controller getController() {
        if (controller==null) {
            controller = new Controller();
        }
        return controller;
    }

    public Ticket addEvenlySplitTicket(String typeOfTicket, UUID paidUser, ArrayList<UUID> involvedUsers)
    {
        Ticket ticket = FactoryProvider.getEvenlySplitTicketFactory().addTicket(typeOfTicket, paidUser);
        ticket.setUserPriceMap(involvedUsers);
        RegistrationDB.getTicketDatabase().addInDBHashMap(paidUser, ticket);
        return ticket;
    }

    public Ticket addNotEvenlySplitTicket(String typeOfTicket, UUID paidUser, ArrayList<UUID> involvedUsers)
    {
        Ticket ticket = FactoryProvider.getNotEvenlySplitTicketFactory().addTicket(typeOfTicket, paidUser);
        ticket.setUserPriceMap(involvedUsers);
        RegistrationDB.getTicketDatabase().addInDBHashMap(paidUser, ticket);
        return ticket;
    }
}
