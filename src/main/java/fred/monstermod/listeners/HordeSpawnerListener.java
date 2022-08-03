package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.events.AdditionalEntitySpawnGroupEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HordeSpawnerListener implements Listener {

    final List<EntityType> HordeCandidates = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.SPIDER,
            EntityType.SKELETON
    );

    private boolean isHordeCandidate(Entity entity)
    {
        return HordeCandidates.contains(entity.getType());
    }

    private boolean shouldBeMadeHorde(CreatureSpawnEvent event)
    {
        final String worldName = event.getLocation().getWorld().getName();
        if (!worldName.equals("world") || !isHordeCandidate(event.getEntity()))
        {
            return false;
        }

        return RandomUtil.shouldEventOccur(Config.ZOMBIE_HORDE_MIN_CHANCE, Config.ZOMBIE_HORDE_MAX_CHANCE);
    }

    @EventHandler
    public void onAdditionalEntitySpawnGroup(AdditionalEntitySpawnGroupEvent event) {
        CreatureSpawnEvent creatureSpawnEvent = event.getCreatureSpawnEvent();
        if (!shouldBeMadeHorde(creatureSpawnEvent))
        {
            return;
        }

        final Byte lightLevel = creatureSpawnEvent.getLocation().getBlock().getLightFromSky();
        if (lightLevel > 0)
        {
            Optional<Integer> spawnModifier = PluginRegistry.Instance().modifiers.getSpawnModifier(creatureSpawnEvent.getEntityType());
            if (!spawnModifier.isPresent())
            {
                return;
            }

            final int hordeCount = spawnModifier.get() * 2;
            Bukkit.getLogger().info("Spawning horde with count: " + hordeCount);
            for (int i = 0; i < hordeCount; i++)
            {
                World world = creatureSpawnEvent.getLocation().getWorld();
                Location spawnLocation = creatureSpawnEvent.getLocation();
                world.spawnEntity(spawnLocation, creatureSpawnEvent.getEntityType());
            }
        }
    }
}
