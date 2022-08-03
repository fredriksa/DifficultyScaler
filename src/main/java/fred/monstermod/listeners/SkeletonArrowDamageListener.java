package fred.monstermod.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SkeletonArrowDamageListener implements Listener {

    @EventHandler
    public void onArrowDamage(EntityDamageByEntityEvent event)
    {
        final boolean isDamageTakenByMonster = event.getEntity() instanceof Monster;
        final boolean isDamageFromProjectile = event.getDamager() instanceof Projectile;

        if (!isDamageTakenByMonster || !isDamageFromProjectile)
        {
            Bukkit.getLogger().info("#2");
            return;
        }

        Projectile projectile = (Projectile) event.getDamager();
        final boolean isProjectileArrow = (projectile.getType() == EntityType.ARROW);
        if (!isProjectileArrow)
        {
            Bukkit.getLogger().info("#3");
            return;
        }

        final boolean isShooterAEntity = (projectile.getShooter() instanceof Entity);
        if (!isShooterAEntity)
        {
            Bukkit.getLogger().info("#4");
            return;
        }

        Entity shooter = (Entity) projectile.getShooter();
        final boolean isSkeletonShooter = shooter.getType() == EntityType.SKELETON;
        if (!isSkeletonShooter)
        {
            Bukkit.getLogger().info("#5");
            return;
        }

        event.setCancelled(true);
    }
}
