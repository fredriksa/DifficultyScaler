package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.DifficultyScaler;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class EntityDamageListener implements Listener {

    private HashMap<EntityType, Float> entityToDamageModifier = new HashMap<>();

    final private float DEFAULT_DAMAGE_INCREASE = Config.MONSTER_DAMAGE_DEFAULT_INREASE;
    final private float NO_DAMAGE_INCREASE = 1f;

    public EntityDamageListener()
    {
        entityToDamageModifier.put(EntityType.ZOMBIE, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.SKELETON, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.GHAST, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.ELDER_GUARDIAN, NO_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.WITHER_SKELETON, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.ZOMBIE_VILLAGER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.EVOKER, NO_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.VEX, NO_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.VINDICATOR, NO_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.CREEPER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.SPIDER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.GIANT, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.SLIME, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.ZOMBIFIED_PIGLIN, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.ENDERMAN, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.CAVE_SPIDER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.SILVERFISH, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.BLAZE, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.MAGMA_CUBE, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.ENDER_DRAGON, NO_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.WITHER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.BAT, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.WITCH, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.ENDERMITE, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.GUARDIAN, NO_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.SHULKER, NO_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.POLAR_BEAR, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.PHANTOM, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.DROWNED, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.PILLAGER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.RAVAGER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.HOGLIN, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.PIGLIN, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.STRIDER, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.ZOGLIN, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.PIGLIN_BRUTE, DEFAULT_DAMAGE_INCREASE);
        entityToDamageModifier.put(EntityType.WARDEN, DEFAULT_DAMAGE_INCREASE);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Monster)) return;
        if (!(event.getEntity() instanceof Player)) return;

        Monster monster = (Monster)event.getDamager();
        final boolean shouldModifyDamage = entityToDamageModifier.containsKey(monster.getType());
        if (!shouldModifyDamage) return;

        double newDamage = entityToDamageModifier.get(monster.getType()) * event.getDamage();
        newDamage = DifficultyScaler.scaleWithPlayers(newDamage, Config.MONSTER_DAMAGE_INCREASE_PER_PLAYER);

        event.setDamage(newDamage);
    }
}
