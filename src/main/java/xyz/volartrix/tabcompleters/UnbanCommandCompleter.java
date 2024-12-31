package xyz.volartrix.tabcompleters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import xyz.volartrix.util.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UnbanCommandCompleter implements TabCompleter {

    private final Storage storage;

    public UnbanCommandCompleter(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        // If it's the first argument (player), suggest banned players
        if (args.length == 1) {
            // Loop through all entries in the storage map and filter for banned players
            for (Map.Entry<String, String> entry : storage.getStorageMap().entrySet()) {
                // Only consider keys starting with "bans."
                if (entry.getKey().startsWith("bans.")) {
                    String bannedPlayer = entry.getKey().substring(5); // Remove "bans." prefix
                    // Suggest player names (filtered by matching the input so far)
                    if (bannedPlayer.startsWith(args[0])) {
                        suggestions.add(bannedPlayer);
                    }
                }
            }
        }

        return suggestions;
    }
}
