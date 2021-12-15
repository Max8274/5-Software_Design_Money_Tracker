package visual.panels;

import database.RegistrationDB;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class NotEnvenlySplitTicketPanel extends JPanel
{
    private HashMap<UUID,Double> involvedUserPriceMap;
    private JList<User> userJList;
    private DefaultListModel<User> defaultListModel;
    private JButton addUsers;
    private JScrollPane scrollPane;
    private JSpinner jSpinner;

    public NotEnvenlySplitTicketPanel(User paidUser)
    {
        this.defaultListModel = new DefaultListModel<>();
        involvedUserPriceMap = new HashMap<>();
        // source: https://stackoverflow.com/questions/13509107/how-to-allow-only-positive-value-in-a-jspinner
        // and https://www.codegrepper.com/code-examples/java/jspinner+only+positive+values
        // and https://stackoverflow.com/questions/12952024/how-to-implement-infinity-in-java
        this.jSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Double.POSITIVE_INFINITY, 1));

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel label = new JLabel("Select user + price and add them");
        this.add(label);

        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
        defaultListModel.removeElement(paidUser);
        userJList = new JList<>(defaultListModel);
        userJList.setSelectionBackground(Color.LIGHT_GRAY);
        userJList.setSelectionForeground(Color.BLACK);
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane(userJList);
        scrollPane.setPreferredSize(new Dimension(150,80));

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollPane,jSpinner);
        jSplitPane.setDividerSize(0); // non-resizable
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.add(jSplitPane,gridBagConstraints);

        addUsers = new JButton("Add user");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(addUsers,gridBagConstraints);

        addUsersButtonActionListener();
    }

    public HashMap<UUID, Double> getInvolvedUserPriceMap()
    {
        return involvedUserPriceMap;
    }

    private void addUsersButtonActionListener()
    {
        this.addUsers.addActionListener(listener ->
        {
            if (!involvedUserPriceMap.isEmpty())
            {
                if (!involvedUserPriceMap.containsKey(userJList.getSelectedValue().getID())
                        && userJList.getSelectedValue() != null && (Double) jSpinner.getValue() != 0)
                {
                    involvedUserPriceMap.put(userJList.getSelectedValue().getID(), (Double) jSpinner.getValue());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Involved user already added, no user is selected or the price to pay = 0!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if (userJList.getSelectedValue() != null && (Double) jSpinner.getValue() != 0)
            {
                involvedUserPriceMap.put(userJList.getSelectedValue().getID(), (Double) jSpinner.getValue());
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Select involved user(s), choose a price to pay bigger than 0 and add the involved user(s)!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
