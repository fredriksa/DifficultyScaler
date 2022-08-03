package fred.monstermod.general;

import fred.monstermod.core.Config;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.core.listeners.iCustomSpawnListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class UndergroundMobSpawnSpeedAdder implements iCustomSpawnListener {
    final List<EntityType> speedEffectTypes = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.SILVERFISH,
            EntityType.CREEPER,
            EntityType.BAT
    );

    @Override
    public void OnCustomSpawn(CreatureSpawnEvent event, Entity spawnedMob) {
        final String worldName = event.getLocation().getWorld().getName();
        if (!worldName.equals("world") || !shouldMobHaveSpeedMod(spawnedMob))
        {
            return;
        }

        final boolean shouldEventOccur = RandomUtil.shouldEventOccur(Config.MOB_SPEED_ADD_UNDERGROUND_MIN_CHANCE, Config.MOB_SPEED_ADD_UNDERGROUND_MAX_CHANCE);
        if (!shouldEventOccur)
        {
            return;
        }

        final Byte lightLevel = event.getLocation().getBlock().getLightFromSky();
        if (spawnedMob instanceof LivingEntity && lightLevel == 0)
        {
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2);
            potionEffect.apply((LivingEntity)spawnedMob);
        }
    }

    private boolean shouldMobHaveSpeedMod(Entity entity)
    {
        return speedEffectTypes.contains(entity.getType());
    }
}
