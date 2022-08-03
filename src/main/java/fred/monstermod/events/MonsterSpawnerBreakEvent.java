package fred.monstermod.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MonsterSpawnerBreakEvent extends Event {

    // REQUIRED BY SPIGOT
    private static final HandlerList HANDLERS = new HandlerList();

    public MonsterSpawnerBreakEvent(Player player, Block brokenBlock)
    {
        this.player = player;
        this.brokenBlock = brokenBlock;
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

    public Player getBreaker()
    {
        return player;
    }

    public Block getBrokenSpawner()
    {
        return brokenBlock;
    }

    private Player player;
    private Block brokenBlock;
}
