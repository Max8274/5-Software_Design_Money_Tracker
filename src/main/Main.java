import controller.Controller;
import database.RegistrationDB;
import factories.FactoryProvider;
import observers.DBObserver;
import tickets.Ticket;
import user.User;
import tickets.EvenlySplitTicket;
import visual.ViewFrame;

import java.beans.PropertyChangeListener;

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

        RegistrationDB.getUserDatabase().addPCL(new DBObserver());

        /*Database db = RegistrationDB.getPersonAndTicketDatabase();
        Controller controller = Controller.getController(db);

        User Joris = new User("Joris");
        User Bart = new User("Bart");
        User Jan = new User("Jan");
        EvenlySplitTicket ticketJoris = (EvenlySplitTicket) controller.createTicket(FactoryProvider.getEvenlySplitTicketFactory(), "Restaurant", Joris);

        ticketJoris.addInvolvedPerson(Joris);
        ticketJoris.addInvolvedPerson(Bart);
        ticketJoris.addInvolvedPerson(Jan);
        ticketJoris.setPrices(Joris, 18);

        RegistrationDB.getPersonAndTicketDatabase().add(Joris, ticketJoris);
        System.out.println(ticketJoris.toString());*/
    }
}
