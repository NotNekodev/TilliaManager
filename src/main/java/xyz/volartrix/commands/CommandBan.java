package xyz.volartrix.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.volartrix.util.Storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CommandBan implements CommandExecutor {

    private final Storage storage;

    public CommandBan(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length < 1) {
            sender.sendMessage(
                    Component.text("Wrong Usage!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD)
                            .append(Component.text(" Usage: /ban <player> <reason>").color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false))
            );
            return false;
        }

        String[] subargs = Arrays.copyOfRange(args, 1, args.length);
        String reason = String.join(" ", subargs);

        OfflinePlayer target = sender.getServer().getOfflinePlayer(args[0]);

        UUID playerUUID = target.getUniqueId();

        long unbanTimeMillis = 9223372036854775807L;
        long banTimeMillis = System.currentTimeMillis();
        storage.set("bans." + playerUUID, unbanTimeMillis + "|" + reason + "|" + banTimeMillis);

        sender.sendMessage(
                Component.text("SUCCESS! ")
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text("The player " + args[0] + " has been banned for " + reason + ".")
                                .color(NamedTextColor.WHITE)
                                .decoration(TextDecoration.BOLD, false))
        );

        if (target.isOnline()) {
            Objects.requireNonNull(target.getPlayer()).kick(Component.text("§cYou have been permanently banned from this server!\n\n§fReason: " + reason + "\n§fBanned on: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM d, h:mm[ a] yyyy")) + "\n§fUnban date: Never\n\n§cIf you believe this is a mistake, please contact support."));
        }


        return true;
    }
}
