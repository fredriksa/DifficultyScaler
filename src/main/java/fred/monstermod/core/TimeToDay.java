package fred.monstermod.core;

public class TimeToDay {

    /**
     * Converts server time into day from 1 and to inclusive 7.
     * @param time Server time since creation (24 hours = 24000)
     * @return Day between from inclusive 1 and to 7.
     */
    public static long convert(long time) {
        if (time < 0)
        {
            throw new IllegalArgumentException("Can't convert negative time");
        }

        return ((time / 24000) % 7) + 1;
    }
}
