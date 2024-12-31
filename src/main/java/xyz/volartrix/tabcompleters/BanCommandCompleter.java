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

public class BanCommandCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            if (sender instanceof Player) {
                StringUtil.copyPartialMatches(args[0], getOnlinePlayers(), suggestions);
            } else {
                StringUtil.copyPartialMatches(args[0], getAllPlayers(), suggestions);
            }
        }

        else if (args.length == 2) {
            StringUtil.copyPartialMatches(args[1], getBanReasons(), suggestions);
        }

        return suggestions;
    }

    private List<String> getOnlinePlayers() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

    private List<String> getAllPlayers() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());
        }
        return players;
    }

    private List<String> getBanReasons() {
        List<String> reasons = new ArrayList<>();
        reasons.add("Cheating");
        reasons.add("Abuse");
        reasons.add("Griefing");
        reasons.add("Spamming");
        reasons.add("Offensive Language");
        return reasons;
    }
}

