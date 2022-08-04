package fred.monstermod.core;

import org.bukkit.Bukkit;

import java.util.Random;

public class RandomUtil {

    /**
     * Checks whether an event should occur or not based on the current phase progression.
     * @param minProbability The minimum probability for the event to occur (0-100).
     * @param maxProbability The maximum probability for the event to occur (0-100).
     * @return True if the event should occur, false if not.
     */
    public static boolean shouldEventOccur(int minProbability, int maxProbability)
    {
        final double phaseChance = PluginRegistry.Instance().timeTracker.getPhaseModifier() * maxProbability;
        final double chance = Math.max(minProbability, phaseChance);

        Random random = new Random();
        final int randomInt = random.nextInt(100) + 1;
        return randomInt <= chance;
    }

    /**
     * Checks whether a event should occur based on a probability.
     * This is not affected by other things, such as current phase.
     * @param probability The probability that the event should occur.
     * @return True if the event should occur, false if not.
     */
    public static boolean shouldEventOccur(int probability)
    {
        Random random = new Random();
        final int randomInt = random.nextInt(100) + 1;
        return randomInt <= probability;
    }

    public static double random(int min, int max)
    {
        Random random = new Random();
        final double nextDouble = random.nextDouble();
        final double candidate =  nextDouble * max;
        return Math.max(min, candidate);
    }
}
