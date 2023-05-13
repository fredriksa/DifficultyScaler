package fred.monstermod.raid.core;

import fred.monstermod.core.Config;
import fred.monstermod.core.MessageUtil;
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
        if (session.getElapsedActiveTime() >= Config.SERVER_SHUTDOWN_TIMER_MINUTES)
        {
            session.end();
        }

        final int secondsUntilTimeOver = RaidConfig.RAID_TIME_LIMIT_SECONDS - (int)session.getElapsedActiveTime();
        if (secondsUntilTimeOver > 0)
        {
            if (session.getElapsedActiveTime() >= RaidConfig.RAID_TIME_LIMIT_SECONDS - RaidConfig.RAID_TIME_ANNOUNCE_EVERY_SECOND_BEFORE)
            {
                session.broadcast("Raid session ends in " + secondsUntilTimeOver + " seconds(s).");
            }
            else if (session.getElapsedActiveTime() % RaidConfig.RAID_TIME_ANNOUNCE_EVERY_SECOND_BEFORE == 0)
            {
                session.broadcast("Raid session ends in " + secondsUntilTimeOver + " seconds(s).");
            }
        }

        if (secondsUntilTimeOver == 0)
            session.end();

        session.setElapsedActiveTime(session.getElapsedActiveTime() + 1);
    }
}
