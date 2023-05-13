package fred.monstermod.raid.core;

import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

public class RaidSession {
    private String name;
    private UUID uuid;
    private RaidSessionStatus status = RaidSessionStatus.PREPARING;

    // All members in the raid session - string is uuid
    private HashSet<UUID> players = new HashSet<>();

    // Name of leader of the raid session
    private UUID leader;

    RaidSession()
    {
        uuid = UUID.randomUUID();
    }

    public String getName() { return name; }
    public UUID getUuid() { return uuid; }
    public RaidSessionStatus getStatus() { return status; }
    public UUID getLeader() { return leader; }
    public HashSet<UUID> getPlayers() { return players; }

    public void setLeader(UUID newLeader)  { leader = newLeader; }
    public void setName(String newName) { name = newName; }
    public void setUuid(UUID newUuid) { uuid = newUuid; }
    public void setStatus(RaidSessionStatus newStatus) { status = newStatus; }

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

    public void activate()
    {
        status = RaidSessionStatus.ACTIVE;
    }

    public boolean isLeader(Player player)
    {
        return leader.equals(player.getUniqueId());
    }

    public void silentJoin(Player player)
    {
        players.add(player.getUniqueId());
        PluginRegistry.Instance().raid.playerData.storeRaidUuid(player.getUniqueId(), uuid);
    }

    public void join(Player player)
    {
        silentJoin(player);
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
}
