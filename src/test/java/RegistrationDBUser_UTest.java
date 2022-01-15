import database.Database;
import database.RegistrationDB;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import user.User;

import java.lang.reflect.Field;
import java.util.*;


// Run with PowerMock, an extended version of Mockito
@RunWith(PowerMockRunner.class)
// Prepare class RegistrationController for testing by injecting mocks
@PrepareForTest(RegistrationDB.class)

public class RegistrationDBUser_UTest
{
    public RegistrationDBUser_UTest()
    {

    }

    @Before
    public void initialize()
    {

    }

    @Test
    @SuppressWarnings("unchecked")
    public void t_addInDBHashMap() throws NoSuchFieldException, IllegalAccessException
    {
        Field field = RegistrationDB.class.getDeclaredField("dbHashMap");
        field.setAccessible(true);

        Database<User> user_registrationDB_underTest = RegistrationDB.getUserDatabase();
        HashMap<UUID, ArrayList<User>> mock_user_db = (HashMap<UUID, ArrayList<User>>) Mockito.mock(HashMap.class);
        field.set(user_registrationDB_underTest, mock_user_db);

        User mockUser = Mockito.mock(User.class);
        UUID mockUUID = UUID.randomUUID();
        Mockito.when(mockUser.getID()).thenReturn(mockUUID);
        ArrayList<User> mockUserList = new ArrayList<>();
        mockUserList.add(mockUser);

        user_registrationDB_underTest.addInDBHashMap(mockUUID, mockUser);
        Mockito.verify(mock_user_db, Mockito.times(1)).put(mockUUID, mockUserList);
    }

    @Test
    public void t_getValueDBHashmap() throws NoSuchFieldException, IllegalAccessException
    {
        Field field = RegistrationDB.class.getDeclaredField("dbHashMap");
        field.setAccessible(true);

        Database<User> user_registrationDB_underTest = RegistrationDB.getUserDatabase();
        HashMap<UUID, ArrayList<User>> mock_user_db = new HashMap<>();
        field.set(user_registrationDB_underTest, mock_user_db);

        UUID mockUUID = UUID.randomUUID();
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getID()).thenReturn(mockUUID);
        ArrayList<User> mockUserList = new ArrayList<>();
        mockUserList.add(mockUser);

        user_registrationDB_underTest.addInDBHashMap(mockUUID, mockUser);

        ArrayList<User> returnedUserList = user_registrationDB_underTest.getValueDBHashmap(mockUUID);
        Assert.assertEquals("Testing getValueDBHashmap - should return mockObject", mockUserList, returnedUserList);
    }
}


