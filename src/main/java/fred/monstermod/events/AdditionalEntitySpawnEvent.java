package fred.monstermod.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AdditionalEntitySpawnEvent extends Event {

    // REQUIRED BY SPIGOT
    private static final HandlerList HANDLERS = new HandlerList();

    public AdditionalEntitySpawnEvent(Entity entity)
    {
        this.entity = entity;
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

    public Entity getEntity()
    {
        return entity;
    }

    private Entity entity;
}
