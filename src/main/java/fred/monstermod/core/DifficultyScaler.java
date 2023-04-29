package fred.monstermod.core;

import org.bukkit.Bukkit;

public class DifficultyScaler {

    public static double scaleWithPlayers(double value)
    {
        return scaleWithPlayers(value, Config.DIFFICULTY_MODIFIER_PER_PLAYER);
    }

    public static double scaleWithPlayers(double value, double scaler)
    {
        final int numberOfPlayers = Bukkit.getOnlinePlayers().size();
        final int playerScaler = Math.max(0, numberOfPlayers - 1);
        final double playerModifier = 1 + (playerScaler * scaler);
        final double scaledValue = value * playerModifier;
        return scaledValue;
    }

    public static double scaleWithPhases(double value)
    {
        final double phaseModifier = PluginRegistry.Instance().timeTracker.getPhaseModifier();
        final double scaler = 1 + phaseModifier;
        return value * scaler;
    }
}
