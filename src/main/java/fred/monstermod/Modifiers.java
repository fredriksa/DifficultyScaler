package fred.monstermod;

import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.util.*;

public class Modifiers {

    private Map<EntityType, List<Integer>> spawn = new HashMap<EntityType, List<Integer>>();

    public Modifiers()
    {
        addSpawnModifiers();
        validateSpawnModifiers();
    }

    public Optional<Integer> getSpawnModifier(EntityType type)
    {
        if (spawn.containsKey(type))
        {
            final int currentDay = (int)PluginRegistry.Instance().timeTracker.getCurrentDay();
            return Optional.of(spawn.get(type).get(currentDay - 1));
        }

        return Optional.empty();
    }

    private void validateSpawnModifiers()
    {
        for (Map.Entry<EntityType, List<Integer>> entry : spawn.entrySet())
        {
            final int size = entry.getValue().size();
            if (size != 7)
            {
                final String message = "Invalid spawn modifier count for: " + entry.getKey().toString() + " with nr of mods: " + size;
                Bukkit.getLogger().info(message);
            }
        }
    }

    private void addSpawnModifiers()
    {
        List<Integer> defaultSpawnMod = Arrays.asList(3,4,5,6,6,7,7);
        List<Integer> halfDefaultSpawnMod = Arrays.asList(2,2,3,3,4,4,5);

        spawn.put(EntityType.ZOMBIE, defaultSpawnMod);
        spawn.put(EntityType.SKELETON, defaultSpawnMod);
        spawn.put(EntityType.GHAST, halfDefaultSpawnMod);
        spawn.put(EntityType.ELDER_GUARDIAN, halfDefaultSpawnMod);
        spawn.put(EntityType.WITHER_SKELETON, defaultSpawnMod);
        spawn.put(EntityType.ZOMBIE_VILLAGER, defaultSpawnMod);
        spawn.put(EntityType.EVOKER, defaultSpawnMod);
        spawn.put(EntityType.VEX, halfDefaultSpawnMod);
        spawn.put(EntityType.VINDICATOR, halfDefaultSpawnMod);
        spawn.put(EntityType.CREEPER, halfDefaultSpawnMod);
        spawn.put(EntityType.SPIDER, defaultSpawnMod);
        spawn.put(EntityType.GIANT, halfDefaultSpawnMod);
        spawn.put(EntityType.SLIME, halfDefaultSpawnMod);
        spawn.put(EntityType.ZOMBIFIED_PIGLIN, defaultSpawnMod);
        spawn.put(EntityType.ENDERMAN, halfDefaultSpawnMod);
        spawn.put(EntityType.CAVE_SPIDER, defaultSpawnMod);
        spawn.put(EntityType.SILVERFISH, halfDefaultSpawnMod);
        spawn.put(EntityType.BLAZE, defaultSpawnMod);
        spawn.put(EntityType.MAGMA_CUBE, halfDefaultSpawnMod);
        spawn.put(EntityType.ENDER_DRAGON, Arrays.asList(0,0,0,0,0,0,0));
        spawn.put(EntityType.WITHER, halfDefaultSpawnMod);
        spawn.put(EntityType.BAT, defaultSpawnMod);
        spawn.put(EntityType.WITCH, halfDefaultSpawnMod);
        spawn.put(EntityType.ENDERMITE, defaultSpawnMod);
        spawn.put(EntityType.GUARDIAN, halfDefaultSpawnMod);
        spawn.put(EntityType.SHULKER, halfDefaultSpawnMod);
        spawn.put(EntityType.POLAR_BEAR, defaultSpawnMod);
        spawn.put(EntityType.PHANTOM, defaultSpawnMod);
        spawn.put(EntityType.DROWNED, defaultSpawnMod);
        spawn.put(EntityType.PILLAGER, defaultSpawnMod);
        spawn.put(EntityType.RAVAGER, defaultSpawnMod);
        spawn.put(EntityType.HOGLIN, halfDefaultSpawnMod);
        spawn.put(EntityType.PIGLIN, defaultSpawnMod);
        spawn.put(EntityType.STRIDER, defaultSpawnMod);
        spawn.put(EntityType.ZOGLIN, defaultSpawnMod);
        spawn.put(EntityType.PIGLIN_BRUTE, halfDefaultSpawnMod);
        spawn.put(EntityType.WARDEN, halfDefaultSpawnMod);
    }
}
