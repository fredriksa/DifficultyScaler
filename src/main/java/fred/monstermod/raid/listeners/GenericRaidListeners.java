package fred.monstermod.raid.listeners;

import fred.monstermod.raid.core.RaidConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSpawnChangeEvent;

public class GenericRaidListeners implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        if (player.hasMetadata(RaidConfig.METADATAKEY_RAID_JOIN_WORLD))
        {
            final String worldName = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_WORLD).get(0).asString();
            World teleportBackToWorld = Bukkit.getWorld(worldName);

            int x = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_X).get(0).asInt();
            int y = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_Y).get(0).asInt();
            int z = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_Z).get(0).asInt();

            event.setRespawnLocation(teleportBackToWorld.getBlockAt(x,y,z).getLocation());
        }
    }

    @EventHandler
    public void onPlayerSpawnChangeEvent(PlayerSpawnChangeEvent event)
    {
        Player player = event.getPlayer();
        if (!player.getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;
        event.setCancelled(true);

        player.sendMessage(ChatColor.RED + "You may not change your spawn point to the raid world. Nice try though!");
    }
}
