package fred.monstermod.core;

import fred.monstermod.Monstermod;
import fred.monstermod.runnables.DelayedMessageRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageUtil {

    public static void broadcast(String message)
    {
        for (Player player : Bukkit.getServer().getOnlinePlayers())
        {
            player.sendMessage(message);
        }
    }

    public static void broadcastWithDelay(String message, long serverTicks)
    {
        final Monstermod monstermod = PluginRegistry.Instance().monsterMod;
        DelayedMessageRunnable delayedMessageRunnable = new DelayedMessageRunnable(message);
        delayedMessageRunnable.runTaskLater(monstermod, serverTicks);
    }
}
