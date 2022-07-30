package fred.monstermod;

import fred.monstermod.general.DayChangedAdverter;
import fred.monstermod.core.PluginRegistry;
import fred.monstermod.listeners.SpawnEventListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Monstermod extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("MonsterMod onEnable()");
        PluginRegistry.Instance().monsterMod = this;

        Bukkit.getServer().getPluginManager().registerEvents(new SpawnEventListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(PluginRegistry.Instance().acidRain, this);

        PluginRegistry.Instance().timeTracker.runTaskTimer(this, 0, 20L * 3L);
        PluginRegistry.Instance().timeTracker.listen(new DayChangedAdverter());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("MonsterMod onDisable()");
    }
}
