package fred.monstermod.general;

import org.bukkit.Bukkit;

public class RestartVoteSession extends VoteSession {
    public RestartVoteSession(String _name, String _command, String _autoCommand) {
        super(_name, _command, _autoCommand);
    }

    @Override
    public void OnVotePassed()
    {
        Bukkit.shutdown();
    }
}
