package fred.monstermod.raid.core;

import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RaidUtils {
    public static Location playerTeleportBackLocation(Player player)
    {
        PlayerRaidDataStore.ExitData exitData = PluginRegistry.Instance().raid.playerData.getExitData(player.getUniqueId());
        if (exitData == null)
        {
            player.sendMessage("Could not find teleport back to location!");
            return null;
        }

        World teleportBackToWorld = Bukkit.getWorld(exitData.worldName);
        if (teleportBackToWorld == null) return null;

        return teleportBackToWorld.getBlockAt((int)exitData.x, (int)exitData.y + 1, (int)exitData.z).getLocation();
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
