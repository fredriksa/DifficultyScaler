package fred.monstermod.core;

import fred.monstermod.Modifiers;
import fred.monstermod.Monstermod;
import fred.monstermod.listeners.AcidRainListener;
import fred.monstermod.runnables.TimeTrackerRunnable;

public class PluginRegistry {

    public final TimeTrackerRunnable timeTracker = new TimeTrackerRunnable();
    public final Modifiers modifiers = new Modifiers();
    public final AcidRainListener acidRain = new AcidRainListener();

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
