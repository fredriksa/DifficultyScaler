package fred.monstermod.general;

import fred.monstermod.core.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class DaytimeVoteSession extends VoteSession {
    public DaytimeVoteSession(String _name, String _command) {
        super(_name, _command);
    }

    private final int SUNSET = 13000;
    private final int SUNRISE = 23000;

    @Override
    public void OnVotePassed()
    {
        boolean loggedMessageOnce = false;
        for (World world : Bukkit.getServer().getWorlds())
        {
            boolean isItNight = world.getTime() > SUNSET && world.getTime() < SUNRISE;
            if (isItNight)
            {
                if (!loggedMessageOnce)
                {
                    MessageUtil.broadcast(ChatColor.GREEN + "World turning to day!");
                    loggedMessageOnce = true;
                }

                world.setTime(SUNRISE);
            }
            else
            {
                if (!loggedMessageOnce)
                {
                    MessageUtil.broadcast(ChatColor.RED + "World could not turn to day -- it's already daytime!");
                    loggedMessageOnce = true;
                }
            }
        }
    }
}
