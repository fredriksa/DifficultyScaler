package fred.monstermod.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SkeletonArrowDamageListener implements Listener {

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event)
    {
        final boolean isDamageDealtBySkeleton = event.getEntityType() == EntityType.SKELETON;
        final boolean isDamageFromProjectile = event.getDamager() instanceof Projectile;

        if (!isDamageDealtBySkeleton || !isDamageFromProjectile)
        {
            return;
        }

        Projectile projectile = (Projectile) event.getDamager();
        final boolean isProjectileArrow = (projectile.getType() == EntityType.ARROW);
        if (!isProjectileArrow)
        {
            return;
        }

        final boolean isShooterAEntity = (projectile.getShooter() instanceof Entity);
        if (!isShooterAEntity)
        {
            return;
        }

        Entity shooter = (Entity) projectile.getShooter();
        final boolean isSkeletonShooter = shooter.getType() == EntityType.SKELETON;
        if (!isSkeletonShooter)
        {
            return;
        }

        event.setCancelled(true);
    }
}
