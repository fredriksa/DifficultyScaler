package fred.monstermod.raid.core;

import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.listeners.TicksUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static fred.monstermod.core.PluginRegistry.Instance;

public class RaidlessPlayerCleanup
{
    public void start()
    {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                cleanupRaidlessPlayers();
            }
        };

        runnable.runTaskTimer(Instance().monsterMod, TicksUtil.secondsToTicks(1), TicksUtil.secondsToTicks(5));
    }

    private void cleanupRaidlessPlayers()
    {
        World raidWorld = Bukkit.getWorld(RaidConfig.WORLD_NAME);
        if (raidWorld == null) return;

        List<Player> players = raidWorld.getPlayers();

        for (Player player : players)
        {
            RaidSession session = PluginRegistry.Instance().raid.sessions.getCurrentRaidSession(player);
            if (session == null)
            {
                player.damage(10000);
            }
        }
    }
}
