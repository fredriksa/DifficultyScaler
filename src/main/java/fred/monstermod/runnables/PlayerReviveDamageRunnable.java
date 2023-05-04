package fred.monstermod.runnables;

import fred.monstermod.core.Config;
import fred.monstermod.systems.ReviveSystem;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerReviveDamageRunnable extends BukkitRunnable {

    private ReviveSystem reviveSystem;

    public PlayerReviveDamageRunnable(ReviveSystem _reviveSystem)
    {
        reviveSystem = _reviveSystem;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (reviveSystem.isRevivablePlayer(player))
            {
                player.damage(Config.REVIVE_DAMAGE_PER_TICK);
                World world = player.getWorld();
                if (world == null) continue;
                world.spawnParticle(Particle.REDSTONE,
                                    player.getLocation(),
                              15, 0.5, 0.75, 0.5, 1,
                                    new Particle.DustOptions(Color.RED, 3));
            }
        }
    }
}
