package visual;

import controller.Controller;
import visual.panels.EvenlySplitTicketPanel;
import visual.panels.TicketPanel;
import visual.panels.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewFrame extends JFrame implements PropertyChangeListener
{
    Controller controller;
    EvenlySplitTicketPanel evenlySplitTicketPanel;
    Integer screenWidth;
    Integer screenHeight;

    public ViewFrame()
    {
        super("MoneyTracker");
    }

    public void initialize()
    {
        this.controller = Controller.getController();
        this.screenWidth = 750;
        this.screenHeight = 500;
        this.setSize(screenWidth, screenHeight);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new UserPanel(), new TicketPanel(controller));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        this.add(jSplitPane,gridBagConstraints);

        //evenlySplitTicketPanel = new EvenlySplitTicketPanel();
        //this.add(evenlySplitTicketPanel);

        JLabel titleLabel = new JLabel("Money Tracker");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Money Tracker",Font.BOLD,50));
        this.add(titleLabel);

        // source: https://stackoverflow.com/questions/1081486/setting-background-color-for-a-jframe
        this.getContentPane().setBackground(Color.GRAY);
        this.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
