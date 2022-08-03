package fred.monstermod.listeners;

import fred.monstermod.Monstermod;
import fred.monstermod.core.BlockUtils;
import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.runnables.DelayedMessageRunnable;
import fred.monstermod.runnables.DelayedRemoveBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;
import java.util.List;

public class TorchPlacementPreventionListener implements Listener {

    private List<Material> torchMaterials = Arrays.asList(Material.TORCH, Material.WALL_TORCH);

    @EventHandler
    public void onBlockCanBuildEvent(BlockPlaceEvent event)
    {
        final Block placedBlock = event.getBlockPlaced();
        final boolean isTorch = torchMaterials.contains(placedBlock.getType());
        if (!isTorch)
        {
            return;
        }

        int blockY = Integer.MAX_VALUE;

        // If the torch is attached to a face, then the getY always returns 1, so we need to find
        // the Y value of that face.
        final Block face = BlockUtils.getAttachedFace(placedBlock);
        if (face != null)
        {
            blockY = face.getY();
        }
        else
        {
            blockY = placedBlock.getLocation().getBlockY();
        }

        final boolean torchIsplacedToDeep = blockY < Config.TORCH_LOWEST_Y_LEVEL;
        if (torchIsplacedToDeep)
        {
            final Monstermod monstermod = PluginRegistry.Instance().monsterMod;
            DelayedRemoveBlock removeBlock = new DelayedRemoveBlock(placedBlock);
            removeBlock.runTaskLater(monstermod, 150);
        }
    }
}
