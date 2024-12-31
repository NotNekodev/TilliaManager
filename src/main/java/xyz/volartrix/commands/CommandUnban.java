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

import java.util.Objects;
import java.util.UUID;

public class CommandUnban implements CommandExecutor {

    private final Storage storage;

    public CommandUnban(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (args.length != 1) {
            sender.sendMessage(
                    Component.text("Wrong Usage!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD)
                            .append(Component.text(" Usage: /unban <player>")
                                    .color(NamedTextColor.WHITE)
                                    .decoration(TextDecoration.BOLD, false))
            );
        }

        OfflinePlayer target = sender.getServer().getOfflinePlayer(args[0]);

        UUID playerUUID = target.getUniqueId();

        String banInfo = storage.get("bans." + playerUUID);
        if (Objects.equals(banInfo, "ERR_NOTFOUND")) {
            sender.sendMessage(
                    Component.text("ERROR! ")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD)
                            .append(Component.text("The player " + args[0] + " is not banned.")
                                    .color(NamedTextColor.WHITE)
                                    .decoration(TextDecoration.BOLD, false))
            );
            return false;
        }

        storage.remove("bans." + playerUUID);

        sender.sendMessage(
                Component.text("SUCCESS! ")
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD)
                        .append(Component.text("The player " + args[0] + " has been unbanned.")
                                .color(NamedTextColor.WHITE)
                                .decoration(TextDecoration.BOLD, false))
        );



        return true;
    }
}
