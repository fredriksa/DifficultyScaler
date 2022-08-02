package fred.monstermod.runnables;

import fred.monstermod.Monstermod;
import fred.monstermod.core.DifficultyScaler;
import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedDifficultyMessageRunnable extends BukkitRunnable {

    public DelayedDifficultyMessageRunnable(long _serverTicks)
    {
        serverTicks = _serverTicks;
    }

    public void queue()
    {
        final Monstermod monstermod = PluginRegistry.Instance().monsterMod;
        this.runTaskLater(monstermod, serverTicks);
    }

    @Override
    public void run() {
        final int playerCount = Bukkit.getServer().getOnlinePlayers().size();
        if (playerCount > 0)
        {
            final double scaledValue =  DifficultyScaler.scale(1);
            final int scaledValueToPct = (int) (scaledValue * 100);

            final String baseMessage = "Difficulty modifier updated to ";
            final String message = baseMessage + scaledValueToPct + "%";

            MessageUtil.broadcast(message);
        }
    }
    private long serverTicks;
}
