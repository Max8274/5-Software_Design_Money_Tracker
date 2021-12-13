package controller;

import tickets.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface Controller
{
    Ticket addEvenlySplitTicket(String typeOfTicket, UUID paidUser, Double paidPrice, ArrayList<UUID> involvedUsers);
    Ticket addNotEvenlySplitTicket(String typeOfTicket, UUID paidUser, Double paidPrice, HashMap<UUID,Double> involvedUserPriceMap);
}
