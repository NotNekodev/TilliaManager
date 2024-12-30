package xyz.volartrix.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandBroadcast implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {

        String message = String.join(" ", args);

        for (Player p: Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Broadcast: " + ChatColor.RESET + message);
        }

        return false;
    }
}
