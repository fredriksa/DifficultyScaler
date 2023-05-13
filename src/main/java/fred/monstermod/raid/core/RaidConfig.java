package fred.monstermod.raid.core;

public class RaidConfig {
    public static final String WORLD_NAME = "raid_world";
    public static final int X_SPREAD = 800000;
    public static final int Z_SPREAD = 800000;

    public static final int RAID_TIME_LIMIT_SECONDS = 120;
    public static final int RAID_TIME_ANNOUNCE_EVERY_SECOND_BEFORE = 5;

    public static final String METADATAKEY_RAID_JOIN_X = "RAID_JOIN_X";
    public static final String METADATAKEY_RAID_JOIN_Y = "RAID_JOIN_Y";
    public static final String METADATAKEY_RAID_JOIN_Z = "RAID_JOIN_Z";
    public static final String METADATAKEY_RAID_JOIN_WORLD = "RAID_JOIN_WORLD";

    public static final String COMPASS_ITEM_NAME = "Raid Exit Compass";
    public static final int EXTRACTION_DISTANCE_MAX = 20;

    public static final int MAX_BLOCKS_TO_BREAK_PER_RAID = 50;
    public static final int BLOCKS_TO_BREAK_WARNING_EVERY_X_BLOCKS = 5;
}
