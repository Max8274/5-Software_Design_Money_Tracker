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

    public EvenlySplitTicketPanel()
    {
        this.defaultListModel = new DefaultListModel<>();
        involvedUsersList = new ArrayList<>();

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel label = new JLabel("Add involved users (not who paid!)");
        this.add(label);

        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
        userJList = new JList<>(defaultListModel);
        userJList.setSelectionBackground(Color.LIGHT_GRAY);
        userJList.setSelectionForeground(Color.BLACK);
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(userJList);
        scrollPane.setPreferredSize(new Dimension(150,80));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(scrollPane,gridBagConstraints);

        addUsers = new JButton("+");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(addUsers,gridBagConstraints);

        addUsersButtonActionListener();
    }

    public ArrayList<UUID> getInvolvedUsersList()
    {
        return involvedUsersList;
    }

    public void addUsersButtonActionListener()
    {
        this.addUsers.addActionListener(listener ->
        {
            if (!involvedUsersList.contains(userJList.getSelectedValue().getID()))
            {
                involvedUsersList.add(userJList.getSelectedValue().getID());
            }
        });
    }
}
