package xyz.volartrix.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static xyz.volartrix.util.PublicVars.staffChatEnabled;

public class CommandStaffchat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        if (args.length != 0) {
            String message = String.join(" ", args);
            String prefix = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");
            String suffix = PlaceholderAPI.setPlaceholders(player, "%luckperms_suffix%");

            if (prefix == null) prefix = "";
            if (suffix == null) suffix = "";

            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.hasPermission("bozo.staffchat")) {
                    target.sendMessage(ChatColor.RED + "[STAFF] " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', prefix) + player.getName() + ChatColor.translateAlternateColorCodes('&', suffix) + ": " + message);
                }
            }

        } else {
            if (staffChatEnabled.contains(player)) {
                staffChatEnabled.remove(player);
                player.sendMessage(ChatColor.RED + "Staff chat disabled!");
            } else {
                staffChatEnabled.add(player);
                player.sendMessage(ChatColor.GREEN + "Staff chat enabled!");
            }
        }

        return true;
    }
}
