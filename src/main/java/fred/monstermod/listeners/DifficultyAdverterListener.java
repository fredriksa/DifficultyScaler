package fred.monstermod.listeners;

import fred.monstermod.runnables.DelayedDifficultyMessageRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class DifficultyAdverterListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        DelayedDifficultyMessageRunnable delayedMessage = new DelayedDifficultyMessageRunnable(1);
        delayedMessage.queue();
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event)
    {
        DelayedDifficultyMessageRunnable delayedMessage = new DelayedDifficultyMessageRunnable(1);
        delayedMessage.queue();
    }
}
