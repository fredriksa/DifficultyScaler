package fred.monstermod.runnables;

import fred.monstermod.core.Config;
import fred.monstermod.core.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class LookingAtPiglinRunnable extends BukkitRunnable {

    public LookingAtPiglinRunnable(Player player)
    {
        this.player = player;
    }

    @Override
    public void run() {
        Block block = player.getTargetBlock(null, 100);
        World world = player.getWorld();

        Collection<Entity> entities = world.getNearbyEntities(block.getLocation(), 2, 2, 2);

        for (Entity entity : entities)
        {
            if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN)
            {
                if (!RandomUtil.shouldEventOccur(Config.ZOMBIE_PIGLIN_LOOK_AT_AGGRO_CHANCE_MIN, Config.ZOMBIE_PIGLIN_LOOK_AT_AGGRO_CHANCE_MAX))
                {
                    return;
                }

               PigZombie zombie = (PigZombie) entity;
               zombie.setAngry(true);
               zombie.setAnger(100);

               if (zombie.getTarget() == null)
               {
                   zombie.setTarget(player);
               }
            }
        }
    }

    private Player player;
}
