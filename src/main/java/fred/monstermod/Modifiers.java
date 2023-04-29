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
            final int currentDay = (int)PluginRegistry.Instance().timeTracker.getCurrentPhase();
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

    private void multiply(List<Integer> list, float modifier)
    {
        for (int i = 0; i < list.size(); i++)
        {
            int newNum = (int) Math.ceil(list.get(i) * modifier);
            list.set(i, newNum);
        }
    }

    private void addSpawnModifiers()
    {
        List<Integer> zeroSpawnMod = Arrays.asList(0,0,0,0,0,0,0);
        List<Integer> halfDefaultSpawnMod = Arrays.asList(2,2,3,3,4,4,5);
        List<Integer> defaultSpawnMod = Arrays.asList(3,4,5,6,6,7,7);
        List<Integer> zombieSpawnMod = Arrays.asList(4,5,6,7,7,8,8);

        final float modifier = 0.5f;
        multiply(halfDefaultSpawnMod, modifier);
        multiply(defaultSpawnMod, modifier);
        multiply(zombieSpawnMod, modifier);

        spawn.put(EntityType.ZOMBIE, zombieSpawnMod);
        spawn.put(EntityType.SKELETON, defaultSpawnMod);
        spawn.put(EntityType.GHAST, halfDefaultSpawnMod);
        spawn.put(EntityType.ELDER_GUARDIAN, halfDefaultSpawnMod);
        spawn.put(EntityType.WITHER_SKELETON, defaultSpawnMod);
        spawn.put(EntityType.ZOMBIE_VILLAGER, defaultSpawnMod);
        spawn.put(EntityType.EVOKER, defaultSpawnMod);
        spawn.put(EntityType.VEX, halfDefaultSpawnMod);
        spawn.put(EntityType.VINDICATOR, halfDefaultSpawnMod);
        spawn.put(EntityType.CREEPER, defaultSpawnMod);
        spawn.put(EntityType.SPIDER, defaultSpawnMod);
        spawn.put(EntityType.GIANT, halfDefaultSpawnMod);
        spawn.put(EntityType.SLIME, halfDefaultSpawnMod);
        spawn.put(EntityType.ZOMBIFIED_PIGLIN, defaultSpawnMod);
        spawn.put(EntityType.ENDERMAN, halfDefaultSpawnMod);
        spawn.put(EntityType.CAVE_SPIDER, defaultSpawnMod);
        spawn.put(EntityType.SILVERFISH, zeroSpawnMod);
        spawn.put(EntityType.BLAZE, defaultSpawnMod);
        spawn.put(EntityType.MAGMA_CUBE, defaultSpawnMod);
        spawn.put(EntityType.ENDER_DRAGON, zeroSpawnMod);
        spawn.put(EntityType.WITHER, halfDefaultSpawnMod);
        spawn.put(EntityType.BAT, defaultSpawnMod);
        spawn.put(EntityType.WITCH, halfDefaultSpawnMod);
        spawn.put(EntityType.ENDERMITE, defaultSpawnMod);
        spawn.put(EntityType.GUARDIAN, halfDefaultSpawnMod);
        spawn.put(EntityType.SHULKER, halfDefaultSpawnMod);
        spawn.put(EntityType.POLAR_BEAR, defaultSpawnMod);
        spawn.put(EntityType.PHANTOM, defaultSpawnMod);
        spawn.put(EntityType.DROWNED, halfDefaultSpawnMod);
        spawn.put(EntityType.PILLAGER, defaultSpawnMod);
        spawn.put(EntityType.RAVAGER, defaultSpawnMod);
        spawn.put(EntityType.HOGLIN, halfDefaultSpawnMod);
        spawn.put(EntityType.PIGLIN, defaultSpawnMod);
        spawn.put(EntityType.STRIDER, defaultSpawnMod);
        spawn.put(EntityType.ZOGLIN, defaultSpawnMod);
        spawn.put(EntityType.PIGLIN_BRUTE, defaultSpawnMod);
        spawn.put(EntityType.WARDEN, zeroSpawnMod);
    }
}
