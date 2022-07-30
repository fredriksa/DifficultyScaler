package fred.monstermod.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeToDayTest {

    final long HalfDayTime = 12000;
    final long FullDayTime = 24000;

    @Test
    void convertNegative() {
        assertThrows(IllegalArgumentException.class, () -> TimeToDay.convert(-1000));
    }

    @Test
    void convertValidDayOne()
    {
        final long dayZeroFirstTime = 0;
        assertEquals(1, TimeToDay.convert(dayZeroFirstTime));

        final long dayZeroLastTime = dayZeroFirstTime + FullDayTime - 1;
        assertEquals(1, TimeToDay.convert(dayZeroLastTime));
    }

    @Test
    void convertValidDayTwo()
    {
        final long dayTwoStartTime = FullDayTime;
        assertEquals(2, TimeToDay.convert(dayTwoStartTime));

        assertEquals(2,TimeToDay.convert(dayTwoStartTime + HalfDayTime));

        final long daytwoLastTime = dayTwoStartTime + FullDayTime - 1;
        assertEquals(2,TimeToDay.convert(daytwoLastTime));
        assertNotEquals(2, TimeToDay.convert(daytwoLastTime + 1));
    }

    @Test
    void convertValidDaySeven()
    {
        final long daySevenStartTime = FullDayTime * 6;
        assertEquals(7, TimeToDay.convert(daySevenStartTime));

        assertEquals(7, TimeToDay.convert(daySevenStartTime + HalfDayTime));

        final long daySevenLastTime = daySevenStartTime + FullDayTime - 1;
        assertEquals(7, TimeToDay.convert(daySevenLastTime));
        assertNotEquals(7, TimeToDay.convert(daySevenLastTime + 1));
    }

    @Test
    void convertWrapsToOne()
    {
        final long dayEightStartTime = 7 * FullDayTime;
        assertEquals(1, TimeToDay.convert(dayEightStartTime));
        assertEquals(1, TimeToDay.convert(dayEightStartTime * 2));
        assertEquals(1, TimeToDay.convert(dayEightStartTime * 10));
    }
}