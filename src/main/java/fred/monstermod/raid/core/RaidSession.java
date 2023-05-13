package fred.monstermod.raid.core;

import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.listeners.TicksUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class RaidSession {
    private String name;
    private UUID uuid;
    private RaidSessionStatus status = RaidSessionStatus.PREPARING;
    private long elapsedActiveTime = 0;

    // All members in the raid session - string is uuid
    private HashSet<UUID> players = new HashSet<>();

    // Name of leader of the raid session
    private UUID leader;


    private RaidTrackerRunnable raidTracker;
    private SessionEndHandler endHandler = new SessionEndHandler(this);

    RaidSession()
    {
        uuid = UUID.randomUUID();
    }

    public String getName() { return name; }
    public UUID getUuid() { return uuid; }
    public RaidSessionStatus getStatus() { return status; }
    public UUID getLeader() { return leader; }
    public HashSet<UUID> getPlayers() { return players; }
    public long getElapsedActiveTime() { return elapsedActiveTime; }
    public RaidTrackerRunnable getRaidTracker() { return raidTracker; }

    public void setLeader(UUID newLeader)  { leader = newLeader; }
    public void setName(String newName) { name = newName; }
    public void setUuid(UUID newUuid) { uuid = newUuid; }
    public void setStatus(RaidSessionStatus newStatus) { status = newStatus; }
    public void setElapsedActiveTime(long newElapsedActiveTime) { elapsedActiveTime = newElapsedActiveTime; }
    public void setRaidTracker(RaidTrackerRunnable newRaidTrackerRunnable) { raidTracker = newRaidTrackerRunnable; }

    public void destroy()
    {
        raidTracker.cancel();
        PluginRegistry.Instance().raid.sessions.remove(name);
    }

    public void onLoadFromFile()
    {
        // Run activation slightly later than reading from disk
        // Ask the game world for raids are not loaded at this point in time.
        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run() {
                if (getStatus().equals(RaidSessionStatus.ACTIVE))
                {
                    activate(true);
                }
            }
        };

        runnable.runTaskLater(PluginRegistry.Instance().monsterMod, TicksUtil.secondsToTicks(5));
    }

    public void broadcast(String message)
    {
        for (UUID playerUuid : players)
        {
            Player player = Bukkit.getServer().getPlayer(playerUuid);
            if (player == null) continue;
            player.sendMessage(message);
        }
    }

    public boolean isMember(Player player)
    {
        return players.contains(player.getUniqueId());
    }

    public void activate(boolean loadedFromFile)
    {
        World raidWorld = Bukkit.getWorld(RaidConfig.WORLD_NAME);
        if (raidWorld == null) return;

        status = RaidSessionStatus.ACTIVE;

        RaidTrackerRunnable raidTracker = new RaidTrackerRunnable(this);
        raidTracker.runTaskTimer(PluginRegistry.Instance().monsterMod, 0, TicksUtil.secondsToTicks(1));
        this.setRaidTracker(raidTracker);
    }

    public boolean isLeader(Player player)
    {
        return leader.equals(player.getUniqueId());
    }

    public void silentJoin(UUID playerUuid)
    {
        players.add(playerUuid);
        PluginRegistry.Instance().raid.playerData.storeRaidUuid(playerUuid, uuid);
    }

    public void join(Player player)
    {
        silentJoin(player.getUniqueId());
        broadcast(ChatColor.GREEN + player.getName() + " joined the raid session.");
    }

    public void leave(Player player)
    {
        if (player == null) return;

        PluginRegistry.Instance().raid.playerData.removeRaidUuid(player.getUniqueId());
        players.remove(player.getUniqueId());

        final boolean shouldFindNewLeader = leader.equals(player.getName()) && !players.isEmpty();
        if (shouldFindNewLeader)
        {
            Random random = new Random();
            leader = UUID.fromString((String) players.toArray()[random.nextInt(players.size())]);

            Player newLeader = Bukkit.getServer().getPlayer(leader);
            if (newLeader == null) return;

            newLeader.sendMessage(ChatColor.GREEN + "You have been promoted to the leader of raid session '" +  name + "'");
        }
    }

    public void end()
    {
        endHandler.onEnd();
    }
}
