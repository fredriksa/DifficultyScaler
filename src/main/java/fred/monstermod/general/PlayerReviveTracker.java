package fred.monstermod.general;

import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.listeners.TicksUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class PlayerReviveTracker {

    private NamespacedKey lastReviveGameTimeKey = new NamespacedKey(PluginRegistry.Instance().monsterMod, "lastReviveGameTime");

    public boolean readyToBeRevived(Player player)
    {
        if (player == null) return false;
        if (!hasLastReviveGameTimeEntry(player)) return true;

        final long lastReviveGameTime = player.getPersistentDataContainer().get(lastReviveGameTimeKey, PersistentDataType.LONG);
        final long timeSinceLastReviveInTicks = getMainWorldTickTime() - lastReviveGameTime;

        return TicksUtil.secondsToTicks(Config.REVIVE_COOLDOWN_SECONDS) <= timeSinceLastReviveInTicks;
    }

    public long timeLeftUntilRevivable(Player player)
    {
        if (player == null) return 0;
        if (!hasLastReviveGameTimeEntry(player)) return 0;


        final long lastReviveGameTime = player.getPersistentDataContainer().get(lastReviveGameTimeKey, PersistentDataType.LONG);
        final long timeSinceLastReviveInTicks = getMainWorldTickTime() - lastReviveGameTime;
        final long tickDifference = TicksUtil.secondsToTicks(Config.REVIVE_COOLDOWN_SECONDS) - timeSinceLastReviveInTicks;

        return TicksUtil.ticksToSeconds(tickDifference);
    }

    public void trackRevive(Player player)
    {
        if (player == null) return;
        player.getPersistentDataContainer().set(lastReviveGameTimeKey, PersistentDataType.LONG, getMainWorldTickTime());
    }

    public void untrackRevive(Player player)
    {
        if (player == null) return;

        player.getPersistentDataContainer().remove(lastReviveGameTimeKey);
    }

    private boolean hasLastReviveGameTimeEntry(Player player)
    {
        return player.getPersistentDataContainer().getKeys().contains(lastReviveGameTimeKey);
    }

    private long getMainWorldTickTime()
    {
        World firstWorld = Bukkit.getWorlds().get(0);
        if (firstWorld == null) return -1;
        return firstWorld.getGameTime();
    }
}
