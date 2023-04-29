package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NearbyEntitiesCommandListener implements Listener
{
    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        if (player == null) return;
        if (!player.getName().equals(Config.SERVER_OWNER_USERNAME)) return;
        if (!event.getMessage().startsWith("!nearby")) return;

        String[] command = event.getMessage().split(" ");

        if (command.length != 4)
        {
            player.sendMessage("Invalid arguments. Usage: !nearby x y z");
            event.setCancelled(true);
            return;
        }

        int x = 0;
        int y = 0;
        int z = 0;
        try
        {
            x = Integer.parseInt(command[1]);
            y = Integer.parseInt(command[2]);
            z = Integer.parseInt(command[3]);
        } catch (Exception e)
        {
            player.sendMessage("Arguments could not be parsed to integers. Usage: !nearby x y z");
            event.setCancelled(true);
            return;
        }

        int finalY = y;
        int finalZ = z;
        int finalX = x;

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                List<Entity> entities = player.getNearbyEntities(finalX, finalY, finalZ);
                player.sendMessage("Found living entity count: " + entities.size());
                for (Entity entity : entities)
                {
                    if (entity instanceof LivingEntity)
                    {
                        player.sendMessage("Found: " + entity.getType());
                    }
                }

                event.setCancelled(true);
            }
        };

        runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 5);
        event.setCancelled(true);
    }
}
