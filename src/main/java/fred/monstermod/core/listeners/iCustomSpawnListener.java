package fred.monstermod.core.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public interface iCustomSpawnListener {
    void OnCustomSpawn(CreatureSpawnEvent event, Entity spawnedMob);
}
