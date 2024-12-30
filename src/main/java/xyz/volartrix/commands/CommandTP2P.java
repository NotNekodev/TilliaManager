package xyz.volartrix.commands;

import me.clip.placeholderapi.libs.kyori.adventure.platform.facet.Facet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTP2P implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        Player player = Bukkit.getPlayer(sender.getName());

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                if (!target.equals(player)) {
                    player.teleport(target.getLocation());
                    player.sendMessage(ChatColor.GREEN + "You have been teleported to " + ChatColor.YELLOW + target.getName() + ChatColor.GREEN + ".");
                    target.sendMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has teleported to you.");
                } else {
                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR!" + ChatColor.RESET + " You cannot teleport to yourself.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR!" + ChatColor.RESET + " Player not found.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Wrong Usage!" + ChatColor.RESET +  "Usage:  /tp2p <player>");
        }

        return true;
    }
}
