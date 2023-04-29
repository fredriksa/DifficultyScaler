package fred.monstermod.listeners;

import fred.monstermod.general.DaytimeVoteSession;
import fred.monstermod.general.RestartVoteSession;
import fred.monstermod.general.VoteSession;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class VoteSessionListener implements Listener {

    private final HashMap<String, VoteSession> nameToVoteSession = new HashMap<>();

    private Object onChatMutex = new Object();

    private String persistentDataFileName = "votesessionlistener.yml";
    private final String rootKey = "root";
    YamlConfiguration persistentData = new YamlConfiguration();

    public VoteSessionListener()
    {
        RestartVoteSession restartVoteSession = new RestartVoteSession("server restart", "!restart", "");
        nameToVoteSession.put(restartVoteSession.name, restartVoteSession);

        DaytimeVoteSession daytimeVoteSession = new DaytimeVoteSession("daytime", "!day", "!auto day");
        nameToVoteSession.put(daytimeVoteSession.name, daytimeVoteSession);
    }

    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event)
    {
        synchronized (onChatMutex)
        {
            for (VoteSession voteSession : nameToVoteSession.values())
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

    public void onEnable()
    {
        try
        {
            persistentData.load(new File(persistentDataFileName));
        } catch (Exception e)
        {
            Bukkit.getLogger().warning("VoteSessionListener - could not load vote sessions!");
            return;
        }

        ConfigurationSection section = persistentData.getConfigurationSection(rootKey);
        for (String key : section.getKeys(false)) {
            if (!nameToVoteSession.containsKey(key)) continue;

            List<String> listValues = section.getStringList(key);
            String[] values = listValues.toArray(new String[listValues.size()]);

            VoteSession voteSession = nameToVoteSession.get(key);
            for (String autoVotedPlayer : values)
            {
                voteSession.TriggerAutoVote(autoVotedPlayer);
            }
        }
    }

    public void onDisable()
    {
        HashMap<String, String[]> saveMap = new HashMap<>();

        for (VoteSession voteSession : nameToVoteSession.values())
        {
            final int autoVotedCount = voteSession.getAutoVotedPlayers().size();
            String[] playersAutoVoted = voteSession.getAutoVotedPlayers().toArray(new String[autoVotedCount]);
            if (playersAutoVoted == null) continue;
            saveMap.put(voteSession.name, playersAutoVoted);
        }

        persistentData.set(rootKey, saveMap);
        try {
            persistentData.save(persistentDataFileName);
        } catch (IOException e) {
            Bukkit.getLogger().warning("VoteSessionListener - could not save vote sessions!");
        }
    }
}
