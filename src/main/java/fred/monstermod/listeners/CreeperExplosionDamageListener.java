package fred.monstermod.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CreeperExplosionDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event)
    {
        final boolean isDamageTakenByMonster = event.getEntity() instanceof Monster;
        final boolean isDamageDealtByCreeper = event.getDamager().getType() == EntityType.CREEPER;

        if (isDamageTakenByMonster && isDamageDealtByCreeper)
        {
            event.setCancelled(true);
        }
    }
}
