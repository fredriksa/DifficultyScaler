package fred.monstermod.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeToPhaseTest {

    final long HalfDayTime = 12000;
    final long FullDayTime = 24000;

    @Test
    void convertNegative() {
        assertThrows(IllegalArgumentException.class, () -> TimeToPhase.convert(-1000));
    }

    @Test
    void convertValidDayOne()
    {
        final long dayZeroFirstTime = 0;
        assertEquals(1, TimeToPhase.convert(dayZeroFirstTime));

        final long dayZeroLastTime = dayZeroFirstTime + FullDayTime - 1;
        assertEquals(1, TimeToPhase.convert(dayZeroLastTime));
    }

    @Test
    void convertValidDayTwo()
    {
        final long dayTwoStartTime = FullDayTime;
        assertEquals(2, TimeToPhase.convert(dayTwoStartTime));

        assertEquals(2, TimeToPhase.convert(dayTwoStartTime + HalfDayTime));

        final long daytwoLastTime = dayTwoStartTime + FullDayTime - 1;
        assertEquals(2, TimeToPhase.convert(daytwoLastTime));
        assertNotEquals(2, TimeToPhase.convert(daytwoLastTime + 1));
    }

    @Test
    void convertValidDaySeven()
    {
        final long daySevenStartTime = FullDayTime * 6;
        assertEquals(7, TimeToPhase.convert(daySevenStartTime));

        assertEquals(7, TimeToPhase.convert(daySevenStartTime + HalfDayTime));

        final long daySevenLastTime = daySevenStartTime + FullDayTime - 1;
        assertEquals(7, TimeToPhase.convert(daySevenLastTime));
        assertNotEquals(7, TimeToPhase.convert(daySevenLastTime + 1));
    }

    @Test
    void convertWrapsToOne()
    {
        final long dayEightStartTime = 7 * FullDayTime;
        assertEquals(1, TimeToPhase.convert(dayEightStartTime));
        assertEquals(1, TimeToPhase.convert(dayEightStartTime * 2));
        assertEquals(1, TimeToPhase.convert(dayEightStartTime * 10));
    }
}