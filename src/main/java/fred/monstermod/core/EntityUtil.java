package fred.monstermod.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class EntityUtil {

    public static List<Player> playersWithinDistance(Entity target, double distance)
    {
        List<Player> playersWithinDistance = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.equals(target)) continue;

            Location playerLocation = player.getLocation();
            Location targetLocation = target.getLocation();

            double distanceBetweenPlayers = playerLocation.distance(targetLocation);
            if (distanceBetweenPlayers <= distance) {
                playersWithinDistance.add(player);
            }
        }

        return playersWithinDistance;
    }

    public static <T extends Entity> void sortEntitiesByDistance(Location location, List<T> entities)
    {
        if (location == null || entities == null) return;
        if (entities.size() < 2) return;

        Map<T, Double> distances = new HashMap<>();

        // Calculate the distance between each entity and the target location
        for (T entity : entities) {
            Location entityLocation = entity.getLocation();
            double distance = entityLocation.distance(location);
            distances.put(entity, distance);
        }

        // Sort the entities by their distance from the target location
        entities.sort(Comparator.comparingDouble(distances::get));
    }
}
