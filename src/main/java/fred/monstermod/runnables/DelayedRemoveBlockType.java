package fred.monstermod.runnables;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedRemoveBlockType extends BukkitRunnable {

    public DelayedRemoveBlockType(Block block, Material material)
    {
        this.block = block;
        this.material = material;
    }

    @Override
    public void run() {
        if (block.getType() == material)
        {
            block.setType(Material.AIR);
            block.breakNaturally();
        }
    }

    private Block block;
    private Material material;
}
