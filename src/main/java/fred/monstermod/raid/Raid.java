package fred.monstermod.raid;

import fred.monstermod.raid.core.*;
import org.bukkit.event.Listener;

public class Raid implements Listener
{
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
