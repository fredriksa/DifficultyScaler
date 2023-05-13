package fred.monstermod.raid.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RaidUtils {
    public static Location playerTeleportBackLocation(Player player)
    {
        if (player.hasMetadata(RaidConfig.METADATAKEY_RAID_JOIN_WORLD))
        {
            Bukkit.getLogger().info("MetaRaidJoinWorld size: " + player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_WORLD).size());
            final String worldName = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_WORLD).get(0).asString();
            World teleportBackToWorld = Bukkit.getWorld(worldName);

            int x = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_X).get(0).asInt();
            int y = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_Y).get(0).asInt();
            int z = player.getMetadata(RaidConfig.METADATAKEY_RAID_JOIN_Z).get(0).asInt();

            return teleportBackToWorld.getBlockAt(x,y,z).getLocation();
        }

        return null;
    }

    public static void teleportPlayerBack(Player player)
    {
        Location backLocation = playerTeleportBackLocation(player);
        if (backLocation == null) return;

        removeRaidSpecificItems(player);
        player.teleport(backLocation);
    }

    public static void removeRaidSpecificItems(Player player)
    {
        PlayerInventory inventory = player.getInventory();

        // Remove items player should not keep, e.g. Compass
        for (ItemStack item : inventory.getContents())
        {
            if (item == null || !item.hasItemMeta()) continue;

            if (item.getItemMeta().getDisplayName().equals(RaidConfig.COMPASS_ITEM_NAME))
                inventory.remove(item);
        }

        player.updateInventory();
    }
}
