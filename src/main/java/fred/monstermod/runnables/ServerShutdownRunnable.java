package fred.monstermod.runnables;

import fred.monstermod.core.Config;
import fred.monstermod.core.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerShutdownRunnable extends BukkitRunnable {

    private int trackedMinutes = 0;

    @Override
    public void run()
    {
        if (trackedMinutes >= Config.SERVER_SHUTDOWN_TIMER_MINUTES)
        {
            Bukkit.shutdown();
        }

        int minutesUntilRestart = Config.SERVER_SHUTDOWN_TIMER_MINUTES - trackedMinutes;
        if (minutesUntilRestart > 0)
        {
            if (trackedMinutes >= Config.SERVER_SHUTDOWN_TIMER_MINUTES - Config.SERVER_SHUTDOWN_ANNOUNCE_EVERY_MINUTE_X_MINUTES_BEFORE)
            {
                MessageUtil.broadcast("Server restarting in " + minutesUntilRestart + " minute(s).");
            }
            else if (trackedMinutes % Config.SERVER_SHUTDOWN_ANNOUNCE_EVERY_X_MINUTE_BEFORE == 0)
            {
                MessageUtil.broadcast("Server restarting in " + minutesUntilRestart + " minute(s).");
            }
        }

        trackedMinutes++;
    }
}
