package fred.monstermod.listeners;

import fred.monstermod.events.MonsterSpawnerBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MonsterSpawnerBlockBreakListener implements Listener {

    // Note for self: BlockBreakEvent only called when a block is broken by a player.
    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event)
    {
        final Block brokenBlock = event.getBlock();
        if (brokenBlock.getType() == Material.SPAWNER)
        {
            MonsterSpawnerBreakEvent breakEvent = new MonsterSpawnerBreakEvent(event.getPlayer(), brokenBlock);
            Bukkit.getServer().getPluginManager().callEvent(breakEvent);
        }
    }
}
