package xyz.volartrix.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.volartrix.util.Storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CommandTempban implements CommandExecutor {

    private final Storage storage;

    public CommandTempban(Storage storage) {
        this.storage = storage;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
         if (args.length < 3) {
             sender.sendMessage(
                     Component.text("Wrong Usage!")
                             .color(NamedTextColor.RED)
                             .decorate(TextDecoration.BOLD)
                             .append(Component.text(" Usage: /tempban <player> <duration>[unit: s|m|h|d|w|y] <reason>")
                                     .color(NamedTextColor.WHITE)
                                     .decoration(TextDecoration.BOLD, false))
             );
             return false;
         }

         String[] subargs = Arrays.copyOfRange(args, 2, args.length);
         String reason = String.join(" ", subargs);

        OfflinePlayer target = sender.getServer().getOfflinePlayer(args[0]);

        UUID playerUUID = target.getUniqueId();

         String durationArg = args[1];
         int duration = Integer.parseInt(durationArg.replaceAll("[^0-9]", ""));
         String unit = durationArg.replaceAll("[0-9]", "").toLowerCase();
         int durationInSeconds;

        switch (unit) {
            case "s":  // seconds
                durationInSeconds = duration;
                break;
            case "m":  // minutes
                durationInSeconds = duration * 60;
                break;
            case "h":  // hours
                durationInSeconds = duration * 60 * 60;
                break;
            case "d":  // days
                durationInSeconds = duration * 24 * 60 * 60;
                break;
            case "w":  // weeks
                durationInSeconds = duration * 7 * 24 * 60 * 60;
                break;
            case "y":  // years
                durationInSeconds = duration * 365 * 24 * 60 * 60;
                break;
            default:
                sender.sendMessage(
                        Component.text("ERROR!")
                                .color(NamedTextColor.RED)
                                .decorate(TextDecoration.BOLD)
                                .append(Component.text(" Invalid time unit! Use s (seconds), m (minutes), h (hours), d (days), w (weeks), or y (years).")
                                        .color(NamedTextColor.WHITE)
                                        .decoration(TextDecoration.BOLD, false))
                );
                return false;
        }


        // Set ban details
        long currentTime = System.currentTimeMillis();
        long unbanTime = currentTime + (durationInSeconds * 1000L);

        // Save ban information using Storage
        String banInfo = unbanTime + "|" + reason + "|" + currentTime;
        storage.set("bans." + playerUUID, banInfo);

        // Format the unban time and ban time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, h:mm[ a] yyyy");
        LocalDateTime unbanDateTime = LocalDateTime.ofEpochSecond(unbanTime / 1000, 0, java.time.ZoneOffset.UTC);
        LocalDateTime banDateTime = LocalDateTime.ofEpochSecond(currentTime / 1000, 0, java.time.ZoneOffset.UTC);

        String formattedUnbanTime = unbanDateTime.format(formatter);
        String formattedBanTime = banDateTime.format(formatter);

        sender.sendMessage(
                Component.text("SUCCESS!")
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text(" The player " + args[0] + " has been banned for " + duration + unit + " for " + reason + ".")
                                .color(NamedTextColor.WHITE)
                                .decoration(TextDecoration.BOLD, false))
        );

        if (target.isOnline()) {
            Player oTarget = Bukkit.getPlayer(playerUUID);
            Objects.requireNonNull(oTarget).kick(Component.text("§cYou have been banned from this server!\n\n§fReason: " + reason + "\n§fBanned on: " + formattedBanTime + "\n§fUnban date: " + formattedUnbanTime + "\n\n§cIf you believe this is a mistake, please contact support."));
        }

        return true;
    }

}
