package fred.monstermod.raid.core;

import fred.monstermod.raid.Raid;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RaidJoinHandler {

    private Raid raid;

    public RaidJoinHandler(Raid _raid) {
        raid = _raid;
    }

    public boolean onRaidJoin(Player player, String message)
    {
        String[] commands = message.split(" ");
        if (commands.length != 3) return false;

        String raidSessionName = commands[2];
        return joinRaid(player, raidSessionName);
    }

    private boolean joinRaid(Player player, String raidSessionName)
    {
        RaidSession raidSession = raid.sessions.getCurrentRaidSession(player);
        if (raidSession != null)
        {
            player.sendMessage(ChatColor.RED + "You are already in a raid session!");
            return false;
        }

        RaidSession candidateRaidSession = raid.sessions.getRaidSession(raidSessionName);
        if (candidateRaidSession == null) {
            candidateRaidSession = raid.sessions.createRaidSession(raidSessionName, player);
            if (candidateRaidSession == null) {
                player.sendMessage(ChatColor.RED + "Unable to create raid. Reason unknown.");
                return false;
            }

            player.sendMessage(ChatColor.GREEN + "You have created raid: " + candidateRaidSession.getName() + ".");
            return true;
        }

        candidateRaidSession.join(player);
        return true;
    }
}
