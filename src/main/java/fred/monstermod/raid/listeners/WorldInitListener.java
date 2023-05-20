package fred.monstermod.raid.listeners;

import fred.monstermod.raid.core.RaidConfig;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldInitListener implements Listener {

    @EventHandler
    public void onWorld(WorldLoadEvent event)
    {
        World world = event.getWorld();
        if (!world.getName().equals(RaidConfig.WORLD_NAME)) return;

        world.setTime(13000);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    }
}
