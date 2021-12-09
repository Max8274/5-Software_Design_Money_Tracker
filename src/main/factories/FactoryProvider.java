package factories;

public class FactoryProvider
{
    public static TicketFactory getEvenlySplitTicketFactory()
    {
        return new EvenlySplitTicketFactory();
    }

    public static TicketFactory getNotEvenlySplitTicketFactory()
    {
        return new NotEvenlySplitTicketFactory();
    }
}
