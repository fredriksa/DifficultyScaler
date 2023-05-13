package fred.monstermod.raid.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SessionEndHandler {

    private RaidSession session;

    public SessionEndHandler(RaidSession _session)
    {
        session = _session;
    }

    public void onEnd()
    {
        for (UUID playerUuid : session.getPlayers())
        {
            Player player = Bukkit.getPlayer(playerUuid);
            if (player == null) continue;

            player.sendMessage(ChatColor.RED + "The raid '" + session.getName() + "' ended up in failure. You did not escape in time.");
            player.damage(1000000);
        }

        session.setStatus(RaidSessionStatus.FINISHED);
        session.destroy();
    }
}
