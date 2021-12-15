import database.Database;
import database.RegistrationDB;
import observers.DBUserObserver;
import observers.DBTicketObserver;
import observers.DBObserver;
import visual.ViewFrame;

public class Main
{
    public static void main(String[] args)
    {
        Main main = new Main();
        main.run();
    }

    public Main()
    {

    }

    public void run()
    {
        ViewFrame viewFrame = new ViewFrame();
        viewFrame.initialize();

        Database userDB = RegistrationDB.getUserDatabase();
        Database ticketDB = RegistrationDB.getTicketDatabase();

        userDB.addPCL(new DBObserver());
        ticketDB.addPCL(new DBObserver());
        userDB.addPCL(new DBUserObserver());
        ticketDB.addPCL(new DBTicketObserver());
    }
}
