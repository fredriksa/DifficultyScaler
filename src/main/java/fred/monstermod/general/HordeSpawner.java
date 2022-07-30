package fred.monstermod.general;

import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.core.listeners.iCustomSpawnEventListener;
import fred.monstermod.runnables.TimeTrackerRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HordeSpawner implements iCustomSpawnEventListener {

    final List<EntityType> HordeCandidates = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.SPIDER,
            EntityType.SKELETON
    );

    final int maxZombieHordeChance = 50;

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

    @Override
    public void OnCustomSpawn(CreatureSpawnEvent event) {
        if (!shouldBeMadeHorde(event))
        {
            return;
        }

        final Byte lightLevel = event.getLocation().getBlock().getLightFromSky();
        if (lightLevel > 0)
        {
            Optional<Integer> spawnModifier = PluginRegistry.Instance().modifiers.getSpawnModifier(event.getEntityType());
            if (!spawnModifier.isPresent())
            {
                return;
            }

            final int hordeCount = spawnModifier.get() * 2;
            Bukkit.getLogger().info("Spawning horde with count: " + hordeCount);
            for (int i = 0; i < hordeCount; i++)
            {
                World world = event.getLocation().getWorld();
                Location spawnLocation = event.getLocation();
                world.spawnEntity(spawnLocation, event.getEntityType());
            }
        }
    }
}
