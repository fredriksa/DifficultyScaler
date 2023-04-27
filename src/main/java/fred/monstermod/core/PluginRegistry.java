package fred.monstermod.core;

import fred.monstermod.Modifiers;
import fred.monstermod.Monstermod;
import fred.monstermod.listeners.AcidRainListener;
import fred.monstermod.runnables.ServerShutdownRunnable;
import fred.monstermod.runnables.TimeTrackerRunnable;
import fred.monstermod.systems.MeteorRain;

public class PluginRegistry {

    public final TimeTrackerRunnable timeTracker = new TimeTrackerRunnable();
    public final Modifiers modifiers = new Modifiers();
    public final AcidRainListener acidRain = new AcidRainListener();
    public final ServerShutdownRunnable shutdownRunnable = new ServerShutdownRunnable();
    public final MeteorRain meteorRain = new MeteorRain();

    public Monstermod monsterMod;

    // SINGLETON PATTERN BELOW
    private PluginRegistry() {}
    private static PluginRegistry registry;
    public static PluginRegistry Instance()
    {
        if (registry == null)
        {
            registry = new PluginRegistry();
        }

        return registry;
    }
}
