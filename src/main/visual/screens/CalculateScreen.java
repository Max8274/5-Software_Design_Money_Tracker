package visual.screens;

import database.RegistrationDB;
import tickets.Ticket;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class CalculateScreen extends JFrame implements PropertyChangeListener
{
    private JButton returnButton;
    private ArrayList<User> userList;
    private ArrayList<Ticket> ticketList;

    public CalculateScreen()
    {
        super("MoneyTracker");
        initialise();
    }

    private void initialise()
    {
        this.userList = new ArrayList<>();
        this.ticketList = new ArrayList<>();
        this.setSize(900,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Global bill");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Global bill",Font.BOLD,50));
        this.add(titleLabel);

        RegistrationDB.getTicketDatabase().forEach(ticketList::add);
        // clear every userHashmap and update it
        for (Map.Entry<UUID, ArrayList<User>> entry : RegistrationDB.getUserDatabase().getDbHashMap().entrySet())
        {
            entry.getValue().get(0).clearUserHashMap();
        }

        for (Ticket ticket : ticketList)
        {
            for (UUID involvedUser : ticket.getUserPriceMap().keySet())
            {
                User user = RegistrationDB.getUserDatabase().getValueDBHashmap(involvedUser).get(0);
                UUID paidUser = ticket.getPaidUser();
                double paidPrice = ticket.getUserPriceMap().get(involvedUser);
                user.addInUserHashmap(paidUser,paidPrice);
                //RegistrationDB.getUserDatabase().getValueDBHashmap(involvedUser).get(0).addInUserHashmap(ticket.getPaidUser(), ticket.getUserPriceMap().get(involvedUser));
            }
        }

        AtomicReference<JLabel> label = new AtomicReference<>();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        RegistrationDB.getUserDatabase().forEach(userList::add);
        userList.forEach(user ->
        {
            if (!user.getUserHashmap().isEmpty())
            {
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy += 1;
                label.set(new JLabel(user.toPay()));
                this.add(label.get(), gridBagConstraints);
            }
        });

        this.returnButton = new JButton("Return");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        this.add(returnButton,gridBagConstraints);

        addReturnButtonActionListener();

        RegistrationDB.getUserDatabase().addPCL(this);
        RegistrationDB.getTicketDatabase().addPCL(this);
        this.setVisible(true);
    }

    private void addReturnButtonActionListener()
    {
        this.returnButton.addActionListener(listener ->
            this.dispose());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        userList.clear();
        ticketList.clear();
        RegistrationDB.getUserDatabase().forEach(userList::add);
        RegistrationDB.getTicketDatabase().forEach(ticketList::add);
        SwingUtilities.updateComponentTreeUI(this);
    }
}
