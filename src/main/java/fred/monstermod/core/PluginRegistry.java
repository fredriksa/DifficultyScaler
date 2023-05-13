package fred.monstermod.core;

import fred.monstermod.Modifiers;
import fred.monstermod.Monstermod;
import fred.monstermod.listeners.AcidRainListener;
import fred.monstermod.listeners.VoteSessionListener;
import fred.monstermod.raid.Raid;
import fred.monstermod.runnables.ServerShutdownRunnable;
import fred.monstermod.runnables.TimeTrackerRunnable;
import fred.monstermod.systems.MeteorRainSystem;

public class PluginRegistry {

    public final TimeTrackerRunnable timeTracker = new TimeTrackerRunnable();
    public final Modifiers modifiers = new Modifiers();
    public final AcidRainListener acidRain = new AcidRainListener();
    public final ServerShutdownRunnable shutdownRunnable = new ServerShutdownRunnable();
    public final MeteorRainSystem meteorRainSystem = new MeteorRainSystem();
    public final VoteSessionListener voteSessionListener = new VoteSessionListener();
    public final Raid raid = new Raid();

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
