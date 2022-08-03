package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.DifficultyScaler;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.core.listeners.TicksUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MonsterSlowDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event)
    {
        final boolean isDamagedEntityAPlayer = event.getEntity() instanceof Player;
        final boolean isDamagerZombie = event.getDamager().getType() == EntityType.ZOMBIE;

        if (!isDamagedEntityAPlayer || !isDamagerZombie)
        {
            return;
        }

        if (!RandomUtil.shouldEventOccur(Config.ZOMBIE_SLOW_MIN_CHANCE, Config.ZOMBIE_SLOW_MAX_CHANCE))
        {
            return;
        }

        final LivingEntity damager = (LivingEntity) event.getDamager();
        final boolean isFastZombie = damager.hasPotionEffect(PotionEffectType.SPEED);
        if (isFastZombie)
        {
            return;
        }

        Player player = (Player) event.getEntity();

        final int duration = TicksUtil.secondsToTicks(7.5);
        final int scaledDuration = (int)Math.round(DifficultyScaler.scaleWithPhases(duration));

        final int amplifier = 2;
        final int scaledAmplifier = (int)Math.round(DifficultyScaler.scaleWithPhases(amplifier));

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, scaledDuration, scaledAmplifier);
        player.addPotionEffect(potionEffect);
    }
}
