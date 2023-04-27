package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.RandomUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;

public class SaplingPlantListener implements Listener {

    private HashMap<Material, Material> treeMaterialToSapling = new HashMap<>();
    private HashSet<Material> allowedPlantMaterials = new HashSet<>();

    public SaplingPlantListener()
    {
        treeMaterialToSapling.put(Material.OAK_LOG, Material.OAK_SAPLING);
        treeMaterialToSapling.put(Material.DARK_OAK_LOG, Material.DARK_OAK_SAPLING);
        treeMaterialToSapling.put(Material.BIRCH_LOG, Material.BIRCH_SAPLING);
        treeMaterialToSapling.put(Material.JUNGLE_LOG, Material.JUNGLE_SAPLING);
        treeMaterialToSapling.put(Material.SPRUCE_LOG, Material.SPRUCE_SAPLING);
        treeMaterialToSapling.put(Material.ACACIA_LOG, Material.ACACIA_SAPLING);

        allowedPlantMaterials.add(Material.DIRT);
        allowedPlantMaterials.add(Material.GRASS_BLOCK);
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event)
    {
        Block block = event.getBlock();

        if (!destroyedTree(block)) return;
        if (!canPlantSaplingUnderneath(block)) return;
        if (!RandomUtil.shouldEventOccur(Config.PLANT_SAPLING_ON_TREE_LOG_BROKEN_CHANCE)) return;

        BukkitRunnable runnable = new BukkitRunnable() {
            private Material originalBlockType = block.getType();

            @Override
            public void run() {
                if (block.getType() != Material.AIR) return;
                if (!canPlantSaplingUnderneath(block)) return;

                Material toSet = treeMaterialToSapling.get(originalBlockType);
                block.setType(toSet);
            }
        };

        runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 10L);
    }

    private boolean destroyedTree(Block block)
    {
        return treeMaterialToSapling.containsKey(block.getType());
    }

    private boolean canPlantSaplingUnderneath(Block brokenBLock)
    {
        Location locationUnderneath = brokenBLock.getLocation().subtract(0, 1, 0);
        Block blockUnderneath = locationUnderneath.getBlock();
        if (blockUnderneath == null) return false;
        return allowedPlantMaterials.contains(blockUnderneath.getType());
    }
}
