package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.DifficultyScaler;
import fred.monstermod.core.RandomUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SuperChargedCreeperListener implements Listener {

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event)
    {
        if (event.getEntityType() == EntityType.CREEPER)
        {
            Creeper creeper = (Creeper) event.getEntity();

            final Block creeperBlock = creeper.getLocation().getBlock();
            final boolean isUnderground = creeperBlock.getLightFromSky() == 0;
            if (!isUnderground)
            {
                return;
            }

            if (creeperBlock.getY() > Config.SUPERCHAGED_CREEPER_MAX_Y_LEVEL)
            {
                return;
            }

            if (RandomUtil.shouldEventOccur(Config.SUPERCHARGED_CREEPER_MIN_CHANCE, Config.SUPERCHAGED_CREEPER_MAX_CHANCE))
            {
                creeper.setPowered(true);
                final double newExplosionRadius = DifficultyScaler.scaleWithPhases(creeper.getExplosionRadius());
                creeper.setExplosionRadius((int) newExplosionRadius);
            }
        }
    }
}
