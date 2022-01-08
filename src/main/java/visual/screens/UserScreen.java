package visual.screens;

import database.RegistrationDB;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class UserScreen extends JFrame
{
    private JButton confirm;
    private JTextField jTextField;

    public UserScreen()
    {
        super("Money Tracker");
        initialise();
    }

    private void initialise()
    {
        this.confirm = new JButton("Confirm");
        this.setSize(300,150);
        this.setResizable(false);
        // source: http://www.nullskull.com/q/10143392/what-is-the-difference-between-form-hide-close-dispose-show-showdialog.aspx
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // source: http://www.java2s.com/Tutorial/Java/0240__Swing/ASimpleApplicationThatUsesGridBagConstraintsWEST.htm
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        JLabel label = new JLabel("Name:");
        this.add(label);

        this.jTextField = new JTextField(10);
        this.add(jTextField);

        addConfirmButtonActionListener();
        this.add(confirm);

        this.setVisible(true);
    }

    private void addConfirmButtonActionListener()
    {
        this.confirm.addActionListener(listener ->
        {
            Boolean isSameName = false;
            if (!jTextField.getText().isBlank())
            {
                for (UUID userID : RegistrationDB.getUserDatabase().getDbHashMap().keySet())
                {
                    if (RegistrationDB.getUserDatabase().getValueDBHashmap(userID).get(0).getName().equals(jTextField.getText()))
                    {
                        isSameName = true;
                    }
                }

                if (!isSameName)
                {
                    User user = new User(jTextField.getText());
                    RegistrationDB.getUserDatabase().addInDBHashMap(user.getID(), user);
                    this.dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "This name already exists in the database. Add a last name to avoid confusion!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Fill in a complete name!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
