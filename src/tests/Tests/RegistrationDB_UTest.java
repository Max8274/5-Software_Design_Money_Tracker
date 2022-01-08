package Tests;

import database.Database;
import database.RegistrationDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import tickets.Ticket;

import java.lang.reflect.Field;
import java.util.*;


@RunWith(PowerMockRunner.class)

@PrepareForTest(RegistrationDB.class)


public class RegistrationDB_UTest {

    @Before
    public void initialize() {
    }


    @Test
    @SuppressWarnings("unchecked")
    //test addInDBHashMap for User and Ticket
    public void t_addEntry() throws NoSuchFieldException, IllegalAccessException {
        Field field = RegistrationDB.class.getDeclaredField("db");
        field.setAccessible(true);

        Database user_registrationDB_underTest = RegistrationDB.getUserDatabase();
        HashMap<UUID,Double> mock_user_db = (HashMap<UUID,Double>) Mockito.mock(HashMap.class);
        field.set(user_registrationDB_underTest, mock_user_db);

        UUID mockUUID = Mockito.mock(UUID.class);
        Double mockPrice = Mockito.mock(Double.class);

        user_registrationDB_underTest.addInDBHashMap(mockUUID, mockPrice);
        Mockito.verify(mock_user_db, Mockito.times(1)).put(mockUUID, mockPrice);
        user_registrationDB_underTest.removeValueDBHashmap(mockUUID,mockPrice);


//        Database ticket_registrationDB_underTest = RegistrationDB.getUserDatabase();
//        HashMap<UUID,Ticket> mock_ticket_db = (HashMap<UUID,Ticket>) Mockito.mock(HashMap.class);
//        field.set(ticket_registrationDB_underTest, mock_ticket_db);
//
//        mockUUID = Mockito.mock(UUID.class);
//        Ticket mockTicket = Mockito.mock(Ticket.class);
//
//        ticket_registrationDB_underTest.addInDBHashMap(mockUUID, mockTicket);
//        Mockito.verify(mock_ticket_db, Mockito.times(1)).put(mockUUID, mockTicket);
//        ticket_registrationDB_underTest.removeValueDBHashmap(mockUUID,mockTicket);
    }

    @Test
    //test iterator function
    public void t_iterator() throws NoSuchFieldException, IllegalAccessException {
        Database registrationDB_underTest = RegistrationDB.getUserDatabase();

        ArrayList<Double> prices = new ArrayList<>(Arrays.asList(2.43, 4.56, 7.23, 4.75));
        Iterator<Double> comparingIterator = prices.iterator();

        for( Double price : prices){
            registrationDB_underTest.addInDBHashMap(UUID.randomUUID(), price);
        }


        Iterator<Double> returnedIterator = registrationDB_underTest.iterator();
        Assert.assertEquals("Testing iterator() - should return mockIterator", comparingIterator, returnedIterator);
    }
}


