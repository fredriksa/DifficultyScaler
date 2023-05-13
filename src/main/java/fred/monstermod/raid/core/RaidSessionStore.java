package fred.monstermod.raid.core;

import com.google.gson.*;
import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class RaidSessionStore {

    private HashMap<String, RaidSession> raidSessionNameToRaidSession = new HashMap<>();

    private final String persistentDataFileName = "raids.json";
    private final String nameProperty = "name";
    private final String uuidProperty = "uuid";
    private final String leaderProperty = "leader";
    private final String statusProperty = "status";
    private final String playersProperty = "players";
    private final String elapsedActiveTimeProperty = "elapsedActiveTime";

    public RaidSession getCurrentRaidSession(Player player)
    {
        for (RaidSession raidSession : raidSessionNameToRaidSession.values())
            if (raidSession.isMember(player))
                return raidSession;

        return null;
    }

    public RaidSession getRaidSession(String raidSessionName)
    {
        if (raidSessionNameToRaidSession.containsKey(raidSessionName))
            return raidSessionNameToRaidSession.get(raidSessionName);

        return null;
    }

    public RaidSession createRaidSession(String raidSessionName, Player leader)
    {
        if (getRaidSession(raidSessionName) != null) return null;

        RaidSession session = new RaidSession();
        session.setName(raidSessionName);
        session.setLeader(leader.getUniqueId());
        session.silentJoin(leader.getUniqueId());

        raidSessionNameToRaidSession.put(raidSessionName, session);
        return session;
    }

    public void remove(String raidSessionName)
    {
        if (raidSessionNameToRaidSession.containsKey(raidSessionName))
            raidSessionNameToRaidSession.remove(raidSessionName);
    }

    public void load()
    {
        Bukkit.getLogger().info("RaidSessionStore - loading raids from disk!");
        try (FileReader reader = new FileReader(persistentDataFileName))
        {
            JsonElement json = JsonParser.parseReader(reader);
            if (!json.isJsonArray()) return;

            JsonArray raids = json.getAsJsonArray();

            for (JsonElement raidJson : raids)
            {
                JsonObject raid = raidJson.getAsJsonObject();
                RaidSession session = new RaidSession();

                session.setName(raid.get(nameProperty).getAsString());
                session.setUuid(UUID.fromString(raid.get(uuidProperty).getAsString()));
                session.setLeader(UUID.fromString(raid.get(leaderProperty).getAsString()));
                session.setStatus(RaidSessionStatus.valueOf(raid.get(statusProperty).getAsString()));
                session.setElapsedActiveTime(raid.get(elapsedActiveTimeProperty).getAsLong());

                JsonArray playersArray = raid.get(playersProperty).getAsJsonArray();
                for (JsonElement element : playersArray)
                {
                    UUID playerUuid = UUID.fromString(element.getAsString());
                    UUID playerRaidUuid = PluginRegistry.Instance().raid.playerData.getRaidUuid(playerUuid);
                    if (playerRaidUuid == null || playerRaidUuid.equals(session.getUuid()))
                        session.silentJoin(playerUuid);
                }

                if (!playersArray.isEmpty())
                {
                    Bukkit.getLogger().info("Here #1");
                    // Avoid loading raids that are empty.
                    raidSessionNameToRaidSession.put(session.getName(), session);
                    session.onLoadFromFile();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().info("RaidSessionStore - failed raids from disk!");
        }
    }

    public void save()
    {
        Bukkit.getLogger().info("RaidSessionStore - saving: " + raidSessionNameToRaidSession.size() + " raids!");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray raidsArray = new JsonArray();

        for (RaidSession raidSession : raidSessionNameToRaidSession.values())
        {
            JsonObject raidJson = new JsonObject();
            raidJson.addProperty(nameProperty, raidSession.getName());
            raidJson.addProperty(uuidProperty, raidSession.getUuid().toString());
            raidJson.addProperty(statusProperty, raidSession.getStatus().toString());
            raidJson.addProperty(leaderProperty, raidSession.getLeader().toString());
            raidJson.addProperty(elapsedActiveTimeProperty, raidSession.getElapsedActiveTime());

            JsonArray playersJson = new JsonArray();
            for (UUID playerUuid : raidSession.getPlayers())
                playersJson.add(playerUuid.toString());

            raidJson.add(playersProperty, playersJson);
            raidsArray.add(raidJson);
        }

        String jsonString = gson.toJson(raidsArray);

        try (FileWriter writer = new FileWriter(persistentDataFileName))
        {
            writer.write(jsonString);
        } catch (Exception e)
        {
            e.printStackTrace();
            Bukkit.getLogger().warning("RaidSessionStore - could not save raid sessions!");
        }
    }
}
