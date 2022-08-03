package fred.monstermod.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DrownedTridentDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageEvent(EntityDamageByEntityEvent event)
    {
        final boolean isDamageTakenByMonster = event.getEntity() instanceof Monster;
        final boolean isDamageFromProjectile = event.getDamager() instanceof Projectile;

        if (!isDamageTakenByMonster || !isDamageFromProjectile)
        {
            return;
        }

        Projectile projectile = (Projectile) event.getDamager();
        final boolean isProjectileTrident = (projectile.getType() == EntityType.TRIDENT);
        if (!isProjectileTrident)
        {
            return;
        }

        final boolean isShooterAEntity = (projectile.getShooter() instanceof Entity);
        if (!isShooterAEntity)
        {
            return;
        }

        Entity shooter = (Entity) projectile.getShooter();
        final boolean isDrownedThrower = shooter.getType() == EntityType.DROWNED;
        if (!isDrownedThrower)
        {
            return;
        }

        event.setCancelled(true);
    }
}
