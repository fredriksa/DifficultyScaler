package fred.monstermod.raid.listeners;

import fred.monstermod.core.MessageUtil;
import fred.monstermod.raid.core.RaidConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class BlockDropListener implements Listener {

    HashSet<Material> increaseBlockDropsFor = new HashSet<Material>();

    public BlockDropListener()
    {
        increaseBlockDropsFor.add(Material.COAL_ORE);
        increaseBlockDropsFor.add(Material.REDSTONE_ORE);
        increaseBlockDropsFor.add(Material.EMERALD_ORE);
        increaseBlockDropsFor.add(Material.LAPIS_ORE);

        increaseBlockDropsFor.add(Material.COPPER_ORE);
        increaseBlockDropsFor.add(Material.IRON_ORE);
        increaseBlockDropsFor.add(Material.GOLD_ORE);
        increaseBlockDropsFor.add(Material.DIAMOND_ORE);

        increaseBlockDropsFor.add(Material.DEEPSLATE_COAL_ORE);
        increaseBlockDropsFor.add(Material.DEEPSLATE_REDSTONE_ORE);
        increaseBlockDropsFor.add(Material.DEEPSLATE_EMERALD_ORE);
        increaseBlockDropsFor.add(Material.DEEPSLATE_LAPIS_ORE);

        increaseBlockDropsFor.add(Material.DEEPSLATE_COPPER_ORE);
        increaseBlockDropsFor.add(Material.DEEPSLATE_IRON_ORE);
        increaseBlockDropsFor.add(Material.DEEPSLATE_GOLD_ORE);
        increaseBlockDropsFor.add(Material.DEEPSLATE_DIAMOND_ORE);

        increaseBlockDropsFor.add(Material.RAW_COPPER);
        increaseBlockDropsFor.add(Material.RAW_IRON_BLOCK);
        increaseBlockDropsFor.add(Material.RAW_IRON_BLOCK);
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent event)
    {
        if (!event.getBlock().getLocation().getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;
        if (!increaseBlockDropsFor.contains(event.getBlock().getType())) return;

        Collection<ItemStack> drops = event.getBlock().getDrops();
        for (ItemStack drop : drops)
        {
            Location dropLocation = event.getBlock().getLocation();
            ItemStack extraDrop = new ItemStack(drop.getType(), drop.getAmount());
            event.getBlock().getWorld().dropItemNaturally(dropLocation, extraDrop);
        }
    }
}
