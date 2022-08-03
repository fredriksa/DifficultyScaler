package fred.monstermod.runnables;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedRemoveBlock extends BukkitRunnable {

    public DelayedRemoveBlock(Block block)
    {
        this.block = block;
    }

    @Override
    public void run() {
        block.breakNaturally();
    }

    private Block block;
}
