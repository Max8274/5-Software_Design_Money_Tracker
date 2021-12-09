package visual.panels;

import controller.Controller;
import database.RegistrationDB;
import tickets.Ticket;
import visual.screens.TicketScreen;

import javax.swing.*;
import java.awt.*;

public class TicketPanel extends JPanel
{
    private Controller controller;
    private JList<Ticket> ticketJList;
    private DefaultListModel<Ticket> defaultListModel;
    private JButton addTicket;
    private JButton removeTicket;

    public TicketPanel(Controller controller)
    {
        this.controller = controller;
        this.defaultListModel = new DefaultListModel<>();
        this.addTicket = new JButton("Add ticket");
        this.removeTicket = new JButton("Remove ticket");

        this.addTicket.setBackground(Color.GREEN);
        this.removeTicket.setBackground(Color.RED);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // source: https://www.javatpoint.com/java-jlist and http://www.java2s.com/Tutorial/Java/0240__Swing/ListSelectionModelModes.htm
        // add every element from the TicketDatabase to the defaultListModel
        // and make a ticketJList with it
        RegistrationDB.getTicketDatabase().forEach(defaultListModel::addElement);
        this.ticketJList = new JList<>(defaultListModel);
        this.ticketJList.setSelectionBackground(Color.GRAY);
        this.ticketJList.setSelectionForeground(Color.WHITE);
        this.ticketJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.ticketJList.setLayoutOrientation(JList.VERTICAL);
        //gridBagConstraints.gridx = 1;
        //gridBagConstraints.gridy = 1;
        this.add(ticketJList);

        // add 'Tickets' label
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        JLabel label = new JLabel("Tickets");
        this.add(label,gridBagConstraints);

        addTicketButtonActionListener();
        addRemoveTicketButtonActionListener();

        this.add(addTicket);
        this.add(removeTicket);
        this.setBackground(Color.WHITE);
    }

    public void addTicketButtonActionListener()
    {
        this.addTicket.addActionListener(listener ->
                new TicketScreen(controller));
    }

    public void addRemoveTicketButtonActionListener()
    {
        this.removeTicket.addActionListener(listener ->
        {
            RegistrationDB.getTicketDatabase().removeValueDBHashmap(ticketJList.getSelectedValue().getPaidUser());
        });
    }
}
