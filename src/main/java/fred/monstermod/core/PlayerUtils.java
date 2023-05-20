package fred.monstermod.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class PlayerUtils {

    public static int getOnlinePlayerCount(HashSet<UUID> players)
    {
        int count = 0;
        for (UUID playerUuid : players)
        {
            Player player = Bukkit.getPlayer(playerUuid);
            if (playerUuid != null) count++;
        }

        return count;
    }

}
