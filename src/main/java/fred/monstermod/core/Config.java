package fred.monstermod.core;

import fred.monstermod.raid.core.RaidConfig;

import java.util.Arrays;
import java.util.HashSet;

public class Config {
    public static final String MAIN_WORLD_NAME = "world";
    public static final double ACID_RAIN_MIN_DAMAGE = 0.5d;
    public static final double ACID_RAIN_FINAL_DAY_DAMAGE = 2;
    public static final int ACID_RAIN_START_MIN_CHANCE = 3;
    public static final int ACID_RAIN_START_MAX_CHANCE = 10;
    public static final int ZOMBIE_HORDE_MIN_CHANCE = 10;
    public static final int ZOMBIE_HORDE_MAX_CHANCE = 20;

    // How much the difficulty increases for each additional player online (0.25 = 25%)
    public static final double DIFFICULTY_MODIFIER_PER_PLAYER = 0.50; //0.25

    public static final double MONSTER_DAMAGE_INCREASE_PER_PLAYER = 0.10;
    public static final float MONSTER_DAMAGE_DEFAULT_INREASE = 1.5f;

    // Chance for having mobs underground spawn with increased movement speed
    public static final int MOB_SPEED_ADD_UNDERGROUND_MIN_CHANCE = 5;
    public static final int MOB_SPEED_ADD_UNDERGROUND_MAX_CHANCE = 35;

    public static final int SILK_TOUCH_MONSTER_SPAWNER_DROP_CHANCE = 40;

    public static final int DROWNED_TRIDENT_SPAWN_MIN_CHANCE = 10;
    public static final int DROWNED_TRIDENT_SPAWN_MAX_CHANCE = 80;

    public static final int TORCH_LOWEST_Y_LEVEL = 20;

    public static final int ZOMBIE_SLOW_MIN_CHANCE = 9;
    public static final int ZOMBIE_SLOW_MAX_CHANCE = 40;

    public static final int HORDE_SPAWN_MODIFIER = 3;

    public static final int ZOMBIE_EQUIPMENT_PER_PIECE_MIN_CHANCE = 20;
    public static final int ZOMBIE_EQUIPMENT_PER_PIECE_MAX_CHANCE = 40;

    public static final int MONSTER_WEAPON_MIN_CHANCE = 30;
    public static final int MONSTER_WEAPON_MAX_CHANCE = 50;

    public static final int ARCHER_WEAPON_MIN_CHANCE = 40;
    public static final int ARCHER_WEAPON_MAX_CHANCE = 65;

    public static final int SPIDER_WEB_SPAWN_MIN_CHANCE = 50;
    public static final int SPIDER_WEB_SPAWN_MAX_CHANCE = 75;
    public static final int SPIDER_PER_WEB_SPAWN_MIN_CHANCE = 50;
    public static final int SPIDER_PER_WEB_SPAWN_MAX_CHANCE = 80;

    public static final int SPIDER_WEB_DURATION_SECONDS = 10;

    public static final int SKELETON_DODGE_ARROW_MIN_CHANCE = 10;
    public static final int SKELETON_DODGE_ARROW_MAX_CHANCE = 30;

    public static final int SUPERCHAGED_CREEPER_MAX_Y_LEVEL = 0;

    public static final int SUPERCHARGED_CREEPER_MIN_CHANCE = 8;
    public static final int SUPERCHAGED_CREEPER_MAX_CHANCE = 40;

    public static final int LEAPING_SPIDER_MIN_CHANCE = 5;
    public static final int LEAPING_SPIDER_MAX_CHANCE = 25;

    public static final int LEAPING_SPIDER_JUMP_EVERY_SECOND_MIN = 2;

    public static final int LEAPING_SPIDER_JUMP_EVERY_SECOND_MAX = 5;

    public static final int ZOMBIE_PIGLIN_LOOK_AT_AGGRO_CHANCE_MIN = 40;
    public static final int ZOMBIE_PIGLIN_LOOK_AT_AGGRO_CHANCE_MAX = 70;

    public static final int SERVER_SHUTDOWN_TIMER_MINUTES = 90;
    public static final int SERVER_SHUTDOWN_ANNOUNCE_EVERY_X_MINUTE_BEFORE = 30;
    public static final int SERVER_SHUTDOWN_ANNOUNCE_EVERY_MINUTE_X_MINUTES_BEFORE = 5;

    public static final int PLANT_SAPLING_ON_TREE_LOG_BROKEN_CHANCE = 90;

    public static final boolean METEOR_RAIN_ENABLED = true;

    public static final int METEOR_DAMAGE = 3;
    public static final int METEOR_FIRE_SECONDS = 7;
    public static final int METEOR_KNOCKBACK_POWER_XZ = 23;
    public static final int METEOR_KNOCKBACK_POWER_Y = 1;
    public static final int METEOR_KNOCKBACK_RADIUS = 7;
    public static final int METEOR_SPAWN_Y = 320;
    public static final int METEOR_SPAWN_OFFSET_X_Z = 2;
    public static final float METEOR_VELOCITY_Y = -0.1f;
    public static final int METEOR_VELOCITY_SPREAD = 2;
    public static final int METEOR_STOP_SPAWNING_PLAYER_UNDERGROUND_Y_DIFF = 20;

    public static final int METEOR_RAIN_START_MIN_CHANCE = 7;
    public static final int METEOR_RAIN_START_MAX_CHANCE = 15;

    public static final int METEOR_RAIN_MIN_LENGTH_SECONDS = 90;
    public static final int METEOR_RAIN_MAX_LENGTH_SECONDS = 120;
    public static final int METEOR_RAIN_INITIAL_DELAY_SECONDS = 180;
    public static final int METEOR_RAIN_DELAY_SECONDS = 2700;
    public static final int METEOR_RAIN_TRY_ACTIVATE_EVERY_X_SECONDS = 600;

    public static final int METEOR_BATCH_SPAWN_FREQUENCY_TICKS = 10;
    public static final int METEORS_PER_PLAYER_PER_BATCH = 3;

    public static final float MINECART_MAX_SPEED_MODIFIER = 1;
    public static final float MINECART_ACTIVATOR_RAIL_SPEED_BOOST = 2f;

    public static final boolean REVIVE_SYSTEM_ENABLED = true;
    public static final long REVIVE_COOLDOWN_SECONDS = 1800;
    public static final float REVIVE_DAMAGE_PER_TICK = 1f;
    public static final int REVIVE_DAMAGE_EVERY_X_SECONDS = 2;
    public static final int REVIVE_PLAYER_NEARBY_DISTANCE = 150;

    public static final String SERVER_OWNER_USERNAME = "Fredkan";

    // List of worlds that the general difficulty scaler should modify.
    public static final HashSet<String> WORLDS_TO_MODIFY = new HashSet<>(Arrays.asList(Config.MAIN_WORLD_NAME, RaidConfig.WORLD_NAME));
}
