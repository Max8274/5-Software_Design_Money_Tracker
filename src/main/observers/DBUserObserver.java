package observers;

import user.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DBUserObserver implements PropertyChangeListener
{
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (evt.getPropertyName().equals("Add user or ticket"))
        {
            if (evt.getNewValue() instanceof User)
            {
                System.out.println("Added user: " + evt.getNewValue().toString());
            }
        }
        else if (evt.getPropertyName().equals("Delete user or ticket"))
        {
            if (evt.getOldValue() instanceof User)
            {
                System.out.println("Removed user: " + evt.getOldValue().toString());
            }
        }
    }
}
