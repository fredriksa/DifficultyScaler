package fred.monstermod.raid.core;

import fred.monstermod.raid.Raid;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RaidLeaveHandler {

    private Raid raid;

    public RaidLeaveHandler(Raid _raid) {
        raid = _raid;
    }

    public void leaveRaid(Player player)
    {
        RaidSession session = raid.sessions.getCurrentRaidSession(player);
        if (session == null)
        {
            player.sendMessage(ChatColor.GREEN + "You are not part of any raid.");
            return;
        }

        if (session.getStatus().equals(RaidSessionStatus.ACTIVE))
        {
            leaveActiveRaid(player);
            return;
        }

        if (session.getStatus().equals(RaidSessionStatus.PREPARING))
        {
            leavePreparingRaid(player);
            return;
        }
    }

    public void leavePreparingRaid(Player player)
    {
        RaidSession session = raid.sessions.getCurrentRaidSession(player);
        session.leave(player);

        player.sendMessage(ChatColor.GREEN + "You have left the raid session.");
    }

    public void leaveActiveRaid(Player player)
    {
        RaidSession session = raid.sessions.getCurrentRaidSession(player);

        session.leave(player);

        player.sendMessage(ChatColor.GREEN + "You have left the raid.");
        player.damage(100);
    }
}
