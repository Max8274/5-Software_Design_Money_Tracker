package visual.screens;

import database.RegistrationDB;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserScreen extends JFrame //implements ActionListener
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
        this.setSize(300,150);
        this.confirm = new JButton("Confirm");
        // source: http://www.nullskull.com/q/10143392/what-is-the-difference-between-form-hide-close-dispose-show-showdialog.aspx
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // source: http://www.java2s.com/Tutorial/Java/0240__Swing/ASimpleApplicationThatUsesGridBagConstraintsWEST.htm
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel label = new JLabel("Name:");
        this.add(label);

        this.jTextField = new JTextField(10);
        //jTextField.addActionListener(this);
        this.add(jTextField);

        addConfirmButtonActionListener();
        this.add(confirm);

        this.setVisible(true);
    }

    public void addConfirmButtonActionListener()
    {
        this.confirm.addActionListener(listener ->
        {
            User user = new User(jTextField.getText());
            RegistrationDB.getUserDatabase().addInDBHashMap(user.getID(),user);
            this.dispose();
        });
    }

    /*@Override
    public void actionPerformed(ActionEvent e)
    {

    }*/
}
