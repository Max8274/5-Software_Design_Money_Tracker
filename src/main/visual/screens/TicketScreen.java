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
    private JComboBox comboBox;
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

    private void initialise()
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

        JLabel label = new JLabel("Type of ticket:");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        this.add(label,gridBagConstraints);

        // source: https://www.javatpoint.com/java-jcombobox
        String[] ticketTypes = {"Airplane", "Restaurant", "Taxi", "Concert", "Club", "Musical", "Café"};
        this.comboBox = new JComboBox(ticketTypes);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        this.add(comboBox,gridBagConstraints);

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

    private void addEvenlySplitButtonActionListener()
    {
        this.evenlySplit.addActionListener(listener ->
        {
            if (userJList.getSelectedValue() != null
                    && (Double) jSpinner.getValue() != 0)
            {
                if (notEnvenlySplitTicketPanel != null)
                {
                    remove(notEnvenlySplitTicketPanel);
                }
                evenlySplitTicketPanel = new EvenlySplitTicketPanel(this.userJList.getSelectedValue());
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 9;
                this.add(evenlySplitTicketPanel,gridBagConstraints);
                isEvenlySplitTicket = true;
                isNotEvenlySplitTicket = false;
                SwingUtilities.updateComponentTreeUI(this);
            }
            else
            {
                //source: http://www.java2s.com/Code/Java/Swing-JFC/DisplaywarningmessagedialogwithJOptionPaneWARNINGMESSAGE.htm
                JOptionPane.showMessageDialog(null, "First fill in the ticket name, select the user who paid and fill in the price the user has paid before you can continue!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void addNotEvenlySplitButtonActionListener()
    {
        this.notEvenlySplit.addActionListener(listener ->
        {
            if (userJList.getSelectedValue() != null
                && (Double) jSpinner.getValue() != 0)
            {
                if (evenlySplitTicketPanel != null)
                {
                    remove(evenlySplitTicketPanel);
                }
                notEnvenlySplitTicketPanel = new NotEnvenlySplitTicketPanel(this.userJList.getSelectedValue());
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = 9;
                this.add(notEnvenlySplitTicketPanel,gridBagConstraints);
                isEvenlySplitTicket = false;
                isNotEvenlySplitTicket = true;
                SwingUtilities.updateComponentTreeUI(this);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "First fill in the ticket name, select the user who paid and fill in the price the user has paid before you can continue!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void addConfirmButtonActionListener()
    {
        this.confirm.addActionListener(listener ->
        {
            if (userJList.getSelectedValue() != null)
            {
                if ((Double) jSpinner.getValue() != 0)
                {
                    if (isEvenlySplitTicket)
                    {
                        if (!evenlySplitTicketPanel.getInvolvedUsersList().isEmpty())
                        {
                            Ticket evenlySplitTicket = controller.addEvenlySplitTicket(comboBox.getSelectedItem().toString(), userJList.getSelectedValue().getID(), (Double) jSpinner.getValue(), evenlySplitTicketPanel.getInvolvedUsersList());
                            this.dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Add involved people!", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    else if (isNotEvenlySplitTicket)
                    {
                        if (!notEnvenlySplitTicketPanel.getInvolvedUserPriceMap().isEmpty())
                        {
                            Ticket notEvenlySplitTicket = controller.addNotEvenlySplitTicket(comboBox.getSelectedItem().toString(), userJList.getSelectedValue().getID(), (Double) jSpinner.getValue(), notEnvenlySplitTicketPanel.getInvolvedUserPriceMap());
                            this.dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Add involved people!", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Fill in the price the user has paid!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Select an user who paid!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
