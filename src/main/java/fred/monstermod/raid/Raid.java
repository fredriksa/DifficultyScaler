package fred.monstermod.raid;

import fred.monstermod.raid.core.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Raid implements Listener
{
    // TODO: Each player must be tagged with a UUID for a raid, as otherwise the player
    // could wait foer server restart to safely leave...

    // Check if player spawns in world, on e.g. a bed, prevent that
    // Make player not respawn in world

    public RaidSessionStore sessions = new RaidSessionStore();
    public PlayerRaidDataStore playerData = new PlayerRaidDataStore();

    public RequestRaidStartHandler raidStartHandler = new RequestRaidStartHandler(this);
    public RaidJoinHandler raidJoinHandler = new RaidJoinHandler(this);
    public RaidLeaveHandler raidLeaveHandler = new RaidLeaveHandler(this);

    public void onEnable()
    {
        playerData.load();
        sessions.load();
    }

    public void onDisable()
    {
        playerData.save();
        sessions.save();
    }
}
