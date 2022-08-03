package fred.monstermod.core;

public class MathUtils {

    /**
     * Calculates chance for a given value (current value), depending on how close it is to the max value.
     * The closer the max value, the higher the returned chance. But the chance returned can at max be maxPercent.
     * @param minValue The minimum value the current value can be.
     * @param maxValue The maximum value the current value can be.
     * @param minPercent The percentage at the minimum value.
     * @param maxPercent The percentage for the maxmimum value.
     * @param currentValue The value that we are calculating the percentage for.
     * @return The chance.
     */
    public static double calculateChance(int minValue, int maxValue, int minPercent, int maxPercent, double currentValue)
    {
        double ratio = (Math.abs(currentValue) - Math.abs(minValue))/(Math.abs(maxValue) - Math.abs(minValue));
        double result = minPercent + ratio*(maxPercent - minPercent);
        return result > maxPercent ? maxPercent : result;
    }
}
