package fred.monstermod.listeners;

import fred.monstermod.core.Config;
import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.core.RandomUtil;
import fred.monstermod.runnables.AcidRainRunnable;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Random;

public class AcidRainListener implements Listener {

    private boolean acidRainActive = false;
    private AcidRainRunnable runner = new AcidRainRunnable();

    public boolean isAcidRainActive()
    {
        return acidRainActive;
    }

    @EventHandler
    public void OnWeatherChange(WeatherChangeEvent event)
    {
        final boolean isRaining = event.toWeatherState();
        if (isAcidRainActive() && !isRaining)
        {
            inactivate();
        }
        else if (isRaining)
        {
            if (RandomUtil.shouldEventOccur(Config.ACID_RAIN_START_MIN_CHANCE, Config.ACID_RAIN_START_MAX_CHANCE))
            {
                activate();
            }
        }
    }

    private void inactivate()
    {
        acidRainActive = false;
        runner.cancel();
        runner = null;
    }

    private void activate()
    {
        acidRainActive = true;

        if (runner == null)
        {
            runner = new AcidRainRunnable();
        }

        runner.runTaskTimer(PluginRegistry.Instance().monsterMod, 20L * 10L, 20L * 2L);
        MessageUtil.broadcast(ChatColor.GREEN + "The rain reeks of a foul stench and has a slight sting to the touch");
    }
}
