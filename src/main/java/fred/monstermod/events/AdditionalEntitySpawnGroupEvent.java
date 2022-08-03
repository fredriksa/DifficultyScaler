package fred.monstermod.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class AdditionalEntitySpawnGroupEvent extends Event {

    // REQUIRED BY SPIGOT
    private static final HandlerList HANDLERS = new HandlerList();

    public AdditionalEntitySpawnGroupEvent(CreatureSpawnEvent creatureSpawnEvent)
    {
        this.creatureSpawnEvent = creatureSpawnEvent;
    }

    // REQUIRED BY SPIGOT
    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    // REQUIRED BY SPIGOT
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public CreatureSpawnEvent getCreatureSpawnEvent()
    {
        return creatureSpawnEvent;
    }

    private CreatureSpawnEvent creatureSpawnEvent;
}
