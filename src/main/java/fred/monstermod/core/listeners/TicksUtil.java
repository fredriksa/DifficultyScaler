package fred.monstermod.core.listeners;

public class TicksUtil
{
    public static int secondsToTicks(double seconds)
    {
        return (int)(seconds * 20);
    }

    public static long ticksToSeconds(long ticks)
    {
        return ticks / 20;
    }
}
