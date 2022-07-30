package fred.monstermod.core;

import java.util.Random;

public class RandomUtil {

    /**
     * Checks whether an event should occur or not based on the current phase progression.
     * @param minProbability The minimum probability for the event to occur (0-100).
     * @param maxProbability The maximum probability for the event to occur (0-100).
     * @return True if the event should occur, false ifnot.
     */
    public static boolean shouldEventOccur(int minProbability, int maxProbability)
    {
        final double phaseChance = PluginRegistry.Instance().timeTracker.getPhaseModifier() * maxProbability;
        final double chance = Math.max(minProbability, phaseChance);

        Random random = new Random();
        final int randomInt = random.nextInt(100) + 1;
        return randomInt <= chance;
    }
}
