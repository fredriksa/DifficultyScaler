package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.DifficultyScaler;
import fred.monstermod.core.MathUtils;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.events.AdditionalEntitySpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MonsterEquipmentAddListener implements Listener {

    private List<EntityType> entitiesToAddArmorTo = Arrays.asList(EntityType.ZOMBIE, EntityType.DROWNED, EntityType.SKELETON, EntityType.ZOMBIE_VILLAGER);

    private List<Material> helmets = Arrays.asList(Material.LEATHER_HELMET, Material.IRON_HELMET, Material.GOLDEN_HELMET, Material.DIAMOND_HELMET);
    private List<Material> chests = Arrays.asList(Material.LEATHER_CHESTPLATE, Material.IRON_CHESTPLATE, Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE);
    private List<Material> legs = Arrays.asList(Material.LEATHER_LEGGINGS, Material.IRON_LEGGINGS, Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS);
    private List<Material> boots = Arrays.asList(Material.LEATHER_BOOTS, Material.IRON_BOOTS, Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS);

    private List<Material> swords = Arrays.asList(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD, Material.GOLDEN_SWORD);

    private List<Material> axes = Arrays.asList(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE);

    @EventHandler
    public void onAdditionalEntitySpawn(AdditionalEntitySpawnEvent event)
    {
        final Entity spawnedEntity = event.getEntity();

        if (shouldAddArmorTo(spawnedEntity))
        {
            addArmorTo(spawnedEntity);
            addWeaponTo(spawnedEntity);
        }
    }

    private void addWeaponTo(Entity spawnedEntity)
    {
        final double yLevel = spawnedEntity.getLocation().getY();
        final LivingEntity livingEntity = (LivingEntity) spawnedEntity;
        final EntityEquipment equipment = livingEntity.getEquipment();

        if (spawnedEntity.getType() == EntityType.SKELETON)
        {
            if (!RandomUtil.shouldEventOccur(Config.ARCHER_WEAPON_MIN_CHANCE, Config.ARCHER_WEAPON_MAX_CHANCE))
            {
                return;
            }

            addSkeletonWeapon(spawnedEntity);
        }
        else
        {
            if (!RandomUtil.shouldEventOccur(Config.MONSTER_WEAPON_MIN_CHANCE, Config.MONSTER_WEAPON_MAX_CHANCE))
            {
                return;
            }

            Material material = null;
            if (RandomUtil.shouldEventOccur(50))
            {
                material = getMaterial(swords, yLevel);
            }
            else
            {
                material = getMaterial(axes, yLevel);
            }

            ItemStack mainHand = new ItemStack(material);
            equipment.setItemInMainHand(mainHand);
        }
    }

    private void addArmorTo(Entity entity)
    {
        final LivingEntity livingEntity = (LivingEntity) entity;
        final EntityEquipment equipment = livingEntity.getEquipment();
        final double entityY = livingEntity.getLocation().getY();

        if (RandomUtil.shouldEventOccur(Config.ZOMBIE_EQUIPMENT_PER_PIECE_MIN_CHANCE, Config.ZOMBIE_EQUIPMENT_PER_PIECE_MAX_CHANCE))
        {
            final Material material = getMaterial(helmets, entityY);
            ItemStack helmet = new ItemStack(material);
            equipment.setHelmet(helmet);
        }

        if (RandomUtil.shouldEventOccur(Config.ZOMBIE_EQUIPMENT_PER_PIECE_MIN_CHANCE, Config.ZOMBIE_EQUIPMENT_PER_PIECE_MAX_CHANCE))
        {
            final Material material = getMaterial(chests, entityY);
            ItemStack chest = new ItemStack(material);
            equipment.setChestplate(chest);
        }

        if (RandomUtil.shouldEventOccur(Config.ZOMBIE_EQUIPMENT_PER_PIECE_MIN_CHANCE, Config.ZOMBIE_EQUIPMENT_PER_PIECE_MAX_CHANCE))
        {
            final Material material = getMaterial(legs, entityY);
            ItemStack legs = new ItemStack(material);
            equipment.setLeggings(legs);
        }

        if (RandomUtil.shouldEventOccur(Config.ZOMBIE_EQUIPMENT_PER_PIECE_MIN_CHANCE, Config.ZOMBIE_EQUIPMENT_PER_PIECE_MAX_CHANCE))
        {
            final Material material = getMaterial(boots, entityY);
            ItemStack boots = new ItemStack(material);
            equipment.setBoots(boots);
        }
    }

    private Material getMaterial(List<Material> materials, double yLevel)
    {
        final int maxPercentage = 60;
        final int minPercentage = 30;

        // Diamonds from -30 to -60
        if (yLevel < -30)
        {
            final double diamondChance = MathUtils.calculateChance(-60, -30, minPercentage, maxPercentage, yLevel);
            if (RandomUtil.shouldEventOccur((int) diamondChance))
            {
                return materials.get(3);
            }
        }

        // Gold from 0 to -30
        if (yLevel < 0)
        {
            final double goldChance = MathUtils.calculateChance(-30, -0, minPercentage, maxPercentage, yLevel);
            if (RandomUtil.shouldEventOccur((int) goldChance))
            {
                return materials.get(2);
            }
        }

        // Iron from 30 to 0
        if (yLevel < 30)
        {
            final double ironChance = MathUtils.calculateChance(0, 30, minPercentage, maxPercentage, yLevel);
            if (RandomUtil.shouldEventOccur((int) ironChance))
            {
                return materials.get(1);
            }
        }

        // Leather for rest
        return materials.get(0);
    }
    private boolean shouldAddArmorTo(Entity entity)
    {
        if (!entitiesToAddArmorTo.contains(entity.getType()))
        {
            return false;
        }

        if (entity.getLocation().getBlock().getLightFromSky() > 0)
        {
            return false;
        }

        return true;
    }

    private void addSkeletonWeapon(Entity skeleton)
    {
        final ItemStack bow = new ItemStack(Material.BOW);

        Random random = new Random();
        final int randomNr = random.nextInt(2) + 1;

        if (randomNr == 1)
        {
            bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        }
        else if (randomNr == 2)
        {
            bow.addEnchantment(Enchantment.ARROW_DAMAGE, (int) DifficultyScaler.scaleWithPhases(1));
        }
        else if (randomNr == 3)
        {
            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, (int) DifficultyScaler.scaleWithPhases(1));
        }

        final LivingEntity livingSkeleton = (LivingEntity) skeleton;
        final EntityEquipment equipment = livingSkeleton.getEquipment();
        equipment.setItemInMainHand(bow);
    }
}
