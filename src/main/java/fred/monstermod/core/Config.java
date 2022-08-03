package fred.monstermod.core;

public class Config {
    public static final double ACID_RAIN_MIN_DAMAGE = 0.5d;
    public static final double ACID_RAIN_FINAL_DAY_DAMAGE = 2;
    public static final int ACID_RAIN_START_MIN_CHANCE = 5;
    public static final int ACID_RAIN_START_MAX_CHANCE = 40;
    public static final int ZOMBIE_HORDE_MIN_CHANCE = 10;
    public static final int ZOMBIE_HORDE_MAX_CHANCE = 20;

    // How much the difficulty increases for each additional player online (0.25 = 25%)
    public static final double DIFFICULTY_MODIFIER_PER_PLAYER = 0.25;

    // Chance for having mobs underground spawn with increased movement speed
    public static final int MOB_SPEED_ADD_UNDERGROUND_MIN_CHANCE = 5;
    public static final int MOB_SPEED_ADD_UNDERGROUND_MAX_CHANCE = 35;

    public static final int SILK_TOUCH_MONSTER_SPAWNER_DROP_CHANCE = 40;

    public static final int DROWNED_TRIDENT_SPAWN_MIN_CHANCE = 10;
    public static final int DROWNED_TRIDENT_SPAWN_MAX_CHANCE = 80;

    public static final int TORCH_LOWEST_Y_LEVEL = 20;

    public static final int ZOMBIE_SLOW_MIN_CHANCE = 9;
    public static final int ZOMBIE_SLOW_MAX_CHANCE = 40;

    public static final int ZOMBIE_EQUIPMENT_PER_PIECE_MIN_CHANCE = 20;
    public static final int ZOMBIE_EQUIPMENT_PER_PIECE_MAX_CHANCE = 40;
}
