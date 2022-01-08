package visual.panels;

import database.RegistrationDB;
import tickets.Ticket;
import user.User;
import visual.screens.UserScreen;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.UUID;

public class UserPanel extends JPanel implements PropertyChangeListener
{
    private JList<User> userJList;
    private DefaultListModel<User> defaultListModel;
    private JButton addUser;
    private JButton removeUser;
    private JScrollPane userScrollPane;

    public UserPanel()
    {
        this.defaultListModel = new DefaultListModel<>();
        this.addUser = new JButton("Add user");
        this.removeUser = new JButton("Remove user");

        this.addUser.setBackground(Color.GREEN);
        this.removeUser.setBackground(Color.RED);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        // source: http://www.java2s.com/Tutorial/Java/0240__Swing/ASimpleApplicationThatUsesGridBagConstraintsWEST.htm
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // add 'Users' label
        JLabel label = new JLabel("Users");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(label,gridBagConstraints);

        addUserButtonActionListener();
        addRemoveUserButtonActionListener();

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(addUser,gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        this.add(removeUser,gridBagConstraints);

        // source: https://www.javatpoint.com/java-jlist and http://www.java2s.com/Tutorial/Java/0240__Swing/ListSelectionModelModes.htm
        // add every element from the UserDatabase to the defaultListModel
        // and make a userJList with it
        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
        this.userJList = new JList<>(defaultListModel);
        this.userJList.setSelectionBackground(Color.LIGHT_GRAY);
        this.userJList.setSelectionForeground(Color.BLACK);
        this.userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.userScrollPane = new JScrollPane(userJList);
        userScrollPane.setPreferredSize(new Dimension(150,80));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        this.add(userScrollPane,gridBagConstraints);

        this.setBackground(Color.WHITE);
        RegistrationDB.getUserDatabase().addPCL(this);
    }

    private void addUserButtonActionListener()
    {
        this.addUser.addActionListener(listener ->
                new UserScreen());
    }

    private void addRemoveUserButtonActionListener()
    {
        this.removeUser.addActionListener(listener ->
        {
            Boolean isUserInTicket = false;
            //go through ticket database and check if the selected user isn't connected with any ticket
            for (UUID ID : RegistrationDB.getTicketDatabase().getDbHashMap().keySet())
            {
                for (Ticket ticket : RegistrationDB.getTicketDatabase().getValueDBHashmap(ID))
                {
                    //is the selected user an user who paid or is involved in any ticket
                    if (userJList.getSelectedValue().getID() == ID
                            || ticket.getUserPriceMap().containsKey(userJList.getSelectedValue().getID()))
                    {
                        isUserInTicket = true;
                    }
                }
            }
            if (userJList.getSelectedValue() != null)
            {
                if (!isUserInTicket)
                {
                    RegistrationDB.getUserDatabase().removeValueDBHashmap(userJList.getSelectedValue().getID(), userJList.getSelectedValue());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "First delete ticket(s) where user is connected with!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Select user!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        defaultListModel.clear();
        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
        SwingUtilities.updateComponentTreeUI(this);
    }
}
