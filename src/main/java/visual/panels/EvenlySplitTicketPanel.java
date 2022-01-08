package visual.panels;

import database.RegistrationDB;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

public class EvenlySplitTicketPanel extends JPanel
{
    private ArrayList<UUID> involvedUsersList;
    private JList<User> userJList;
    private DefaultListModel<User> defaultListModel;
    private JButton addUsers;
    private JScrollPane scrollPane;

    public EvenlySplitTicketPanel(User paidUser)
    {
        this.defaultListModel = new DefaultListModel<>();
        involvedUsersList = new ArrayList<>();

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel label = new JLabel("Select and add involved user(s)");
        this.add(label);

        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
        defaultListModel.removeElement(paidUser);
        userJList = new JList<>(defaultListModel);
        userJList.setSelectionBackground(Color.LIGHT_GRAY);
        userJList.setSelectionForeground(Color.BLACK);
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(userJList);
        scrollPane.setPreferredSize(new Dimension(150,80));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(scrollPane,gridBagConstraints);

        addUsers = new JButton("Add user");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(addUsers,gridBagConstraints);

        addUsersButtonActionListener();
    }

    public ArrayList<UUID> getInvolvedUsersList()
    {
        return involvedUsersList;
    }

    private void addUsersButtonActionListener()
    {
        this.addUsers.addActionListener(listener ->
        {
            if (!involvedUsersList.isEmpty())
            {
                if (!involvedUsersList.contains(userJList.getSelectedValue().getID()) && userJList.getSelectedValue() != null)
                {
                    involvedUsersList.add(userJList.getSelectedValue().getID());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Involved user already added or no user is selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if (userJList.getSelectedValue() != null)
            {
                involvedUsersList.add(userJList.getSelectedValue().getID());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Select involved user(s) and add the involved user(s)!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
