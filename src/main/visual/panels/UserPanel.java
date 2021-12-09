package visual.panels;

import database.RegistrationDB;
import user.User;
import visual.screens.UserScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class UserPanel extends JPanel implements PropertyChangeListener
{
    private ArrayList<User> userArrayList;
    private JList<User> userJList;
    private DefaultListModel<User> defaultListModel;
    private JButton addUser;
    private JButton removeUser;

    public UserPanel()
    {
        this.userArrayList = new ArrayList<>();
        this.defaultListModel = new DefaultListModel<>();
        this.addUser = new JButton("Add user");
        this.removeUser = new JButton("Remove user");

        this.addUser.setBackground(Color.GREEN);
        this.removeUser.setBackground(Color.RED);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        // add every user from the UserDatabase to the userArrayList
        RegistrationDB.getUserDatabase().forEach(userArrayList::add);
        // source: https://www.javatpoint.com/java-jlist and http://www.java2s.com/Tutorial/Java/0240__Swing/ListSelectionModelModes.htm
        // add every element from the userArrayList to the defaultListModel
        // and make a userJList with it
        this.userArrayList.forEach(defaultListModel::addElement);
        this.userJList = new JList<>(defaultListModel);
        this.userJList.setSelectionBackground(Color.green);
        this.userJList.setSelectionForeground(Color.BLACK);
        this.userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.userJList.setLayoutOrientation(JList.VERTICAL);
        //gridBagConstraints.gridx = 1;
        //gridBagConstraints.gridy = 1;
        this.add(userJList);

        // add 'Users' label
        JLabel label = new JLabel("Users");
        //gridBagConstraints.gridx = 1;
        //gridBagConstraints.gridy = 0;
        this.add(label);

        addUserButtonActionListener();
        addRemoveUserButtonActionListener();

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(addUser);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        this.add(removeUser);
        this.setBackground(Color.WHITE);
    }

    public void addUserButtonActionListener()
    {
        this.addUser.addActionListener(listener ->
                new UserScreen());
    }

    public void addRemoveUserButtonActionListener()
    {
        this.removeUser.addActionListener(listener ->
        {
            RegistrationDB.getUserDatabase().removeValueDBHashmap(userJList.getSelectedValue().getID());
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        userArrayList.clear();
        defaultListModel.clear();

        RegistrationDB.getUserDatabase().forEach(userArrayList::add);
        userArrayList.forEach(defaultListModel::addElement);
        userJList = new JList<>(defaultListModel);
        // source: https://stackoverflow.com/questions/24959878/does-swingutilities-updatecomponenttreeui-method-set-the-current-lf-to-all-su
        SwingUtilities.updateComponentTreeUI(this);
    }
}
