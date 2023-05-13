package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.events.AdditionalEntitySpawnEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class UndergroundMobSpawnSpeedAdderListener implements Listener {
    final List<EntityType> speedEffectTypes = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.SILVERFISH,
            EntityType.CREEPER,
            EntityType.BAT
    );

    @EventHandler
    public void onAdditionalEntitySpawn(AdditionalEntitySpawnEvent event) {
        final Entity spawnedEntity = event.getEntity();
        final String worldName = spawnedEntity.getWorld().getName();
        if (!Config.WORLDS_TO_MODIFY.contains(worldName) || !shouldMobHaveSpeedMod(spawnedEntity))
        {
            return;
        }

        final boolean shouldEventOccur = RandomUtil.shouldEventOccur(Config.MOB_SPEED_ADD_UNDERGROUND_MIN_CHANCE, Config.MOB_SPEED_ADD_UNDERGROUND_MAX_CHANCE);
        if (!shouldEventOccur)
        {
            return;
        }

        final Byte lightLevel = spawnedEntity.getLocation().getBlock().getLightFromSky();
        if (spawnedEntity instanceof LivingEntity && lightLevel == 0)
        {
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2);
            potionEffect.apply((LivingEntity)spawnedEntity);
        }
    }

    private boolean shouldMobHaveSpeedMod(Entity entity)
    {
        return speedEffectTypes.contains(entity.getType());
    }
}
