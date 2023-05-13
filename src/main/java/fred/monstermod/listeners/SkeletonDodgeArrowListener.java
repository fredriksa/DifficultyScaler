package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SkeletonDodgeArrowListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        final boolean isDamageTakenBySkeleton = event.getEntityType() == EntityType.SKELETON;
        final boolean isDamageFromProjectile = event.getDamager() instanceof Projectile;

        if (!isDamageTakenBySkeleton || !isDamageFromProjectile)
        {
            return;
        }

        Projectile projectile = (Projectile) event.getDamager();
        final boolean isProjectileArrow = (projectile.getType() == EntityType.ARROW);
        if (!isProjectileArrow)
        {
            return;
        }

        final boolean isShooterAPlayer = (projectile.getShooter() instanceof Player);
        if (!isShooterAPlayer)
        {
            return;
        }

        if (RandomUtil.shouldEventOccur(Config.SKELETON_DODGE_ARROW_MIN_CHANCE, Config.SKELETON_DODGE_ARROW_MAX_CHANCE))
        {
            event.setCancelled(true);
        }
    }
}
