package fred.monstermod.general;

import org.bukkit.Bukkit;

public class RestartVoteSession extends VoteSession {
    public RestartVoteSession(String _name, String _command) {
        super(_name, _command);
    }

    @Override
    public void OnVotePassed()
    {
        Bukkit.shutdown();
    }
}
