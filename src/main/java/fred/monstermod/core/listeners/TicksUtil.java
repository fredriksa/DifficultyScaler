package fred.monstermod.core.listeners;

public class TicksUtil
{
    public static int secondsToTicks(double seconds)
    {
        return (int)(seconds * 20);
    }
}
