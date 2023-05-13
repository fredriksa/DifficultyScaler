package fred.monstermod.raid.core;

import com.google.gson.*;
import org.bukkit.Bukkit;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRaidDataStore
{
    public class ExitData
    {
        public ExitData(String worldName, double x, double y, double z)
        {
            this.worldName = worldName;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String worldName;
        public double x;
        public double y;
        public double z;
    };

    private HashMap<UUID, UUID> playerUuidToRaidUuid = new HashMap<>();
    private HashMap<UUID, ExitData> playerUuidToExitData = new HashMap<>();

    private final String persistentDataFileName = "playerraiddatastore.json";

    public final String raidProperty = "raids";
    public final String exitProperty = "exits";

    public final String exitWorldNameProperty = "worldName";
    public final String exitXProperty = "exitX";
    public final String exitYProperty = "exitY";
    public final String exitZProperty = "exitZ";

    public UUID getRaidUuid(UUID playerUuid)
    {
        if (playerUuidToRaidUuid.containsKey(playerUuid))
            return playerUuidToRaidUuid.get(playerUuid);

        return null;
    }

    public void storeExit(UUID playerUuid, String worldName, double x, double y, double z)
    {
        ExitData exitData = new ExitData(worldName, x, y, z);
        playerUuidToExitData.put(playerUuid, exitData);
    }

    public ExitData getExitData(UUID playerUuid)
    {
        Bukkit.getLogger().info("Trying to find exit data for: " + playerUuid);

        for (HashMap.Entry<UUID, ExitData> entry : playerUuidToExitData.entrySet())
        {
            Bukkit.getLogger().info("Found exit data for: " + entry.getKey());
        }

        if (playerUuidToExitData.containsKey(playerUuid))
            return playerUuidToExitData.get(playerUuid);

        return null;
    }

    public void storeRaidUuid(UUID playerUuid, UUID raidUuid)
    {
        playerUuidToRaidUuid.put(playerUuid, raidUuid);
    }

    public void removeRaidUuid(UUID playerUuid)
    {
        playerUuidToRaidUuid.remove(playerUuid);
    }

    public void load()
    {
        Bukkit.getLogger().info("PlayerRaidDataStore - loading from disk!");

        try (FileReader reader = new FileReader(persistentDataFileName))
        {
            JsonElement json = JsonParser.parseReader(reader);
            JsonObject data = json.getAsJsonObject();

            JsonObject raidEntry = data.get(raidProperty).getAsJsonObject();
            for (String key : raidEntry.keySet())
                playerUuidToRaidUuid.put(UUID.fromString(key), UUID.fromString(raidEntry.get(key).getAsString()));

            JsonObject exitEntries = data.get(exitProperty).getAsJsonObject();

            for (String key : exitEntries.keySet())
            {
                JsonObject exitEntry = exitEntries.get(key).getAsJsonObject();

                String worldName = exitEntry.get(exitWorldNameProperty).getAsString();
                double x = exitEntry.get(exitXProperty).getAsDouble();
                double y = exitEntry.get(exitYProperty).getAsDouble();
                double z = exitEntry.get(exitZProperty).getAsDouble();

                ExitData exitData = new ExitData(worldName, x, y, z);
                playerUuidToExitData.put(UUID.fromString(key), exitData);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            Bukkit.getLogger().info("PlayerRaidDataStore - failed raids from disk!");
        }
    }

    public void save()
    {
        Bukkit.getLogger().info("PlayerRaidDataStore - saving to disk!");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject topElement = new JsonObject();

        JsonObject raidElement = new JsonObject();
        for (Map.Entry<UUID, UUID> playerUuidToRaidUuid : playerUuidToRaidUuid.entrySet())
        {
            raidElement.addProperty(playerUuidToRaidUuid.getKey().toString(), playerUuidToRaidUuid.getValue().toString());
        }

        JsonObject exitElement = new JsonObject();
        for (Map.Entry<UUID, ExitData> playerUuidToExitData : playerUuidToExitData.entrySet())
        {
            ExitData exitData = playerUuidToExitData.getValue();
            JsonObject exitDataJson = new JsonObject();
            exitDataJson.addProperty(exitWorldNameProperty, exitData.worldName);
            exitDataJson.addProperty(exitXProperty, exitData.x);
            exitDataJson.addProperty(exitYProperty, exitData.y);
            exitDataJson.addProperty(exitZProperty, exitData.z);

            exitElement.add(playerUuidToExitData.getKey().toString(), exitDataJson);
        }

        topElement.add(raidProperty, raidElement);
        topElement.add(exitProperty, exitElement);

        String jsonString = gson.toJson(topElement);
        try (FileWriter writer = new FileWriter(persistentDataFileName))
        {
            writer.write(jsonString);
        } catch (Exception e)
        {
            e.printStackTrace();
            Bukkit.getLogger().warning("PlayerRaidDataStore - could not save raid sessions!");
        }
    }
}
