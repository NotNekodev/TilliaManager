package xyz.volartrix.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
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
                    assert player != null;
                    player.teleport(target.getLocation());
                    player.sendMessage(
                            Component.text("You have been teleported to ")
                                    .color(NamedTextColor.GREEN)
                                    .append(Component.text(target.getName())
                                            .color(NamedTextColor.YELLOW))
                                    .append(Component.text(".")
                                            .color(NamedTextColor.GREEN))
                    );
                    target.sendMessage(
                            Component.text(player.getName())
                                    .color(NamedTextColor.YELLOW)
                                    .append(Component.text(" has teleported to you.")
                                            .color(NamedTextColor.GREEN))
                    );
                } else {
                    player.sendMessage(
                            Component.text("ERROR!")
                                    .color(NamedTextColor.RED)
                                    .decorate(TextDecoration.BOLD)
                                    .append(Component.text(" You cannot teleport to yourself.")
                                            .color(NamedTextColor.WHITE)
                                            .decoration(TextDecoration.BOLD, false))
                    );
                }
            } else {
                assert player != null;
                player.sendMessage(
                        Component.text("ERROR!")
                                .color(NamedTextColor.RED)
                                .decorate(TextDecoration.BOLD)
                                .append(Component.text(" Player not found.")
                                        .color(NamedTextColor.WHITE)
                                        .decoration(TextDecoration.BOLD, false))
                );            }
        } else {
            sender.sendMessage(
                    Component.text("Wrong Usage!")
                            .color(NamedTextColor.RED)
                            .decorate(TextDecoration.BOLD)
                            .append(Component.text(" Usage: /tp2p <player>")
                                    .color(NamedTextColor.WHITE)
                                    .decoration(TextDecoration.BOLD, false))
            );
        }

        return true;
    }
}
