package fred.monstermod.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class PhantomSpawnPreventListener implements Listener {

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event)
    {
        if (event.getEntityType() == EntityType.PHANTOM)
        {
            event.setCancelled(true);
        }
    }
}
