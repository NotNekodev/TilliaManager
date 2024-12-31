package xyz.volartrix.tabcompleters;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TP2PCommandCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        // If there's one argument (player), suggest online players
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], getOnlinePlayerNames(sender), suggestions);
        }

        return suggestions;
    }

    // Get a list of online players' names
    private List<String> getOnlinePlayerNames(CommandSender sender) {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Don't suggest the sender as a target (they can't teleport to themselves)
            if (!player.getName().equals(sender.getName())) {
                players.add(player.getName());
            }
        }
        return players;
    }
}
