package visual.panels;

import database.RegistrationDB;
import user.User;
import visual.screens.UserScreen;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

    public void addUserButtonActionListener()
    {
        this.addUser.addActionListener(listener ->
                new UserScreen());
    }

    public void addRemoveUserButtonActionListener()
    {
        this.removeUser.addActionListener(listener ->
        {
            if (userJList.getSelectedValue() != null)
            {
                RegistrationDB.getUserDatabase().removeValueDBHashmap(userJList.getSelectedValue().getID(), userJList.getSelectedValue());
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        defaultListModel.clear();

        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
        // source: https://stackoverflow.com/questions/24959878/does-swingutilities-updatecomponenttreeui-method-set-the-current-lf-to-all-su
        SwingUtilities.updateComponentTreeUI(this);
    }
}
