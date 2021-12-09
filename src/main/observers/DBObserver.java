package observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DBObserver implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Database got updated");
    }
}
