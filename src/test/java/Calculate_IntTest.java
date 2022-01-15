import controller.Controller;
import controller.RegistrationController;
import database.Database;
import database.RegistrationDB;
import org.junit.Assert;
import org.junit.Test;
import tickets.Ticket;
import user.User;
import visual.screens.CalculateScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Calculate_IntTest
{
    @Test
    public void test_calculate_evenlySplitTicket()
    {
        Database<User> testUserDatabase = RegistrationDB.getUserDatabase();
        Controller testController = RegistrationController.getController();

        //Ticket 1
        String testTypeOfTicket = "Club";
        User testUser1 = new User("user1");
        User testUser2 = new User("user2");
        User testUser3 = new User("user3");
        User testUser4 = new User("user4");

        double testPaidPrice = 20.0;
        ArrayList<UUID> testInvolvedUsers = new ArrayList<>();
        testInvolvedUsers.add(testUser2.getID());
        testInvolvedUsers.add(testUser3.getID());
        testInvolvedUsers.add(testUser4.getID());

        testUserDatabase.addInDBHashMap(testUser1.getID(), testUser1);
        testUserDatabase.addInDBHashMap(testUser2.getID(), testUser2);
        testUserDatabase.addInDBHashMap(testUser3.getID(), testUser3);
        testUserDatabase.addInDBHashMap(testUser4.getID(), testUser4);

        Ticket testEvenlySplitTicket = testController.addEvenlySplitTicket(testTypeOfTicket, testUser1.getID(), testPaidPrice, testInvolvedUsers);
        CalculateScreen testCalculateScreen = new CalculateScreen();

        HashMap<UUID, Double> expectedUserHashMap = new HashMap<>();

        //testUser1 only receives money, so his userHashMap is empty
        Assert.assertEquals("Testing userHashMap testInvolvedUser1", expectedUserHashMap, testUser1.getUserHashmap());

        expectedUserHashMap.put(testUser1.getID(), 5.0); //testUser2 has to pay 20/4 = 5 euro to testUser1
        Assert.assertEquals("Testing userHashMap testInvolvedUser2", expectedUserHashMap, testUser2.getUserHashmap());
        expectedUserHashMap.clear();

        expectedUserHashMap.put(testUser1.getID(), 5.0); //testUser3 has to pay 20/4 = 5 euro to testUser1
        Assert.assertEquals("Testing userHashMap testInvolvedUser3", expectedUserHashMap, testUser3.getUserHashmap());
        expectedUserHashMap.clear();

        expectedUserHashMap.put(testUser1.getID(), 5.0); //testUser4 has to pay 20/4 = 5 euro to testUser1
        Assert.assertEquals("Testing userHashMap testInvolvedUser4", expectedUserHashMap, testUser4.getUserHashmap());
        expectedUserHashMap.clear();

        //Ticket 2
        //now is testUser2 the user who has paid
        testTypeOfTicket = "Airplane";
        testPaidPrice = 40.0;
        testInvolvedUsers.clear();
        testInvolvedUsers.add(testUser1.getID());
        testInvolvedUsers.add(testUser3.getID());
        testInvolvedUsers.add(testUser4.getID());

        Ticket testEvenlySplitTicket2 = testController.addEvenlySplitTicket(testTypeOfTicket, testUser2.getID(), testPaidPrice, testInvolvedUsers);
        testCalculateScreen = new CalculateScreen();

        expectedUserHashMap.put(testUser2.getID(), 5.0); //testUser1 has to pay 40/4 = 10 euro to testUser2
                                                          //and receives 20/4 = 5 euro from testUser2
                                                          //Calculation: has to pay 5 euro to testUser2 in total
        Assert.assertEquals("Testing userHashMap testInvolvedUser1", expectedUserHashMap, testUser1.getUserHashmap());
        expectedUserHashMap.clear();

        //testUser2 only receives money, so his userHashMap is empty
        Assert.assertEquals("Testing userHashMap testInvolvedUser2", expectedUserHashMap, testUser2.getUserHashmap());

        expectedUserHashMap.put(testUser1.getID(), 5.0); //testUser3 has to pay 20/4 = 5 euro to testUser1
        expectedUserHashMap.put(testUser2.getID(), 10.0); //testUser3 has to pay 40/4 = 10 euro to testUser2
        Assert.assertEquals("Testing userHashMap testInvolvedUser3", expectedUserHashMap, testUser3.getUserHashmap());
        expectedUserHashMap.clear();

        expectedUserHashMap.put(testUser1.getID(), 5.0); //testUser4 has to pay 20/4 = 5 euro to testUser1
        expectedUserHashMap.put(testUser2.getID(), 10.0); //testUser4 has to pay 40/4 = 10 euro to testUser2
        Assert.assertEquals("Testing userHashMap testInvolvedUser4", expectedUserHashMap, testUser4.getUserHashmap());
        expectedUserHashMap.clear();
    }
}
