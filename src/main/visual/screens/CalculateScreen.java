package visual.screens;

import database.RegistrationDB;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicReference;

public class CalculateScreen extends JFrame implements PropertyChangeListener
{
    public CalculateScreen()
    {
        super("MoneyTracker");
        initialise();
    }

    private void initialise()
    {
        this.setSize(900,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setResizable(false);

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Global bill");
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setFont(new Font("Global bill",Font.BOLD,50));
        this.add(titleLabel);

        AtomicReference<JLabel> label = new AtomicReference<>();
        gridBagConstraints.gridy = 0;

        RegistrationDB.getUserDatabase().forEach(user ->
        {
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy += 1;
            label.set(new JLabel(user.toPay()));
            this.add(label.get(),gridBagConstraints);
        });

        gridBagConstraints.gridy = 0;

        RegistrationDB.getUserDatabase().addPCL(this);
        RegistrationDB.getTicketDatabase().addPCL(this);
        this.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
