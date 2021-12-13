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

    public NotEnvenlySplitTicketPanel()
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

        JLabel label = new JLabel("Add involved users (not who paid) + price to pay");
        this.add(label);

        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
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

        addUsers = new JButton("+");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        this.add(addUsers,gridBagConstraints);

        addUsersButtonActionListener();
    }

    public HashMap<UUID, Double> getInvolvedUserPriceMap()
    {
        return involvedUserPriceMap;
    }

    public void addUsersButtonActionListener()
    {
        this.addUsers.addActionListener(listener ->
        {
            if (!involvedUserPriceMap.containsKey(userJList.getSelectedValue().getID()))
            {
                involvedUserPriceMap.put(userJList.getSelectedValue().getID(), (Double) jSpinner.getValue());
            }
        });
    }
}
