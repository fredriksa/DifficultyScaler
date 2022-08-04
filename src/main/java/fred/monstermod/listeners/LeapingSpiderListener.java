package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.mobs.LeapingSpider;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
public class LeapingSpiderListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        if (!LeapingSpider.canBeLeapingSpider(event.getEntityType()))
        {
            return;
        }

        final boolean isInNaturallyLitArea = event.getEntity().getLocation().getBlock().getLightFromSky() > 0;
        if (isInNaturallyLitArea)
        {
            return;
        }

        if (!RandomUtil.shouldEventOccur(Config.LEAPING_SPIDER_MIN_CHANCE, Config.LEAPING_SPIDER_MAX_CHANCE))
        {
            return;
        }

        Spider spider = (Spider) event.getEntity();
        LeapingSpider.morphIntoLeapingSpider(spider);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (!LeapingSpider.canBeLeapingSpider(event.getEntityType()) || !LeapingSpider.isLeapingSpider(event.getEntity()))
        {
            return;
        }

        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))
        {
            event.setCancelled(true);
        }
    }


}
