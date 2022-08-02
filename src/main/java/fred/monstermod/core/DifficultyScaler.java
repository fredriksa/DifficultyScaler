package fred.monstermod.core;

import org.bukkit.Bukkit;

public class DifficultyScaler {

    public static double scale(double value)
    {
        final int numberOfPlayers = Bukkit.getOnlinePlayers().size();
        final int playerScaler = Math.max(0, numberOfPlayers - 1);
        final double playerModifier = 1 + (playerScaler * Config.DIFFICULTY_MODIFIER_PER_PLAYER);
        final double scaledValue = value * playerModifier;
        return scaledValue;
    }
}
