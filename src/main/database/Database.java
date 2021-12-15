package database;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Database<T> implements Iterable<T>
{
    public Database()
    {

    }

    public abstract HashMap<UUID, ArrayList<T>> getDbHashMap();
    public abstract ArrayList<T> getValueDBHashmap(UUID ID);
    public abstract void addInDBHashMap(UUID ID, T template);
    public abstract void removeValueDBHashmap(UUID ID, T template);
    public abstract void addPCL(PropertyChangeListener pcl);
    public abstract void clearObservers();
}
