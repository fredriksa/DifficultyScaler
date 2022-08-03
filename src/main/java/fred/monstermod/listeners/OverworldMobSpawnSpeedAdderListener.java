package fred.monstermod.listeners;

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

public class OverworldMobSpawnSpeedAdderListener implements Listener {
    final List<EntityType> speedEffectTypes = Arrays.asList(
            EntityType.ZOMBIE,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.SILVERFISH,
            EntityType.BAT
    );

    @EventHandler
    public void onAdditionalEntitySpawn(AdditionalEntitySpawnEvent event) {
        final Entity spawnedEntity = event.getEntity();
        final String worldName = spawnedEntity.getLocation().getWorld().getName();
        if (!worldName.equals("world") || !shouldMobHaveSpeedMod(spawnedEntity))
        {
            return;
        }

        final Byte lightLevel = spawnedEntity.getLocation().getBlock().getLightFromSky();
        if (spawnedEntity instanceof LivingEntity && lightLevel > 0)
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
