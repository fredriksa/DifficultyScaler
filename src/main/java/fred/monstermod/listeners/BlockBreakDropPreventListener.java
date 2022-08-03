package fred.monstermod.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class BlockBreakDropPreventListener implements Listener {

    @EventHandler

    public void blockBreakEvent(BlockBreakEvent event)
    {
        Block block = event.getBlock();

        List<MetadataValue> values = block.getMetadata("dropOnBreak");
        if (values.isEmpty())
        {
            return;
        }

        block.getDrops().clear();
    }
}
