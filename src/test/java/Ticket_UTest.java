import database.RegistrationDB;
import org.junit.Assert;
import org.junit.Test;
import tickets.EvenlySplitTicket;
import tickets.NotEvenlySplitTicket;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Ticket_UTest
{
    @Test
    public void test_getters_and_setters()
    {
        User testPaidUser = new User("testPaidUser");
        EvenlySplitTicket testEvenlySplitTicket = new EvenlySplitTicket("Airplane", testPaidUser.getID());

        Assert.assertEquals("Testing getTypeOfTicket", "Airplane", testEvenlySplitTicket.getTypeOfTicket());
        testEvenlySplitTicket.setTypeOfTicket("Club");
        Assert.assertEquals("Testing setTypeOfTicket", "Club", testEvenlySplitTicket.getTypeOfTicket());

        Assert.assertEquals("Testing getPaidUser", testPaidUser.getID(), testEvenlySplitTicket.getPaidUser());
        User testPaidUser2 = new User("testPaidUser");
        testEvenlySplitTicket.setPaidUser(testPaidUser2.getID());
        Assert.assertEquals("Testing setPaidUser", testPaidUser2.getID(), testEvenlySplitTicket.getPaidUser());

        testEvenlySplitTicket.setPaidPrice(15.0);
        Assert.assertEquals("Testing get-_and_setPaidPrice", 15.0, testEvenlySplitTicket.getPaidPrice(), 0);

        HashMap<UUID, Double> testUserPriceMap = new HashMap<>();
        testUserPriceMap.put(testPaidUser.getID(), 5.0);
        testUserPriceMap.put(testPaidUser2.getID(), 10.0);
        testEvenlySplitTicket.setUserPriceMap(testUserPriceMap);
        Assert.assertEquals("Testing get-_and_setUserPriceMap", testUserPriceMap, testEvenlySplitTicket.getUserPriceMap());
    }

    @Test
    public void test_setPriceInvolvedUsers_Evenly()
    {
        User testPaidUser = new User("testPaidUser");
        EvenlySplitTicket testEvenlySplitTicket = new EvenlySplitTicket("Airplane", testPaidUser.getID());
        ArrayList<UUID> testInvolvedUsers = new ArrayList<>();
        double testPaidPrice = 12.0;

        User testUser1 = new User("testUser1");
        User testUser2 = new User("testUser2");
        User testUser3 = new User("testUser3");

        testInvolvedUsers.add(testUser1.getID());
        testInvolvedUsers.add(testUser2.getID());
        testInvolvedUsers.add(testUser3.getID());

        testEvenlySplitTicket.setPaidPrice(testPaidPrice);
        testEvenlySplitTicket.setPriceInvolvedUsers(testInvolvedUsers);

        HashMap<UUID, Double> expectedUserPriceMap = new HashMap<>();
        expectedUserPriceMap.put(testUser1.getID(), 3.0);
        expectedUserPriceMap.put(testUser2.getID(), 3.0);
        expectedUserPriceMap.put(testUser3.getID(), 3.0);

        Assert.assertEquals("Testing setPriceInvolvedUsers", expectedUserPriceMap, testEvenlySplitTicket.getUserPriceMap());
    }

    @Test
    public void test_setPriceInvolvedUsers_NotEvenly()
    {
        User testPaidUser = new User("testPaidUser");
        NotEvenlySplitTicket testNotEvenlySplitTicket = new NotEvenlySplitTicket("Airplane", testPaidUser.getID());
        HashMap<UUID,Double> testInvolvedUserPriceMap = new HashMap<>();

        User testUser1 = new User("testUser1");
        User testUser2 = new User("testUser2");
        User testUser3 = new User("testUser3");

        testInvolvedUserPriceMap.put(testUser1.getID(), 2.0);
        testInvolvedUserPriceMap.put(testUser2.getID(), 3.0);
        testInvolvedUserPriceMap.put(testUser3.getID(), 4.0);

        testNotEvenlySplitTicket.setPriceInvolvedUsers(testInvolvedUserPriceMap);

        HashMap<UUID, Double> expectedUserPriceMap = new HashMap<>();
        expectedUserPriceMap.put(testUser1.getID(), 2.0);
        expectedUserPriceMap.put(testUser2.getID(), 3.0);
        expectedUserPriceMap.put(testUser3.getID(), 4.0);

        Assert.assertEquals("Testing setPriceInvolvedUsers", expectedUserPriceMap, testNotEvenlySplitTicket.getUserPriceMap());
    }

    @Test
    public void test_toString()
    {
        //testUser has to pay 5 euros to testPaidUser
        User testPaidUser = new User("testPaidUser");
        User testUser = new User("testUser");

        EvenlySplitTicket testEvenlySplitTicket = new EvenlySplitTicket("Taxi", testPaidUser.getID());
        RegistrationDB.getUserDatabase().addInDBHashMap(testUser.getID(), testUser);
        RegistrationDB.getUserDatabase().addInDBHashMap(testPaidUser.getID(), testPaidUser);

        HashMap<UUID, Double> testUserPriceMap = new HashMap<>();
        testUserPriceMap.put(testUser.getID(), 5.0);
        testEvenlySplitTicket.setUserPriceMap(testUserPriceMap);
        testEvenlySplitTicket.setPaidUser(testPaidUser.getID());
        testEvenlySplitTicket.setPaidPrice(10.0);

        String expectedString = "Taxi: 10.0 euro paid by testPaidUser. Users who need to pay: testUser -> 5.0 euro  ";

        Assert.assertEquals("Testing toString", expectedString, testEvenlySplitTicket.toString());
    }
}
