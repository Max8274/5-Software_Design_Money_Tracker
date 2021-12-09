package visual.panels;

import user.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EvenlySplitTicketPanel extends JPanel implements ActionListener
{
    private ArrayList<Object> personArrayList = new ArrayList<>();
    private ArrayList<User> remainingPersons = new ArrayList<>();
    private ArrayList<JComboBox<Object>> comboBoxArray = new ArrayList<>();
    private int i=0;
    private int row=0;
    JButton addUserButton = new JButton("+");
    JButton removeUserButton = new JButton("-");

    public EvenlySplitTicketPanel()
    {

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }
}
