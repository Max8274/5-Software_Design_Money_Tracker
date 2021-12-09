package visual.screens;

import controller.Controller;

import javax.swing.*;

public class TicketScreen extends JFrame
{
    private Controller controller;

    public TicketScreen(Controller controller)
    {
        super("MoneyTracker");
        this.controller = controller;
        initialise();
    }

    public void initialise()
    {
        this.setSize(300,150);
        // source: http://www.nullskull.com/q/10143392/what-is-the-difference-between-form-hide-close-dispose-show-showdialog.aspx
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        this.setVisible(true);
    }
}
