package xyz.volartrix.commands;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.volartrix.util.Storage;

import java.util.Objects;
import java.util.UUID;

public class CommandUnban implements CommandExecutor {

    private Storage storage;

    public CommandUnban(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Wrong Usage!" + ChatColor.RESET +  "Usage:  /unban <player>");
        }

        OfflinePlayer target = sender.getServer().getOfflinePlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR! " + ChatColor.RESET + "The player " + args[0] + " does not exist or has never joined before.");
            return false;
        }

        UUID playerUUID = target.getUniqueId();

        String banInfo = storage.get("bans." + playerUUID.toString());
        if (Objects.equals(banInfo, "ERR_NOTFOUND")) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ERROR! " + ChatColor.RESET + "The player " + args[0] + " is not banned.");
            return false;
        }

        storage.remove("bans." + playerUUID.toString());

        sender.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "SUCCESS! " + ChatColor.RESET + "The player " + args[0] + " has been unbanned.");




        return true;
    }
}
