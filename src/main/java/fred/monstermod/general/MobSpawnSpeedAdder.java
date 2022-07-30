package fred.monstermod.general;

import fred.monstermod.core.listeners.iCustomSpawnListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class MobSpawnSpeedAdder implements iCustomSpawnListener {
    final List<EntityType> speedEffectTypes = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.SILVERFISH,
            EntityType.BAT
    );

    @Override
    public void OnCustomSpawn(CreatureSpawnEvent event, Entity spawnedMob) {
        final String worldName = event.getLocation().getWorld().getName();
        if (!worldName.equals("world") || !shouldMobHaveSpeedMod(spawnedMob))
        {
            return;
        }

        final Byte lightLevel = event.getLocation().getBlock().getLightFromSky();
        if (spawnedMob instanceof LivingEntity && lightLevel > 0)
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
