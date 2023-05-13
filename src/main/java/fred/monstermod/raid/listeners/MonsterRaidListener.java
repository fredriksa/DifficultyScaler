package fred.monstermod.raid.listeners;

import fred.monstermod.core.BlockUtils;
import fred.monstermod.events.AdditionalEntitySpawnGroupEvent;
import fred.monstermod.raid.core.RaidConfig;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class MonsterRaidListener implements Listener {

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (!event.getEntity().getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;
        if (!(event.getEntity() instanceof Monster)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onAdditionalEntitySpawnGroupEvent(AdditionalEntitySpawnGroupEvent event)
    {
        LivingEntity entity = event.getCreatureSpawnEvent().getEntity();
        if (!entity.getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        final Byte lightLevel = entity.getLocation().getBlock().getLightFromSky();
        if (lightLevel > 0)
        {
            Block highestBlock = BlockUtils.getHighestYBlock(entity.getWorld(), (int)entity.getLocation().getX(), (int)entity.getLocation().getZ());

            Location spawnLocation = highestBlock.getLocation().add(0, 25, 0);
            Entity spawnedEntity = entity.getWorld().spawnEntity(spawnLocation, EntityType.GHAST);
            spawnedEntity.setPersistent(true);

            Location spawnLocationTwo = spawnLocation.add(2, 0, 2);
            Entity spawnedEntityTwo = entity.getWorld().spawnEntity(spawnLocationTwo, EntityType.GHAST);
            spawnedEntityTwo.setPersistent(true);
        }
    }
}
