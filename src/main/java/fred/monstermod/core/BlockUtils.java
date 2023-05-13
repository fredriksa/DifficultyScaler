package fred.monstermod.core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashSet;

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

    public static Block getHighestYBlock(World world, int x, int z) {
        HashSet<Material> invalidMaterials = new HashSet<>();
        invalidMaterials.add(Material.AIR);
        invalidMaterials.add(Material.VOID_AIR);

        for (int y = world.getMaxHeight(); y >= 0; y--)
        {
            Block block = new Location(world, x, y, z).getBlock();

            if (!invalidMaterials.contains(block.getType()))
            {
                return block;
            }
        }

        return null;
    }
}
