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
    private final HashMap<UUID, ArrayList<T>> dbHashMap; //template method
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

    public HashMap<UUID, ArrayList<T>> getDbHashMap()
    {
        return dbHashMap;
    }

    public ArrayList<T> getValueDBHashmap(UUID ID)
    {
        return this.dbHashMap.get(ID);
    }

    public void addInDBHashMap(UUID ID, T template)
    {
        ArrayList<T> arrayList;
        if (this.dbHashMap.containsKey(ID))
        {
            arrayList = getValueDBHashmap(ID);
            arrayList.add(template);
            this.dbHashMap.put(ID, arrayList);
        }
        else
        {
            arrayList = new ArrayList<>();
            arrayList.add(template);
            this.dbHashMap.put(ID, arrayList);
        }
        support.firePropertyChange("Add user or ticket", null, template);
    }

    public void removeValueDBHashmap(UUID ID, T template)
    {
        // list of tickets has more then one value -> keep list and only delete the certain template value
        if (dbHashMap.containsKey(ID) && this.dbHashMap.get(ID).size() >= 2)
        {
            this.dbHashMap.get(ID).remove(template);
        }
        // only one value in list -> delete key-value pair
        else
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

    // implements Iterable<T> -> include methods
    @Override
    public Iterator<T> iterator()
    {
        ArrayList<T> list = new ArrayList<>();
        for (Map.Entry<UUID, ArrayList<T>> entry : dbHashMap.entrySet())
        {
            list.add(entry.getValue().get(0));
        }
        return list.iterator();
    }

    @Override
    public Spliterator<T> spliterator()
    {
        ArrayList<T> list = new ArrayList<>();
        for (Map.Entry<UUID, ArrayList<T>> entry : dbHashMap.entrySet())
        {
            list.add(entry.getValue().get(0));
        }
        return list.spliterator();
    }

    //source: https://zetcode.com/java/foreach/
    @Override
    public void forEach(Consumer<? super T> action)
    {
        this.dbHashMap.forEach((key, valueList) -> valueList.forEach(action));
    }
}
