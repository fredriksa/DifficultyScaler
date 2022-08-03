package fred.monstermod.core;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

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
}
