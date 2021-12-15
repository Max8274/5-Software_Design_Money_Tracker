package visual.panels;

import controller.Controller;
import database.RegistrationDB;
import tickets.Ticket;
import visual.screens.TicketScreen;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TicketPanel extends JPanel implements PropertyChangeListener
{
    private Controller controller;
    private JList<Ticket> ticketJList;
    private DefaultListModel<Ticket> defaultListModel;
    private JButton addTicket;
    private JButton removeTicket;
    private JScrollPane ticketScrollPane;

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
        // source: http://www.java2s.com/Tutorial/Java/0240__Swing/ASimpleApplicationThatUsesGridBagConstraintsWEST.htm
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // add 'Tickets' label
        JLabel label = new JLabel("Tickets");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(label,gridBagConstraints);

        addTicketButtonActionListener();
        addRemoveTicketButtonActionListener();

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(addTicket,gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        this.add(removeTicket,gridBagConstraints);

        // source: https://www.javatpoint.com/java-jlist and http://www.java2s.com/Tutorial/Java/0240__Swing/ListSelectionModelModes.htm
        // add every element from the TicketDatabase to the defaultListModel
        // and make a ticketJList with it
        RegistrationDB.getTicketDatabase().forEach(defaultListModel::addElement);
        this.ticketJList = new JList<>(defaultListModel);
        this.ticketJList.setSelectionBackground(Color.LIGHT_GRAY);
        this.ticketJList.setSelectionForeground(Color.BLACK);
        this.ticketJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.ticketScrollPane = new JScrollPane(ticketJList);
        this.ticketScrollPane.setPreferredSize(new Dimension(150,80));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(ticketScrollPane,gridBagConstraints);

        this.setBackground(Color.WHITE);
        RegistrationDB.getTicketDatabase().addPCL(this);
    }

    private void addTicketButtonActionListener()
    {
        this.addTicket.addActionListener(listener ->
                new TicketScreen(controller));
    }

    private void addRemoveTicketButtonActionListener()
    {
        this.removeTicket.addActionListener(listener ->
        {
            if (ticketJList.getSelectedValue() != null)
            {
                RegistrationDB.getTicketDatabase().removeValueDBHashmap(ticketJList.getSelectedValue().getPaidUser(), ticketJList.getSelectedValue());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Select ticket!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        defaultListModel.clear();
        RegistrationDB.getTicketDatabase().forEach(defaultListModel::addElement);
        SwingUtilities.updateComponentTreeUI(this);
    }
}
