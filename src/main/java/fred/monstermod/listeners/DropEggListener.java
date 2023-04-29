package fred.monstermod.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class DropEggListener implements Listener {

    private class EggDrop
    {
        public EggDrop(Material _egg, int _dropChancePercentage)
        {
            egg = _egg;
            dropChancePercentage = _dropChancePercentage;
        }

        public final Material egg;
        public final int dropChancePercentage;
    }

    final private HashMap<EntityType, Material> entityToEggDrop = new HashMap<>();

    public DropEggListener()
    {
       // entityToEggDrop.put(EntityType.ZOMBIE, new EggDrop(Material.ZOMBIE_SPAWN_EGG, );
        entityToEggDrop.put(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG);
    }

    @EventHandler
    public void OnEntityDeath(EntityDeathEvent event)
    {
        Player killer = (Player) event.getEntity().getKiller();
        if (killer == null) return;
    }
}
