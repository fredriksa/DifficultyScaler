package fred.monstermod;

import fred.monstermod.core.Config;
import fred.monstermod.core.listeners.TicksUtil;
import fred.monstermod.listeners.HordeSpawnerListener;
import fred.monstermod.listeners.OverworldMobSpawnSpeedAdderListener;
import fred.monstermod.general.PhaseChangedAdverter;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.listeners.UndergroundMobSpawnSpeedAdderListener;
import fred.monstermod.listeners.*;
import fred.monstermod.runnables.LookingAtPiglinStarterRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Monstermod extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("MonsterMod onEnable()");
        PluginRegistry.Instance().monsterMod = this;

        registerEvents();

        // 20 ticks on server per second. 20L * 3L = every 3 seconds.
        PluginRegistry.Instance().timeTracker.runTaskTimer(this, 0, 20L * 3L);
        PluginRegistry.Instance().timeTracker.listen(new PhaseChangedAdverter());
        PluginRegistry.Instance().shutdownRunnable.runTaskTimer(this, 0, 20 * 60);
        PluginRegistry.Instance().meteorRain.runTaskTimer(this, 0, Config.METEOR_BATCH_SPAWN_FREQUENCY_TICKS);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("MonsterMod onDisable()");
    }

    private void registerEvents()
    {
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new DifficultyAdverterListener(), this);
        pluginManager.registerEvents(new PhantomSpawnPreventListener(), this);
        pluginManager.registerEvents(new SpawnEventListener(), this);
        pluginManager.registerEvents(PluginRegistry.Instance().acidRain, this);
        pluginManager.registerEvents(new CreeperExplosionPreventionListener(), this);
        pluginManager.registerEvents(new SkeletonArrowDamageListener(), this);
        pluginManager.registerEvents(new CreeperExplosionDamageListener(), this);
        pluginManager.registerEvents(new MonsterSpawnerBlockBreakListener(), this);
        pluginManager.registerEvents(new MonsterSpawnerBreakEventListener(), this);
        pluginManager.registerEvents(new DrownedSpawnEventListener(), this);
        pluginManager.registerEvents(new DrownedTridentDamageListener(), this);
        pluginManager.registerEvents(new TorchPlacementPreventionListener(), this);
        pluginManager.registerEvents(new MonsterSlowDamageListener(), this);
        pluginManager.registerEvents(new OverworldMobSpawnSpeedAdderListener(), this);
        pluginManager.registerEvents(new UndergroundMobSpawnSpeedAdderListener(), this);
        pluginManager.registerEvents(new HordeSpawnerListener(), this);
        pluginManager.registerEvents(new MonsterEquipmentAddListener(), this);
        pluginManager.registerEvents(new SpiderDeathListener(), this);
        pluginManager.registerEvents(new BlockBreakDropPreventListener(), this);
        pluginManager.registerEvents(new SkeletonDodgeArrowListener(), this);
        pluginManager.registerEvents(new SuperChargedCreeperListener(), this);
        pluginManager.registerEvents(new LeapingSpiderListener(), this);
        pluginManager.registerEvents(new VoteCommandListener(), this);
        pluginManager.registerEvents(new SaplingPlantListener(), this);

        if (Config.METEOR_RAIN_ENABLED)
        {
            pluginManager.registerEvents(PluginRegistry.Instance().meteorRain, this);
        }

        new LookingAtPiglinStarterRunnable().runTaskTimer(this, 0, TicksUtil.secondsToTicks(1));
    }
}
