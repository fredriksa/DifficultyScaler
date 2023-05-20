package fred.monstermod.listeners;

import fred.monstermod.core.DifficultyScaler;
import fred.monstermod.events.AdditionalEntitySpawnEvent;
import fred.monstermod.events.AdditionalEntitySpawnGroupEvent;
import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.*;

public class SpawnEventListener implements Listener {

    private HashMap<EntityType, List<EntityType>> entityTypeToEntityTypes = new HashMap<>();
    private Random random = new Random();

    public SpawnEventListener()
    {
        List<EntityType> defaultMonstersList = Arrays.asList(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER);
        entityTypeToEntityTypes.put(EntityType.ZOMBIE, defaultMonstersList);
        entityTypeToEntityTypes.put(EntityType.SKELETON, defaultMonstersList);
        entityTypeToEntityTypes.put(EntityType.CREEPER, defaultMonstersList);

        List<EntityType> spiderMonstersList = Arrays.asList(EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER);
        entityTypeToEntityTypes.put(EntityType.SPIDER, spiderMonstersList);
    }

    @EventHandler
    public void onEntitySpawnEvent(CreatureSpawnEvent event)
    {
        // Protect again magma cubes / slimes
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SLIME_SPLIT)
        {
            return;
        }

        // Protect against when we use code to spawn more entities.
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM)
        {
            return;
        }

        Optional<Integer> spawnModifier = PluginRegistry.Instance().modifiers.getSpawnModifier(event.getEntityType());
        if (!spawnModifier.isPresent())
        {
            return;
        }

        Bukkit.getServer().getPluginManager().callEvent(new AdditionalEntitySpawnGroupEvent(event));

        final double spawnModifierScaled = DifficultyScaler.scaleWithPlayers(spawnModifier.get());
        for (int i = 0; i < spawnModifierScaled; i++)
        {
            World world = event.getLocation().getWorld();
            Location spawnLocation = event.getLocation();
            Entity spawnedMob = world.spawnEntity(spawnLocation, getEntityType(event.getEntityType()));

            AdditionalEntitySpawnEvent additionalEntitySpawnEvent = new AdditionalEntitySpawnEvent(spawnedMob);
            Bukkit.getServer().getPluginManager().callEvent(additionalEntitySpawnEvent);
        }
    }


    private EntityType getEntityType(EntityType spawnedEntityType)
    {
        if (entityTypeToEntityTypes.containsKey(spawnedEntityType))
        {
            List<EntityType> candidates = entityTypeToEntityTypes.get(spawnedEntityType);
            return candidates.get(random.nextInt(candidates.size()));
        }

        return spawnedEntityType;
    }
}
