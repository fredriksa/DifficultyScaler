package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class DrownedSpawnEventListener implements Listener {

    @EventHandler
    public void OnCreatureSpawnEvent(CreatureSpawnEvent event)
    {
        final boolean isDrowned = event.getEntityType() == EntityType.DROWNED;
        if (!isDrowned)
        {
            return;
        }

        if (!RandomUtil.shouldEventOccur(Config.DROWNED_TRIDENT_SPAWN_MIN_CHANCE, Config.DROWNED_TRIDENT_SPAWN_MAX_CHANCE))
        {
            return;
        }

        ItemStack tridentStack = new ItemStack(Material.TRIDENT);
        EntityEquipment drownedEquipment = event.getEntity().getEquipment();
        drownedEquipment.setItemInMainHand(tridentStack);
    }
}
