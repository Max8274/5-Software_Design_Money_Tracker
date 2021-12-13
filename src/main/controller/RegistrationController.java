package controller;

import database.RegistrationDB;
import factories.FactoryProvider;
import tickets.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RegistrationController implements Controller
{
    private static RegistrationController controller = null; //singleton pattern

    private RegistrationController()
    {

    }

    public static Controller getController() {
        if (controller==null) {
            controller = new RegistrationController();
        }
        return controller;
    }

    @Override
    public Ticket addEvenlySplitTicket(String typeOfTicket, UUID paidUser, Double paidPrice, ArrayList<UUID> involvedUsers)
    {
        Ticket ticket = FactoryProvider.getEvenlySplitTicketFactory().addTicket(typeOfTicket, paidUser);
        ticket.setPaidPrice(paidPrice);
        ticket.setPriceInvolvedUsers(involvedUsers);
        RegistrationDB.getTicketDatabase().addInDBHashMap(paidUser, ticket);
        return ticket;
    }

    @Override
    public Ticket addNotEvenlySplitTicket(String typeOfTicket, UUID paidUser, Double paidPrice, HashMap<UUID,Double> involvedUserPriceMap)
    {
        Ticket ticket = FactoryProvider.getNotEvenlySplitTicketFactory().addTicket(typeOfTicket, paidUser);
        ticket.setPaidPrice(paidPrice);
        ticket.setPriceInvolvedUsers(involvedUserPriceMap);
        RegistrationDB.getTicketDatabase().addInDBHashMap(paidUser, ticket);
        return ticket;
    }
}
