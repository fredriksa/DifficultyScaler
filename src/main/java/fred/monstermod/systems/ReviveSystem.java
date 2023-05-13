package fred.monstermod.systems;

import fred.monstermod.core.*;
import fred.monstermod.core.listeners.TicksUtil;
import fred.monstermod.general.PlayerReviveTracker;
import fred.monstermod.runnables.PlayerReviveDamageRunnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ReviveSystem implements Listener {

    private PlayerReviveTracker reviveTracker = new PlayerReviveTracker();

    private NamespacedKey revivableKey = new NamespacedKey(PluginRegistry.Instance().monsterMod, "revivable");

    private final String LAST_PLAYER_TARGET_TIMESTAMP_METADATA_KEY = "LAST_PLAYER_TARGET_TIMESTAMP_METADATA_KEY";
    private final String LAST_PLAYER_TARGET_METADATA_KEY = "LAST_PLAYER_TARGET_METADATA_KEY";
    private final long PICK_NEW_PLAYER_TARGET_COOLDOWN = TicksUtil.secondsToTicks(5);

    private final float DEFAULT_WALK_SPEED = 0.2f;
    private PlayerReviveDamageRunnable damageRunnable;

    public ReviveSystem()
    {
        damageRunnable = new PlayerReviveDamageRunnable(this);
        damageRunnable.runTaskTimer(PluginRegistry.Instance().monsterMod, 0, TicksUtil.secondsToTicks(Config.REVIVE_DAMAGE_EVERY_X_SECONDS));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        Player player = (Player) event.getEntity();

        if (isRevivablePlayer(player))
        {
            event.setCancelled(true);
            return;
        }

        final boolean playerWillDie = player.getHealth() - event.getFinalDamage() <= 0;
        if (!playerWillDie) return;

        final boolean readyToBeRevived = reviveTracker.readyToBeRevived(player);
        final boolean anotherPlayerNearby = EntityUtil.playersWithinDistance(player, Config.REVIVE_PLAYER_NEARBY_DISTANCE).size() > 0;

        if (!readyToBeRevived)
        {
            final String timeLeftStr = TimeUtil.secondsToDateTimeString(reviveTracker.timeLeftUntilRevivable(player));
            player.sendMessage(ChatColor.RED + "Second life was still on cooldown when you died. You had " + timeLeftStr + " cooldown left.");
            return;
        }

        if (!anotherPlayerNearby)
        {
            player.sendMessage(ChatColor.RED + "No player was close enough to trigger second life.");
            return;
        }

        player.sendMessage(ChatColor.GREEN + "You have been downed. Another player can still save you.");

        event.setCancelled(true);
        player.setHealth(20);

        setRevivablePlayer(player, true);
        removePlayerAsMonsterTarget(player);
    }

    @EventHandler
    public void onChatCommand(AsyncPlayerChatEvent event)
    {
        if (event.getMessage().equals("!die"))
        {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    event.getPlayer().setHealth(0);
                }
            };

            runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 1);
            return;
        }

        if (!event.getPlayer().getName().equals(Config.SERVER_OWNER_USERNAME)) return;
        if (!event.getMessage().startsWith("!reset revive")) return;

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                revivePlayer(event.getPlayer());
                reviveTracker.untrackRevive(event.getPlayer());
            }
        };

        runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 1);
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event)
    {
        if (!isRevivablePlayer(event.getPlayer())) return;

        boolean isFlying = event.getPlayer().getFallDistance() > 0;
        if (isFlying) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onTargetEvent(EntityTargetEvent event)
    {
        if (!(event.getTarget() instanceof Player)) return;

        Player player = (Player) event.getTarget();
        if (player == null || !isRevivablePlayer(player)) return;

        event.setTarget(null);

        Entity eventEntity = event.getEntity();
        World entityWorld = eventEntity.getWorld();
        if (entityWorld == null) return;

        if (eventEntity.hasMetadata(LAST_PLAYER_TARGET_TIMESTAMP_METADATA_KEY))
        {
            List<MetadataValue> metadataValues = eventEntity.getMetadata(LAST_PLAYER_TARGET_TIMESTAMP_METADATA_KEY);
            if (metadataValues.isEmpty()) return;

            MetadataValue lastPlayerTargetTimeMetaValue = metadataValues.get(0);
            long lastPlayerTargetTime = lastPlayerTargetTimeMetaValue.asLong();
            if (entityWorld.getGameTime() - lastPlayerTargetTime < PICK_NEW_PLAYER_TARGET_COOLDOWN)
            {
                // Try to get last target
                List<MetadataValue> targetNameMetaDataValue = eventEntity.getMetadata(LAST_PLAYER_TARGET_METADATA_KEY);
                if (!targetNameMetaDataValue.isEmpty())
                {
                    String oldTargetName = targetNameMetaDataValue.get(0).asString();
                    Player oldTarget = Bukkit.getPlayer(oldTargetName);
                    event.setTarget(oldTarget);
                    return;
                }
            }
        }

        List<Player> candidatePlayers = EntityUtil.playersWithinDistance(player, 50);
        if (candidatePlayers.isEmpty()) return;

        EntityUtil.sortEntitiesByDistance(player.getLocation(), candidatePlayers);
        Player newTarget = candidatePlayers.get(0);
        eventEntity.setMetadata(LAST_PLAYER_TARGET_TIMESTAMP_METADATA_KEY, new FixedMetadataValue(PluginRegistry.Instance().monsterMod, entityWorld.getGameTime()));
        eventEntity.setMetadata(LAST_PLAYER_TARGET_METADATA_KEY, new FixedMetadataValue(PluginRegistry.Instance().monsterMod, newTarget.getName()));
        event.setTarget(newTarget);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
        if (!(event.getRightClicked() instanceof Player)) return;

        Player clickedPlayer = (Player) event.getRightClicked();
        if (isRevivablePlayer(clickedPlayer))
        {
            revivePlayer(clickedPlayer);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        if (!isRevivablePlayer(event.getEntity())) return;

        // When the player bleeds out and dies run it through the revive player system
        revivePlayer(event.getEntity());
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event)
    {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        Player player = (Player) event.getEntity();
        if (player == null || !isRevivablePlayer(player)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if (!isRevivablePlayer(player)) return;
        revivePlayer(player);
        player.setHealth(0);
    }

    public boolean isRevivablePlayer(Player player)
    {
        if (player == null) return false;
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (!data.getKeys().contains(revivableKey)) return false;
        return data.get(revivableKey, PersistentDataType.INTEGER) == 1;
    }

    public void revivePlayer(Player player)
    {
        setRevivablePlayer(player, false);
        reviveTracker.trackRevive(player);

        player.setWalkSpeed(DEFAULT_WALK_SPEED);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);

        player.getPlayer().sendMessage(ChatColor.GREEN + "You have been revived. Next revive ready in " + TimeUtil.secondsToDateTimeString(Config.REVIVE_COOLDOWN_SECONDS) + ".");
    }

    public void setRevivablePlayer(Player player, boolean revivable)
    {
        if (player == null) return;

        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(revivableKey, PersistentDataType.INTEGER, revivable ? 1 : 0);
    }

    private void removePlayerAsMonsterTarget(Player player)
    {
        final int SEARCH_AREA = 50;
        List<Entity> entities = player.getNearbyEntities(SEARCH_AREA, SEARCH_AREA, SEARCH_AREA);
        for (Entity entity : entities)
        {
            if (!(entity instanceof Monster)) continue;

            Monster monster = (Monster) entity;
            if (monster == null) continue;

            LivingEntity monsterTarget = monster.getTarget();
            if (monsterTarget == null || !(monsterTarget instanceof Player)) continue;

            Player playerTarget = (Player) monster.getTarget();
            if (playerTarget == null) continue;

            if (playerTarget.equals(player))
            {
                monster.setTarget(null);
            }
        }
    }
}
