package fred.monstermod.raid.listeners;

import fred.monstermod.raid.core.RaidConfig;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class MonsterRaidListener implements Listener {

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (!event.getEntity().getWorld().getName().equals(RaidConfig.WORLD_NAME)) return;
        if (!(event.getEntity() instanceof Monster)) return;

        event.setCancelled(true);
    }
}
