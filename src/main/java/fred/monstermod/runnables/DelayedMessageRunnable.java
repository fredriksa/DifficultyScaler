package fred.monstermod.runnables;

import fred.monstermod.core.MessageUtil;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedMessageRunnable extends BukkitRunnable {

    public DelayedMessageRunnable(String _message)
    {
        message = _message;
    }

    @Override
    public void run() {
        MessageUtil.broadcast(message);
    }

    private String message;
}
