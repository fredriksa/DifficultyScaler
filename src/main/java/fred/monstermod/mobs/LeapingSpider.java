package fred.monstermod.mobs;

import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.core.listeners.TicksUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class LeapingSpider {

    public static final String LEAPING_SPIDER_METADATA_KEY = "leapingSpider";

    private static List<EntityType> spiderTypes = Arrays.asList(EntityType.SPIDER, EntityType.CAVE_SPIDER);

    private static final int spiderHealth = 50;
    private static int MINIMUM_JUMP_DISTANCE = 7;

    public static void morphIntoLeapingSpider(Spider spider)
    {
        spider.setMetadata(LEAPING_SPIDER_METADATA_KEY, new FixedMetadataValue(PluginRegistry.Instance().monsterMod, true));

        Attributable spiderAttributes = spider;
        AttributeInstance healthAttribute =  spiderAttributes.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        healthAttribute.setBaseValue(spiderHealth);
        spider.setHealth(spiderHealth);

        LeapingSpiderRunnable leapingSpiderLogic = new LeapingSpider.LeapingSpiderRunnable(spider);

        final int jumpFrequencyInTicks = TicksUtil.secondsToTicks(RandomUtil.random(Config.LEAPING_SPIDER_JUMP_EVERY_SECOND_MIN, Config.LEAPING_SPIDER_JUMP_EVERY_SECOND_MAX));
        leapingSpiderLogic.runTaskTimer(PluginRegistry.Instance().monsterMod, 0, jumpFrequencyInTicks);
    }

    public static void spawn(Location location)
    {
        Spider spider = location.getWorld().spawn(location, Spider.class);
        morphIntoLeapingSpider(spider);
    }

    public static boolean canBeLeapingSpider(EntityType type)
    {
        return spiderTypes.contains(type);
    }

    public static boolean isLeapingSpider(Entity entity)
    {
        return entity.getMetadata(LeapingSpider.LEAPING_SPIDER_METADATA_KEY).size() > 0;
    }

    private static class LeapingSpiderRunnable extends BukkitRunnable
    {

        LeapingSpiderRunnable(Spider spider)
        {
            this.spider = spider;
        }

        @Override
        public void run() {
            if (spider.isDead())
            {
                this.cancel();
                return;
            }

            if (spider.getTarget() != null)
            {
                tryGetPlayerTarget();
            }

            LivingEntity target = spider.getTarget();
            if (target != null)
            {
                if (shouldJumpTo(target))
                {
                    jumpTo(target);
                }
            }
        }

        private void tryGetPlayerTarget()
        {
            for (Entity entity : spider.getNearbyEntities(10, 10, 10))
            {
                if (entity instanceof Player)
                {
                    Player player = (Player) entity;
                    spider.setTarget(player);
                }
            }
        }

        private boolean shouldJumpTo(LivingEntity target)
        {
            final boolean isMovingOrFallingDown = spider.getVelocity().getY() > 0;
            if (isMovingOrFallingDown)
            {
                return false;
            }

            return target.getLocation().distanceSquared(spider.getLocation()) > MINIMUM_JUMP_DISTANCE;
        }

        private void jumpTo(LivingEntity target)
        {
            spider.getWorld().playSound(spider.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.05f, 5);
            spider.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, spider.getLocation(), 10);

            Location targetLocation = target.getLocation().add(0,2,0);
            targetLocation = targetLocation.subtract(spider.getLocation());
            Vector locationVector = targetLocation.toVector().multiply(0.275);
            spider.setVelocity(locationVector);
        }

        private Spider spider;
    }
}
