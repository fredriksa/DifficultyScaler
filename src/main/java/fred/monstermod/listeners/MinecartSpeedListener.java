package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.PluginRegistry;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class MinecartSpeedListener implements Listener {

    private String METADATA_LAST_PASSED_RAIL_LOCATION = "LAST_PASSED_RAIL_LOCATION_X";

    @EventHandler
    public void OnPlayerEnterVehicle(VehicleEnterEvent event)
    {
        Vehicle vehicle = event.getVehicle();
        if (vehicle.getType() != EntityType.MINECART) return;
        if (!(event.getEntered() instanceof Player)) return;

        Player player = (Player) event.getEntered();
        if (player == null) return;

        Minecart minecart = (Minecart) vehicle;
        if (minecart == null) return;

        minecart.setMaxSpeed(minecart.getMaxSpeed() * Config.MINECART_MAX_SPEED_MODIFIER);
    }


    @EventHandler
    public void OnVehicleMove(VehicleMoveEvent event) {
        Entity vehicle = event.getVehicle();
        if (vehicle.getType() != EntityType.MINECART) return;

        Block railBlock = vehicle.getLocation().getBlock();
        if (railBlock == null) return;
        if (railBlock.getType() != Material.ACTIVATOR_RAIL && railBlock.getBlockPower() <= 0) return;
        if (hasAlreadyBeenBoostedBy(vehicle, railBlock)) return;

        FixedMetadataValue metaDataValue = new FixedMetadataValue(PluginRegistry.Instance().monsterMod, railBlock.getLocation().toVector());
        vehicle.setMetadata("LAST_PASSED_RAIL_LOCATION", metaDataValue);
        Vector newVelocity = vehicle.getVelocity().add(vehicle.getLocation().getDirection().multiply(Config.MINECART_ACTIVATOR_RAIL_SPEED_BOOST));
        vehicle.setVelocity(newVelocity);
    }

    private boolean hasAlreadyBeenBoostedBy(Entity vehicle, Block block)
    {
        if (vehicle.hasMetadata(METADATA_LAST_PASSED_RAIL_LOCATION))
        {
            Vector lastPassedRailLocation = (Vector) vehicle.getMetadata(METADATA_LAST_PASSED_RAIL_LOCATION);
            if (lastPassedRailLocation.equals(block.getLocation().toVector()))
            {
                return true;
            }
        }

        return false;
    }
}
