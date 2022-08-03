package fred.monstermod.runnables;

import fred.monstermod.core.TimeToPhase;
import fred.monstermod.core.listeners.iDayChangedListener;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TimeTrackerRunnable extends BukkitRunnable {

    private List<iDayChangedListener> listeners = new ArrayList<>();
    private long phase = 0;

    public final long maxPhase = 7;

    public void listen(iDayChangedListener listener)
    {
        listeners.add(listener);
    }

    public double getPhaseModifier()
    {
        return ((double) getCurrentPhase() / maxPhase);
    }

    @Override
    public void run()
    {
        final long newDay = TimeToPhase.convert(Bukkit.getWorld("world").getGameTime());
        if (newDay != phase)
        {
            Bukkit.getLogger().info("Server day changed to: " + newDay);
            onDayChanged(newDay);
            phase = newDay;
        }
    }

    public long getCurrentPhase()
    {
        return phase;
    }

    private void onDayChanged(long newDay)
    {
        for (iDayChangedListener listener : listeners)
        {
            listener.onPhaseChanged(newDay);
        }
    }
}
