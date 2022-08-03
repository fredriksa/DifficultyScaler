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
}
