package fred.monstermod.general;

import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.listeners.iDayChangedListener;

public class PhaseChangedAdverter implements iDayChangedListener {
    @Override
    public void onPhaseChanged(long phase) {
        MessageUtil.broadcast("Beginning phase " + phase + "...");
    }
}
