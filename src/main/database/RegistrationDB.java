package database;

import user.User;
import tickets.Ticket;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.function.Consumer;

public class RegistrationDB<T> extends Database<T> implements Iterable<T>
{
    private static RegistrationDB<User> userDatabase = null; //singleton pattern
    private static RegistrationDB<Ticket> ticketDatabase = null; //singleton pattern
    private final HashMap<UUID, T> dbHashMap; //template method
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    private RegistrationDB()
    {
        this.dbHashMap = new HashMap<>();
    }

    public static Database<User> getUserDatabase()
    {
        if (userDatabase==null)
        {
            userDatabase = new RegistrationDB<>();
        }
        return userDatabase;
    }

    public static Database<Ticket> getTicketDatabase()
    {
        if (ticketDatabase==null)
        {
            ticketDatabase = new RegistrationDB<>();
        }
        return ticketDatabase;
    }

    public HashMap<UUID, T> getDbHashMap() {
        return dbHashMap;
    }

    public T getValueDBHashmap(UUID ID)
    {
        return this.dbHashMap.get(ID);
    }

    public void addInDBHashMap(UUID ID, T template)
    {
        this.dbHashMap.put(ID, template);
        support.firePropertyChange("Add user or ticket", null, template);
    }

    public void removeValueDBHashmap(UUID ID, T template)
    {
        if (dbHashMap.containsKey(ID))
        {
            this.dbHashMap.remove(ID);
        }
        support.firePropertyChange("Delete user or ticket", template, null);
    }

    public void addPCL(PropertyChangeListener pcl)
    {
        support.addPropertyChangeListener(pcl);
    }

    public void clearObservers()
    {
        support = new PropertyChangeSupport(this);
    }

    // implements Iterable<T> -> has two methods that has to be included
    @Override
    public Iterator<T> iterator()
    {
        return this.dbHashMap.values().iterator();
    }

    @Override
    public Spliterator<T> spliterator()
    {
        return this.dbHashMap.values().spliterator();
    }

    //source: https://zetcode.com/java/foreach/
    @Override
    public void forEach(Consumer<? super T> action)
    {
        this.dbHashMap.values().forEach(action);
    }
}
