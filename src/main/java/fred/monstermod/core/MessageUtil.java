package fred.monstermod.core;

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
}
