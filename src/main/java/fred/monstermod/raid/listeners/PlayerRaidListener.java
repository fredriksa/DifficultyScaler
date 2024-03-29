package fred.monstermod.raid.listeners;

import fred.monstermod.core.PluginRegistry;
import fred.monstermod.raid.core.RaidConfig;
import fred.monstermod.raid.core.RaidSession;
import fred.monstermod.raid.core.RaidUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSpawnChangeEvent;

import java.util.UUID;

public class PlayerRaidListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        if (!player.getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        RaidSession session = PluginRegistry.Instance().raid.sessions.getCurrentRaidSession(player);
        if (session == null) return;
        session.leave(player);
        player.sendMessage(ChatColor.RED + "Loot eludes you... for now!");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        Location location = RaidUtils.playerTeleportBackLocation(player);
        if (location == null) return;
        event.setRespawnLocation(location);
    }

    @EventHandler
    public void onPlayerSpawnChangeEvent(PlayerSpawnChangeEvent event)
    {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;
        event.setCancelled(true);

        player.sendMessage(ChatColor.RED + "You may not change your spawn point to the raid world. Nice try though!");
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event)
    {
        if (!event.getBlock().getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        RaidSession session = PluginRegistry.Instance().raid.sessions.getCurrentRaidSession(event.getPlayer());
        if (session == null) return;

        if (session.isExitBlock(event.getBlock()))
            event.setCancelled(true);
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event)
    {
        if (!event.getPlayer().getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        RaidSession session = PluginRegistry.Instance().raid.sessions.getCurrentRaidSession(event.getPlayer());
        if (session == null)
        {
            event.getPlayer().sendMessage(ChatColor.RED + "Server is unable to find your raid session. You can not exit.");
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();

            if (!session.isExitBlock(clickedBlock))
                return;

            boolean isEveryoneNear = true;
            for (UUID playerUuid : session.getPlayers())
            {
                Player member = Bukkit.getPlayer(playerUuid);
                if (member == null) continue;
                if (!member.getWorld().getName().equals(RaidConfig.WORLD_NAME)) continue;

                double distanceBetweenPlayers = event.getPlayer().getLocation().distance(member.getLocation());
                if (distanceBetweenPlayers > RaidConfig.EXTRACTION_DISTANCE_MAX)
                    isEveryoneNear = false;
            }

            if (isEveryoneNear)
            {
                session.leave(event.getPlayer());
                RaidUtils.teleportPlayerBack(event.getPlayer());
                event.getPlayer().sendMessage(ChatColor.GREEN + "You made it out alive, congratulations!");
            }
            else
            {
                event.getPlayer().sendMessage(ChatColor.RED + "All remaining raid members must be present to exit.");
            }
        }
    }

    // Cancel breaking exit point block
}
