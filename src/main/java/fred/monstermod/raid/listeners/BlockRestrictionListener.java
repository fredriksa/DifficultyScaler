package fred.monstermod.raid.listeners;

import fred.monstermod.core.PluginRegistry;
import fred.monstermod.raid.core.RaidConfig;
import fred.monstermod.raid.core.RaidSession;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;

public class BlockRestrictionListener implements Listener {

    // Grass should be allowed block

    private HashSet<Material> discountedBlocks = new HashSet<Material>();

    public BlockRestrictionListener()
    {
        discountedBlocks.add(Material.COAL_ORE);
        discountedBlocks.add(Material.REDSTONE_ORE);
        discountedBlocks.add(Material.EMERALD_ORE);
        discountedBlocks.add(Material.LAPIS_ORE);

        discountedBlocks.add(Material.COPPER_ORE);
        discountedBlocks.add(Material.IRON_ORE);
        discountedBlocks.add(Material.GOLD_ORE);
        discountedBlocks.add(Material.DIAMOND_ORE);

        discountedBlocks.add(Material.DEEPSLATE_COAL_ORE);
        discountedBlocks.add(Material.DEEPSLATE_REDSTONE_ORE);
        discountedBlocks.add(Material.DEEPSLATE_EMERALD_ORE);
        discountedBlocks.add(Material.DEEPSLATE_LAPIS_ORE);

        discountedBlocks.add(Material.DEEPSLATE_COPPER_ORE);
        discountedBlocks.add(Material.DEEPSLATE_IRON_ORE);
        discountedBlocks.add(Material.DEEPSLATE_GOLD_ORE);
        discountedBlocks.add(Material.DEEPSLATE_DIAMOND_ORE);

        discountedBlocks.add(Material.RAW_COPPER);
        discountedBlocks.add(Material.RAW_IRON_BLOCK);
        discountedBlocks.add(Material.RAW_IRON_BLOCK);

        discountedBlocks.add(Material.TORCH);
        discountedBlocks.add(Material.WALL_TORCH);
        discountedBlocks.add(Material.REDSTONE_TORCH);
        discountedBlocks.add(Material.REDSTONE_WALL_TORCH);
        discountedBlocks.add(Material.SOUL_TORCH);
        discountedBlocks.add(Material.SOUL_WALL_TORCH);
        discountedBlocks.add(Material.COBWEB);
        discountedBlocks.add(Material.LANTERN);
        discountedBlocks.add(Material.SOUL_LANTERN);
        discountedBlocks.add(Material.END_ROD);
        discountedBlocks.add(Material.REDSTONE_LAMP);
        discountedBlocks.add(Material.STONECUTTER);
        discountedBlocks.add(Material.CARTOGRAPHY_TABLE);
        discountedBlocks.add(Material.FLETCHING_TABLE);
        discountedBlocks.add(Material.SMITHING_TABLE);
        discountedBlocks.add(Material.GRINDSTONE);
        discountedBlocks.add(Material.LOOM);
        discountedBlocks.add(Material.FURNACE);
        discountedBlocks.add(Material.SMOKER);
        discountedBlocks.add(Material.BLAST_FURNACE);
        discountedBlocks.add(Material.ANVIL);
        discountedBlocks.add(Material.CHIPPED_ANVIL);
        discountedBlocks.add(Material.DAMAGED_ANVIL);
        discountedBlocks.add(Material.COMPOSTER);
        discountedBlocks.add(Material.JUKEBOX);
        discountedBlocks.add(Material.ENCHANTING_TABLE);
        discountedBlocks.add(Material.END_CRYSTAL);
        discountedBlocks.add(Material.STICKY_PISTON);
        discountedBlocks.add(Material.PISTON);
        discountedBlocks.add(Material.RAIL);
        discountedBlocks.add(Material.ACTIVATOR_RAIL);
        discountedBlocks.add(Material.DETECTOR_RAIL);
        discountedBlocks.add(Material.POWERED_RAIL);
        discountedBlocks.add(Material.SPAWNER);

        discountedBlocks.add(Material.CHAIN);
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event)
    {
        if (!event.getPlayer().getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        RaidSession session = PluginRegistry.Instance().raid.sessions.getCurrentRaidSession(event.getPlayer());

        if (session == null)
        {
            event.getPlayer().sendMessage(ChatColor.RED + " Could not break block - you are not part of a raid!");
            event.setCancelled(true);
            return;
        }

        if (discountedBlocks.contains(event.getBlock().getType())) return;

        if (session.brokenBlockCount(event.getPlayer()) >= RaidConfig.MAX_BLOCKS_TO_BREAK_PER_RAID)
        {
            event.getPlayer().sendMessage(ChatColor.RED + "You have ran out of blocks to break.");
            event.setCancelled(true);
            return;
        }

        session.brokeBlock(event.getPlayer());

        int brokenBlocks = session.brokenBlockCount(event.getPlayer());
        if (brokenBlocks % RaidConfig.BLOCKS_TO_BREAK_WARNING_EVERY_X_BLOCKS == 0)
        {
            int remaining = RaidConfig.MAX_BLOCKS_TO_BREAK_PER_RAID - brokenBlocks;
            event.getPlayer().sendMessage(ChatColor.YELLOW + "You have " + remaining + " blocks left to break.");
        }
    }
}
