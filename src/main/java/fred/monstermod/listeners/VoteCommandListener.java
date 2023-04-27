package fred.monstermod.listeners;

import fred.monstermod.core.MessageUtil;
import fred.monstermod.general.DaytimeVoteSession;
import fred.monstermod.general.RestartVoteSession;
import fred.monstermod.general.VoteSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;


public class VoteCommandListener implements Listener {

    private final List<VoteSession> activeSessions = new ArrayList<>();

    public VoteCommandListener()
    {
        activeSessions.add(new RestartVoteSession("server restart", "!restart", ""));
        activeSessions.add(new DaytimeVoteSession("daytime", "!day", "!auto day"));
    }

    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event)
    {
        for (VoteSession voteSession : activeSessions)
        {
            if (event.getMessage().equals(voteSession.command))
            {
                voteSession.vote(event.getPlayer().getName());
            }
            else if (!voteSession.autoCommand.isEmpty() && event.getMessage().equals(voteSession.autoCommand))
            {
                voteSession.autoVote(event.getPlayer().getName());
            }
        }
    }
}
