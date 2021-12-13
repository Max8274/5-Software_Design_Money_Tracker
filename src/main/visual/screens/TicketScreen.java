package visual.screens;

import controller.Controller;
import database.RegistrationDB;
import tickets.Ticket;
import user.User;
import visual.panels.EvenlySplitTicketPanel;
import visual.panels.NotEnvenlySplitTicketPanel;

import javax.swing.*;
import java.awt.*;

public class TicketScreen extends JFrame
{
    private Controller controller;
    private JTextField jTextField;
    private DefaultListModel<User> defaultListModel;
    private JList<User> userJList;
    private JScrollPane userScrollPane;
    private JSpinner jSpinner;
    private JRadioButton evenlySplit;
    private JRadioButton notEvenlySplit;
    private JButton confirm;
    private GridBagConstraints gridBagConstraints = new GridBagConstraints();
    private EvenlySplitTicketPanel evenlySplitTicketPanel;
    private NotEnvenlySplitTicketPanel notEnvenlySplitTicketPanel;
    private Boolean isEvenlySplitTicket;
    private Boolean isNotEvenlySplitTicket;

    public TicketScreen(Controller controller)
    {
        super("MoneyTracker");
        this.controller = controller;
        initialise();
    }

    public void initialise()
    {
        this.defaultListModel = new DefaultListModel<>();
        // source: https://stackoverflow.com/questions/13509107/how-to-allow-only-positive-value-in-a-jspinner
        // and https://www.codegrepper.com/code-examples/java/jspinner+only+positive+values
        // and https://stackoverflow.com/questions/12952024/how-to-implement-infinity-in-java
        this.jSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Double.POSITIVE_INFINITY, 1));
        this.confirm = new JButton("Confirm");
        this.setSize(700,650);
        this.setResizable(false);
        // source: http://www.nullskull.com/q/10143392/what-is-the-difference-between-form-hide-close-dispose-show-showdialog.aspx
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        JLabel label = new JLabel("Ticket name:");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.add(label,gridBagConstraints);

        this.jTextField = new JTextField(10);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        this.add(jTextField,gridBagConstraints);

        label = new JLabel("Who has paid and which price?");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        this.add(label,gridBagConstraints);

        RegistrationDB.getUserDatabase().forEach(defaultListModel::addElement);
        this.userJList = new JList<>(defaultListModel);
        this.userScrollPane = new JScrollPane(userJList);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,userScrollPane,jSpinner);
        jSplitPane.setDividerSize(0); // non-resizable
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        this.add(jSplitPane,gridBagConstraints);

        this.evenlySplit = new JRadioButton("Evenly splitted ticket");
        this.notEvenlySplit = new JRadioButton("Not evenly splitted ticket");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(evenlySplit);
        buttonGroup.add(notEvenlySplit);

        label = new JLabel("Kind of ticket?");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        this.add(label,gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        this.add(evenlySplit,gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        this.add(notEvenlySplit,gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        this.add(confirm,gridBagConstraints);

        addEvenlySplitButtonActionListener();
        addNotEvenlySplitButtonActionListener();
        addConfirmButtonActionListener();

        this.setVisible(true);
    }

    public void addEvenlySplitButtonActionListener()
    {
        this.evenlySplit.addActionListener(listener ->
        {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 9;
            evenlySplitTicketPanel = new EvenlySplitTicketPanel();
            this.add(evenlySplitTicketPanel,gridBagConstraints);
            isEvenlySplitTicket = true;
            isNotEvenlySplitTicket = false;
            SwingUtilities.updateComponentTreeUI(this);
        });
    }

    public void addNotEvenlySplitButtonActionListener()
    {
        this.notEvenlySplit.addActionListener(listener ->
        {
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 9;
            notEnvenlySplitTicketPanel = new NotEnvenlySplitTicketPanel();
            this.add(notEnvenlySplitTicketPanel,gridBagConstraints);
            isEvenlySplitTicket = false;
            isNotEvenlySplitTicket = true;
            SwingUtilities.updateComponentTreeUI(this);
        });
    }

    public void addConfirmButtonActionListener()
    {
        this.confirm.addActionListener(listener ->
        {
            if (isEvenlySplitTicket)
            {
                Ticket evenlySplitTicket = controller.addEvenlySplitTicket(jTextField.getText(), userJList.getSelectedValue().getID(), (Double) jSpinner.getValue(), evenlySplitTicketPanel.getInvolvedUsersList());
            }
            else if (isNotEvenlySplitTicket)
            {
                Ticket notEvenlySplitTicket = controller.addNotEvenlySplitTicket(jTextField.getText(), userJList.getSelectedValue().getID(), (Double) jSpinner.getValue(), notEnvenlySplitTicketPanel.getInvolvedUserPriceMap());
            }
            SwingUtilities.updateComponentTreeUI(this);
            this.dispose();
        });
    }
}
