package fred.monstermod.core;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockUtils {

    public static Block getAttachedFace(Block block)
    {
        Block face = null;
        if (block != null)
        {
            BlockData data = block.getBlockData();
            if (data instanceof Directional)
            {
                Directional directional = (Directional)data;
                face = block.getRelative(directional.getFacing().getOppositeFace());
            }
        }

        return face;
    }

    public static void preventBlockDropOnBreak(Block block)
    {
        block.setMetadata("dropOnBreak", new FixedMetadataValue(PluginRegistry.Instance().monsterMod, false));
    }
}
