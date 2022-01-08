package visual;

import controller.Controller;
import controller.RegistrationController;
import database.RegistrationDB;
import visual.panels.TicketPanel;
import visual.panels.UserPanel;
import visual.screens.CalculateScreen;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewFrame extends JFrame implements PropertyChangeListener
{
    private Controller controller;
    private Integer screenWidth;
    private Integer screenHeight;
    private JButton calculate;

    public ViewFrame()
    {
        super("MoneyTracker");
    }

    public void initialize()
    {
        this.controller = RegistrationController.getController();
        this.screenWidth = 750;
        this.screenHeight = 500;
        this.setSize(screenWidth, screenHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Money Tracker");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Money Tracker",Font.BOLD,50));
        this.add(titleLabel);

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new UserPanel(), new TicketPanel(controller));
        jSplitPane.setDividerSize(0); // non-resizable
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(jSplitPane,gridBagConstraints);

        this.calculate = new JButton("Calculate");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        this.add(calculate,gridBagConstraints);

        addCalculateButtonActionListener();

        // source: https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
        this.getContentPane().setBackground(Color.GRAY);
        RegistrationDB.getUserDatabase().addPCL(this);
        RegistrationDB.getTicketDatabase().addPCL(this);
        this.setVisible(true);
    }

    private void addCalculateButtonActionListener()
    {
        this.calculate.addActionListener(listener ->
                new CalculateScreen());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        // source: https://stackoverflow.com/questions/24959878/does-swingutilities-updatecomponenttreeui-method-set-the-current-lf-to-all-su
        SwingUtilities.updateComponentTreeUI(this);
    }
}
