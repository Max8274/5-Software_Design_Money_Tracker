package observers;

import tickets.EvenlySplitTicket;
import tickets.NotEvenlySplitTicket;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DBTicketObserver implements PropertyChangeListener
{
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("Add user or ticket"))
        {
            if (evt.getNewValue() instanceof EvenlySplitTicket)
            {
                System.out.println("Added evenly split ticket: " + evt.getNewValue().toString());
            }
            else if (evt.getNewValue() instanceof NotEvenlySplitTicket)
            {
                System.out.println("Added not evenly split ticket: " + evt.getNewValue().toString());
            }
        }
        else if (evt.getPropertyName().equals("Delete user or ticket"))
        {
            if (evt.getOldValue() instanceof EvenlySplitTicket)
            {
                System.out.println("Removed evenly split ticket: " + evt.getOldValue().toString());
            }
            else if (evt.getOldValue() instanceof NotEvenlySplitTicket)
            {
                System.out.println("Removed not evenly split ticket: " + evt.getOldValue().toString());
            }
        }
    }
}
