package fred.monstermod.listeners;

import fred.monstermod.Monstermod;
import fred.monstermod.core.*;
import fred.monstermod.core.listeners.TicksUtil;
import fred.monstermod.runnables.DelayedRemoveBlock;
import fred.monstermod.runnables.DelayedRemoveBlockType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpiderDeathListener implements Listener {

    private List<EntityType> spiderEntityTypes = Arrays.asList(EntityType.SPIDER, EntityType.CAVE_SPIDER);

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event)
    {
        final boolean isSpider = spiderEntityTypes.contains(event.getEntityType());
        if (!isSpider)
        {
            return;
        }

        final boolean shouldEventOccur = RandomUtil.shouldEventOccur(Config.SPIDER_WEB_SPAWN_MIN_CHANCE, Config.SPIDER_WEB_SPAWN_MAX_CHANCE);
        if (!shouldEventOccur)
        {
            return;
        }

        final LivingEntity spider = event.getEntity();
        final boolean didPlayerDealLastDamage = spider.getKiller() instanceof Player;
        if (!didPlayerDealLastDamage)
        {
            return;
        }

        Block block = spider.getLocation().getBlock();
        if (block.getLightFromSky() == 0)
        {
            addWebRandom(block, BlockFace.SELF);
            addWebRandom(block, BlockFace.UP);
            addWebRandom(block, BlockFace.DOWN);
            addWebRandom(block, BlockFace.NORTH);
            addWebRandom(block, BlockFace.SOUTH);
            addWebRandom(block, BlockFace.WEST);
            addWebRandom(block, BlockFace.EAST);
            addWebRandom(block, BlockFace.NORTH_EAST);
            addWebRandom(block, BlockFace.NORTH_WEST);
            addWebRandom(block, BlockFace.SOUTH_EAST);
            addWebRandom(block, BlockFace.SOUTH_WEST);
            addWebRandom(block, BlockFace.WEST_NORTH_WEST);
            addWebRandom(block, BlockFace.WEST_SOUTH_WEST);
            addWebRandom(block, BlockFace.EAST_NORTH_EAST);
            addWebRandom(block, BlockFace.EAST_SOUTH_EAST);
        }
    }

    private void addWebRandom(Block spiderBlock, BlockFace face)
    {
        if (RandomUtil.shouldEventOccur(Config.SPIDER_PER_WEB_SPAWN_MIN_CHANCE, Config.SPIDER_PER_WEB_SPAWN_MAX_CHANCE))
        {
            addWeb(spiderBlock, face);
        }
    }

    private void addWeb(Block spiderBlock, BlockFace face)
    {
        Random random = new Random();
        final int webDistance = random.nextInt(3) + 1;
        Block faceBlock = spiderBlock.getRelative(face, webDistance);

        // Randomize the cowweb going positive or negative Y (up and down).
        final int direction = random.nextInt(2) + 1;
        final BlockFace candidateDirection = direction == 1 ? BlockFace.UP : BlockFace.DOWN;
        final Block candidate = faceBlock.getRelative(candidateDirection, 1);

        if (candidate.getType() == Material.AIR)
        {
            faceBlock = candidate;
        }

        if (faceBlock.getType() == Material.AIR)
        {
            faceBlock.setType(Material.COBWEB);
        }

        BlockUtils.preventBlockDropOnBreak(faceBlock);

        final Monstermod monstermod = PluginRegistry.Instance().monsterMod;
        DelayedRemoveBlockType removeBlock = new DelayedRemoveBlockType(faceBlock, Material.COBWEB);

        final double webDuration = DifficultyScaler.scaleWithPhases(Config.SPIDER_WEB_DURATION_SECONDS);
        removeBlock.runTaskLater(monstermod, TicksUtil.secondsToTicks(webDuration));
    }
}
