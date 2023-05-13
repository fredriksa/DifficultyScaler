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
    private HashMap<UUID, UUID> playerUuidToRaidUuid = new HashMap<>();
    private final String persistentDataFileName = "playerraiddatastore.json";

    public UUID getRaidUuid(UUID playerUuid)
    {
        if (playerUuidToRaidUuid.containsKey(playerUuid))
            return playerUuidToRaidUuid.get(playerUuid);

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

            for (String key : data.keySet())
                playerUuidToRaidUuid.put(UUID.fromString(key), UUID.fromString(data.get(key).getAsString()));
        } catch (Exception e)
        {
            Bukkit.getLogger().info("PlayerRaidDataStore - failed raids from disk!");
        }
    }

    public void save()
    {
        Bukkit.getLogger().info("PlayerRaidDataStore - saving to disk!");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject topElement = new JsonObject();

        for (Map.Entry<UUID, UUID> playerUuidToRaidUuid : playerUuidToRaidUuid.entrySet())
        {
            topElement.addProperty(playerUuidToRaidUuid.getKey().toString(), playerUuidToRaidUuid.getValue().toString());
        }

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
