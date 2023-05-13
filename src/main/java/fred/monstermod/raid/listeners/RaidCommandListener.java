package fred.monstermod.raid.listeners;

import fred.monstermod.core.PluginRegistry;
import fred.monstermod.raid.Raid;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RaidCommandListener implements Listener {

    private final String RAID_START_COMMAND = "!raid start";
    private final String RAID_JOIN_PREFIX_COMMAND = "!raid join";
    private final String RAID_LEAVE_COMMAND = "!raid leave";

    private Raid raid = PluginRegistry.Instance().raid;

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event)
    {
        if (event.getMessage().equals(RAID_START_COMMAND))
        {
            onChatRaidStart(event.getPlayer());
        }
        else if (event.getMessage().startsWith(RAID_JOIN_PREFIX_COMMAND))
        {
            onChatRaidJoin(event.getPlayer(), event.getMessage());
        }
        else if (event.getMessage().equals(RAID_LEAVE_COMMAND))
        {
            onChatRaidLeave(event.getPlayer());
        }

        // Add leave command (leave raid session both while in progress and while preparing...)
    }

    private void onChatRaidStart(Player player)
    {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                raid.raidStartHandler.onRequestRaidStart(player);
            }
        };

        runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 1);
    }

    private void onChatRaidJoin(Player player, String message)
    {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                raid.raidJoinHandler.onRaidJoin(player, message);
            }
        };

        runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 1);
    }

    private void onChatRaidLeave(Player player)
    {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                raid.raidLeaveHandler.leaveRaid(player);
            }
        };

        runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 1);
    }
}
