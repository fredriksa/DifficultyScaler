package fred.monstermod.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperExplosionPreventionListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        if (event.getEntityType() == EntityType.CREEPER)
        {
            final boolean isInOpenWorld = event.getLocation().getBlock().getLightFromSky() > 0;
            if (isInOpenWorld)
            {
                event.blockList().clear();
            }
        }
    }
}
