package fred.monstermod.runnables;

import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.listeners.TicksUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class LookingAtPiglinStarterRunnable extends BukkitRunnable {

    private final String playerRunnableMetaDataKey = "lookingAtEntityStarter";

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.getMetadata(playerRunnableMetaDataKey).size() > 0)
            {
                continue;
            }

            LookingAtPiglinRunnable lookingRunnable = new LookingAtPiglinRunnable(player);
            lookingRunnable.runTaskTimer(PluginRegistry.Instance().monsterMod, 0, TicksUtil.secondsToTicks(1));
            player.setMetadata(playerRunnableMetaDataKey, new FixedMetadataValue(PluginRegistry.Instance().monsterMod, true));
        }
    }
}
