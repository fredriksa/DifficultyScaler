package fred.monstermod.listeners;

import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.listeners.TicksUtil;
import fred.monstermod.raid.core.RaidConfig;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SkeletonArrowSprayListener implements Listener {

    @EventHandler
    public void OnEntityShootBowEvent(EntityShootBowEvent event)
    {
        if (!event.getEntity().getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;

        final boolean isArrow = event.getProjectile() instanceof Arrow;
        final boolean isSkeletonShooter = event.getEntityType() == EntityType.SKELETON;
        if (!isArrow && !isSkeletonShooter) return;

        Arrow arrow = (Arrow) event.getProjectile();

        BukkitRunnable shootLaterOneSecond = new BukkitRunnable() {
            @Override
            public void run() {
                shootLater(event, arrow.getVelocity());
            }
        };

        BukkitRunnable shootLaterTwoSeconds = new BukkitRunnable() {
            @Override
            public void run() {
                shootLater(event, arrow.getVelocity());
            }
        };

        shootLaterOneSecond.runTaskLater(PluginRegistry.Instance().monsterMod, TicksUtil.secondsToTicks(1));
        shootLaterTwoSeconds.runTaskLater(PluginRegistry.Instance().monsterMod, TicksUtil.secondsToTicks(2));
    }

    private void shootLater(EntityShootBowEvent event, Vector velocity)
    {
        Location spawnLocation = event.getEntity().getEyeLocation();
        spawnLocation.add(spawnLocation.getDirection().normalize().multiply(2));

        Arrow arrow = event.getEntity().getWorld().spawn(spawnLocation, Arrow.class);
        arrow.setColor(Color.RED);
        arrow.setDamage(1);
        arrow.setKnockbackStrength(7);
        arrow.setShooter(event.getEntity());
        arrow.setVelocity(velocity);
        //arrowOne.setVelocity(arrow.getVelocity().rotateAroundY(Math.toRadians(35)));
    }

}
