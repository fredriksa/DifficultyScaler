package fred.monstermod;

import fred.monstermod.general.PhaseChangedAdverter;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Monstermod extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("MonsterMod onEnable()");
        PluginRegistry.Instance().monsterMod = this;

        registerEvents();

        PluginRegistry.Instance().timeTracker.runTaskTimer(this, 0, 20L * 3L);
        PluginRegistry.Instance().timeTracker.listen(new PhaseChangedAdverter());
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
    }
}
