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

public class TempbanCommandCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        // First argument: Suggest online player names
        if (args.length == 1) {
            StringUtil.copyPartialMatches(args[0], getOnlinePlayerNames(), suggestions);
        }
        // Second argument: Suggest valid duration units with optional number (e.g., 1s, 1m, etc.)
        else if (args.length == 2) {
            // Check if the first part of the second argument is a number
            if (args[1].matches("[0-9]+")) {
                // If there's a number, append valid duration units (e.g., 5s, 5m, 5h, etc.)
                List<String> validUnits = List.of("s", "m", "h", "d", "w", "y");
                for (String unit : validUnits) {
                    suggestions.add(args[1] + unit);
                }
            } else {
                // If no number is provided (empty or non-numeric input), suggest durations like 1s, 1m, 1h, 1d, 1w, 1y
                List<String> durationUnits = List.of("s", "m", "h", "d", "w", "y");
                for (String unit : durationUnits) {
                    suggestions.add("1" + unit); // Default to 1 + unit
                }
            }
        } else if (args.length == 3) {
            // Third argument: Suggest ban reason
            List<String> banReasons = List.of("Cheating", "Abuse", "Griefing", "Spamming", "Offensive Language");
            StringUtil.copyPartialMatches(args[2], banReasons, suggestions);
        }

        return suggestions;
    }

    // Get a list of online players' names
    private List<String> getOnlinePlayerNames() {
        List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Only suggest players who are not the sender themselves
            players.add(player.getName());
        }
        return players;
    }
}

