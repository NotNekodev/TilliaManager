package xyz.volartrix.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.volartrix.util.Storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

public class CommandBan implements CommandExecutor {

    private Storage storage;

    public CommandBan(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Wrong Usage!" + ChatColor.RESET +  "Usage:  /ban <player> <reason>");
            return false;
        }

        String[] subargs = Arrays.copyOfRange(args, 1, args.length);
        String reason = String.join(" ", subargs);

        OfflinePlayer target = sender.getServer().getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR! " + ChatColor.RESET + "The player " + args[0] + " does not exist or has never joined before.");
            return false;
        }

        UUID playerUUID = target.getUniqueId();

        long unbanTimeMillis = -1;
        long banTimeMillis = System.currentTimeMillis();
        storage.set("bans." + playerUUID.toString(), unbanTimeMillis + "|" + reason + "|" + banTimeMillis);

        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "SUCCESS! " + ChatColor.RESET + "The player " + args[0] + " has been banned for " + reason + ".");

        if (target.isOnline()) {
            target.getPlayer().kick(Component.text("§cYou have been permanently banned from this server!\n\n§fReason: " + reason + "\n§fBanned on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM d, h:mm[ a] yyyy")) + "\n§fUnban date: Never\n\n§cIf you believe this is a mistake, please contact support."));
        }


        return true;
    }
}
