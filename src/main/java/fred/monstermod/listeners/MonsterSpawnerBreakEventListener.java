package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.events.MonsterSpawnerBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class MonsterSpawnerBreakEventListener implements Listener {

    @EventHandler
    public void OnMonsterSpawnerBreakEvent(MonsterSpawnerBreakEvent event)
    {
        final Block brokenSpawner = event.getBrokenSpawner();
        final boolean isBrokenSpawnerCreatureSpawner = brokenSpawner.getState() instanceof CreatureSpawner;

        if (!isBrokenSpawnerCreatureSpawner)
        {
            return;
        }

        if (!canPlayerMakeSpawnerDrop(event.getBreaker()))
        {
            return;
        }

        final CreatureSpawner brokenCreatureSpawner = (CreatureSpawner) brokenSpawner.getState();

        final ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
        final BlockStateMeta spawnerItemMeta = (BlockStateMeta) spawnerItem.getItemMeta();
        final CreatureSpawner spawnerItemSpawner = (CreatureSpawner) spawnerItemMeta.getBlockState();

        spawnerItemSpawner.setSpawnedType(brokenCreatureSpawner.getSpawnedType());
        spawnerItemMeta.setBlockState(spawnerItemSpawner);
        spawnerItem.setItemMeta(spawnerItemMeta);

        final Location brokenSpawnerLocation = brokenSpawner.getLocation();
        brokenSpawnerLocation.getWorld().dropItem(brokenSpawnerLocation, spawnerItem);
    }

    private boolean canPlayerMakeSpawnerDrop(Player player)
    {
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        final boolean brokenByHand = mainHandItem == null;

        if (!brokenByHand && mainHandItem.containsEnchantment(Enchantment.SILK_TOUCH))
        {
            return RandomUtil.shouldEventOccur(Config.SILK_TOUCH_MONSTER_SPAWNER_DROP_CHANCE);
        }

        return false;
    }
}
