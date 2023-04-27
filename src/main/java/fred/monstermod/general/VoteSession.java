package fred.monstermod.general;

import fred.monstermod.core.MessageUtil;
import fred.monstermod.core.PluginRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Objects;

public class VoteSession
{
    public String name;
    public String command;
    public String autoCommand;

    public HashSet<String> votedPlayers = new HashSet<>();
    public HashSet<String> autoVotedPlayers = new HashSet<>();

    private boolean scheduled = false;

    public VoteSession(String _name, String _command, String _autoCommand)
    {
        name = _name;
        command = _command;
        autoCommand = _autoCommand;
    }

    public void autoVote(String playerName)
    {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) return;

        boolean haveAlreadyAutoVoted = autoVotedPlayers.contains(playerName);
        if (haveAlreadyAutoVoted)
        {
            player.sendMessage(ChatColor.GREEN + "You are no longer auto-voting on " + name + ".");
            autoVotedPlayers.remove(playerName);
            return;
        }

        player.sendMessage(ChatColor.GREEN + "You are now auto-voting on " + name + ".");
        autoVotedPlayers.add(playerName);
        vote(playerName);
    }

    public void vote(String playerName)
    {
        clearOfflinePlayers();

        if (!votedPlayers.contains(playerName))
        {
            votedPlayers.add(playerName);

            if (votedPlayers.size() == requiredVotes())
            {
                VotePassed(playerName);
            }
            else
            {
                VoteAdded(playerName);
            }
        }
        else if (votedPlayers.size() == requiredVotes())
        {
            VotePassed(playerName);
        }
        else
        {
            AlreadyVoted(playerName);
        }
    }

    public void OnVotePassed()
    {

    }

    private void AfterVotePassed()
    {
        votedPlayers.clear();
        votedPlayers.addAll(autoVotedPlayers);
        scheduled = false;
    }

    private void VotePassed(String playerName)
    {
        if (scheduled)
        {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null) return;

            player.sendMessage(ChatColor.RED + " You voted for " + name + ". But it is already scheduled. Hold on.");
        }
        else
        {
            MessageUtil.broadcast(ChatColor.GREEN + playerName + " voted for " + name + ". Effective in ~15 seconds.");
            scheduled = true;

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
                    OnVotePassed();
                    AfterVotePassed();
                }
            };

            runnable.runTaskLater(PluginRegistry.Instance().monsterMod, 20L * 15L);
        }
    }

    private void VoteAdded(String playerName)
    {
        MessageUtil.broadcast(ChatColor.YELLOW + playerName + " voted for " + name + ". " + remainingVotesRequired() + " more vote(s) required.");
    }

    private void AlreadyVoted(String playerName)
    {
        Player player = Bukkit.getPlayer(playerName);
        if (player == null) return;

        String message = "You have already voted for " + name + ".";

        if (remainingVotesRequired() > 0)
        {
            message += " " + remainingVotesRequired() + " more vote(s) required.";
        }

        player.sendMessage(ChatColor.RED + message);
    }

    private void clearOfflinePlayers()
    {
        for (String playerName : votedPlayers)
        {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null)
            {
                votedPlayers.remove(playerName);
            }
        }

        for (String playerName : autoVotedPlayers)
        {
            Player player = Bukkit.getPlayer(playerName);
            if (player == null)
            {
                autoVotedPlayers.remove(playerName);
            }
        }
    }

    private int remainingVotesRequired()
    {
        return requiredVotes() - votedPlayers.size();
    }

    private int requiredVotes()
    {
        return Bukkit.getOnlinePlayers().size();
    }
}