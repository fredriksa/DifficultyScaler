package fred.monstermod.raid.core;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class RaidTrackerRunnable extends BukkitRunnable {

    private RaidSession session;

    public RaidTrackerRunnable(RaidSession _session)
    {
        session = _session;
    }

    @Override
    public void run() {
        CheckTime();
    }

    public void CheckTime()
    {
        final int minutesUntilTimeOver = RaidConfig.RAID_TIME_LIMIT_MINUTES - (int)session.getElapsedActiveTime();
        Bukkit.getLogger().info("Raid limit: " + RaidConfig.RAID_TIME_LIMIT_MINUTES + " elapsed: " + session.getElapsedActiveTime() + "minutesUntilTimeOver: " + minutesUntilTimeOver);
        if (minutesUntilTimeOver > 0)
        {
            if (session.getElapsedActiveTime() >= RaidConfig.RAID_TIME_LIMIT_MINUTES - RaidConfig.RAID_TIME_ANNOUNCE_EVERY_MINUTE_BEFORE)
            {
                session.broadcast("Raid session ends in " + minutesUntilTimeOver + " minute(s).");
            }
            else if (session.getElapsedActiveTime() % RaidConfig.RAID_TIME_ANNOUNCE_EVERY_MINUTE_BEFORE == 0)
            {
                session.broadcast("Raid session ends in " + minutesUntilTimeOver + " minute(s).");
            }
        }

        if (minutesUntilTimeOver <= 0)
            session.end();

        session.setElapsedActiveTime(session.getElapsedActiveTime() + 1);
    }
}
