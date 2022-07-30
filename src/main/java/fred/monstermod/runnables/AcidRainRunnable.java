package fred.monstermod.runnables;

import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AcidRainRunnable extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            World world = player.getWorld();

            final double highestBlockY = world.getHighestBlockAt(player.getLocation()).getLocation().getY();
            final boolean playerOnHighestBlock = highestBlockY < player.getLocation().getY();
            if (playerOnHighestBlock)
            {
                damage(player);
            }
        }
    }

    private void damage(Player player)
    {
        TimeTrackerRunnable tracker = PluginRegistry.Instance().timeTracker;
        final double phaseDamage = tracker.getPhaseModifier() * Config.ACID_RAIN_FINAL_DAY_DAMAGE;
        final double damage = Math.max(Config.ACID_RAIN_MIN_DAMAGE, phaseDamage);
        player.damage(damage);
    }
}
