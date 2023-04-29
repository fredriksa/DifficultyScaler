package fred.monstermod.systems;

import fred.monstermod.core.Config;
import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.core.listeners.TicksUtil;
import fred.monstermod.general.VoteSession;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.time.Duration;
import java.time.Instant;

public class MeteorRain extends BukkitRunnable implements Listener {

    private boolean IsActive = false;
    private Instant lastActiveInstant = null;
    private Instant lastTryActivateInstant = null;

    @Override
    public void run() {
        if (IsActive)
        {
            SpawnMeteors();
        }
        else
        {
            TryActivate();
        }
    }

    private void SpawnMeteors()
    {
        for (int meteorIndex = 0; meteorIndex < Config.METEORS_PER_PLAYER_PER_BATCH; meteorIndex++)
        {
            for (Player player : Bukkit.getOnlinePlayers())
            {
                if (player.getWorld().getEnvironment() != World.Environment.NORMAL) continue;
                if (isPlayerDeeperThanXBlocksUnderground(player, Config.METEOR_STOP_SPAWNING_PLAYER_UNDERGROUND_Y_DIFF)) continue;

                SpawnMeteorAbovePlayer(player);
            }
        }
    }

    private void TryActivate()
    {
        if (IsActive) return;

        if (lastTryActivateInstant != null)
        {
            Instant now = Instant.now();
            Duration diff = Duration.between(lastTryActivateInstant, now);
            if (diff.getSeconds() < Config.METEOR_RAIN_TRY_ACTIVATE_EVERY_X_SECONDS)
            {
                // Not enough time has passed to try activate yet.
                return;
            }
        }

        if (lastActiveInstant != null)
        {
            Instant now = Instant.now();
            Duration diff = Duration.between(lastActiveInstant, now);

            if (diff.getSeconds() < Config.METEOR_RAIN_DELAY_SECONDS)
            {
                // Not enough time has passed to activate yet.
                return;
            }
        }

        if (RandomUtil.shouldEventOccur(Config.METEOR_RAIN_START_MIN_CHANCE, Config.METEOR_RAIN_START_MAX_CHANCE))
        {
            Activate();
        }

        lastTryActivateInstant = Instant.now();
    }

    private void Activate()
    {
        IsActive = true;
        MessageUtil.broadcast(ChatColor.GREEN + "The sky is opening up...");

        BukkitRunnable deactivateRunnable = new BukkitRunnable() {

            @Override
            public void run() {
                Deactivate();
            }
        };

        int deactivateTime = (int)RandomUtil.random(Config.METEOR_RAIN_MIN_LENGTH_SECONDS, Config.METEOR_RAIN_MAX_LENGTH_SECONDS);
        deactivateRunnable.runTaskLater(PluginRegistry.Instance().monsterMod, deactivateTime * 20L);
        lastActiveInstant = Instant.now();
    }

    private void Deactivate()
    {
        IsActive = false;
        MessageUtil.broadcast(ChatColor.GREEN + "The sky is closing...");
    }

    private boolean isPlayerDeeperThanXBlocksUnderground(Player player, int blocks)
    {
        Location playerLocation = player.getLocation();
        Block highestBlock = player.getWorld().getHighestBlockAt((int)playerLocation.getX(), (int)playerLocation.getZ());
        float highestBlockY = highestBlock.getY();
        return highestBlockY - playerLocation.getY() > blocks;
    }

    private void SpawnMeteorAbovePlayer(Player player)
    {
        Location highestLocation = player.getLocation();

        int xOffset = (int)RandomUtil.random(-Config.METEOR_SPAWN_OFFSET_X_Z, Config.METEOR_SPAWN_OFFSET_X_Z);
        int yOffset = (int)RandomUtil.random(-Config.METEOR_SPAWN_OFFSET_X_Z, Config.METEOR_SPAWN_OFFSET_X_Z);

        highestLocation.setX(highestLocation.getX() + xOffset);
        highestLocation.setY(highestLocation.getY() + yOffset);

        highestLocation.setY(Config.METEOR_SPAWN_Y);
        Block highestBlock = player.getWorld().getBlockAt(highestLocation);
        if (highestBlock.getType() == Material.VOID_AIR || highestBlock.getType() == Material.AIR)
        {
            FallingBlock block = highestLocation.getWorld().spawnFallingBlock(highestLocation, Material.DIRT.createBlockData());
            block.setCustomName("FALLING_METEOR_BLOCK");
            block.setCustomNameVisible(false);
            block.setDropItem(false);

            double xVel = RandomUtil.randomNegative(-Config.METEOR_VELOCITY_SPREAD, Config.METEOR_VELOCITY_SPREAD);
            double zVel = RandomUtil.randomNegative(-Config.METEOR_VELOCITY_SPREAD, Config.METEOR_VELOCITY_SPREAD);
            block.setVelocity(new Vector(xVel, Config.METEOR_VELOCITY_Y, zVel));
            return;
        }
    }

    @EventHandler
    public void onBlockLand(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof FallingBlock) {
            FallingBlock block = (FallingBlock) event.getEntity();

            if (block.getCustomName() != null && block.getCustomName().equals("FALLING_METEOR_BLOCK"))
            {
                Location explosionLocation = block.getLocation();

                World world = explosionLocation.getWorld();
                world.spawnParticle(Particle.EXPLOSION_HUGE, explosionLocation, 3);
                world.createExplosion(explosionLocation, 0, false, false);

                explodePlayers(explosionLocation);

                Block realBlock = event.getBlock();
                realBlock.setType(Material.AIR);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event)
    {
        if (!event.getBlock().hasMetadata("FALLING_METEOR_BLOCK")) return;

        event.setCancelled(true);
        event.setDropItems(false);
    }

    private void explodePlayers(Location explosionLocation)
    {
        final float xzPower = Config.METEOR_KNOCKBACK_POWER_XZ;
        final float yPower = Config.METEOR_KNOCKBACK_POWER_Y;
        final int radius = Config.METEOR_KNOCKBACK_RADIUS;

        World explosionWorld = explosionLocation.getWorld();
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!player.getWorld().equals(explosionWorld)) continue;

            Location playerLocation = player.getLocation();
            double distance = explosionLocation.distance(playerLocation);

            if (distance <= radius) {
                Vector direction = playerLocation.toVector().subtract(explosionLocation.toVector()).normalize();
                RayTraceResult result = explosionWorld.rayTraceBlocks(explosionLocation, direction, radius);

                if (result != null && result.getHitBlock() != null) {
                    continue;
                }

                double knockback = (1.0 - (distance / radius)) * xzPower;
                direction.multiply(knockback);

                Vector playerVelocity = player.getVelocity();
                playerVelocity.setX(playerVelocity.getX() + direction.getX());
                playerVelocity.setY(playerVelocity.getY() + yPower);
                playerVelocity.setZ(playerVelocity.getZ() + direction.getZ());
                player.setVelocity(playerVelocity);
                player.damage(Config.METEOR_DAMAGE);
                player.setFireTicks(TicksUtil.secondsToTicks(Config.METEOR_FIRE_SECONDS));
            }
        }
    }


    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event)
    {
        if (!event.getPlayer().getName().equals(Config.SERVER_OWNER_USERNAME)) return;

        if (event.getMessage().equals("!meteorrain"))
        {
            event.getPlayer().sendMessage("Meteorrain command registered.");
            if (!IsActive)
            {
                Activate();
            }

            event.setCancelled(true);
        }
    }
}
