package fred.monstermod.runnables;

import fred.monstermod.core.TimeToDay;
import fred.monstermod.core.listeners.iDayChangedListener;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TimeTrackerRunnable extends BukkitRunnable {

    private List<iDayChangedListener> listeners = new ArrayList<>();
    private long day = 0;

    public final long maxDay = 7;

    public void listen(iDayChangedListener listener)
    {
        listeners.add(listener);
    }

    public double getPhaseModifier()
    {
        return ((double)getCurrentDay() / maxDay);
    }

    @Override
    public void run()
    {
        final long newDay = TimeToDay.convert(Bukkit.getWorld("world").getGameTime());
        if (newDay != day)
        {
            Bukkit.getLogger().info("Server day changed to: " + newDay);
            onDayChanged(newDay);
            day = newDay;
        }
    }

    public long getCurrentDay()
    {
        return day;
    }

    private void onDayChanged(long newDay)
    {
        for (iDayChangedListener listener : listeners)
        {
            listener.onDayChanged(newDay);
        }
    }
}
