package fred.monstermod.general;

import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.listeners.iDayChangedListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DayChangedAdverter implements iDayChangedListener {
    @Override
    public void onDayChanged(long day) {
        MessageUtil.broadcast("Beginning phase " + day + "...");
    }
}
