package fred.monstermod.listeners;

import fred.monstermod.general.HordeSpawner;
import fred.monstermod.general.MobSpawnSpeedAdder;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.listeners.iCustomSpawnEventListener;
import fred.monstermod.core.listeners.iCustomSpawnListener;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpawnEventListener implements Listener {

    List<iCustomSpawnListener> spawnListeners = new ArrayList<iCustomSpawnListener>();
    List<iCustomSpawnEventListener> spawnEventListeners = new ArrayList<iCustomSpawnEventListener>();

    public SpawnEventListener()
    {
        spawnListeners.add(new MobSpawnSpeedAdder());
        spawnEventListeners.add(new HordeSpawner());
    }

    @EventHandler
    public void onEntitySpawnEvent(CreatureSpawnEvent event)
    {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM)
        {
            return;
        }

        Optional<Integer> spawnModifier = PluginRegistry.Instance().modifiers.getSpawnModifier(event.getEntityType());
        if (!spawnModifier.isPresent())
        {
            return;
        }

        for (iCustomSpawnEventListener eventListener : spawnEventListeners)
        {
            eventListener.OnCustomSpawn(event);
        }

        for (int i = 0; i < spawnModifier.get(); i++)
        {
            World world = event.getLocation().getWorld();
            Location spawnLocation = event.getLocation();
            Entity spawnedMob = world.spawnEntity(spawnLocation, event.getEntityType());

            for (iCustomSpawnListener spawnListener : spawnListeners)
            {
                spawnListener.OnCustomSpawn(event, spawnedMob);
            }
        }
    }
}
