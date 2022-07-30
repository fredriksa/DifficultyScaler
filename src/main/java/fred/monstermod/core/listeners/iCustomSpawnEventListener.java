package fred.monstermod.core.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public interface iCustomSpawnEventListener {

    // Called once per natural zombie spawn, that then has extra mob spawns by spawn event listener
    void OnCustomSpawn(CreatureSpawnEvent event);

}
