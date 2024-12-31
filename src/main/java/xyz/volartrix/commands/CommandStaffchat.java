package xyz.volartrix.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
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
            sender.sendMessage(
                    Component.text("Only players can execute this command.")
                            .color(NamedTextColor.RED)
            );
            return true;
        }

        Player player = Bukkit.getPlayer(sender.getName());

        if (args.length != 0) {
            String message = String.join(" ", args);
            String prefix = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");
            String suffix = PlaceholderAPI.setPlaceholders(player, "%luckperms_suffix%");

            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.hasPermission("bozo.staffchat")) {
                    assert player != null;
                    Component formattedMessage = Component.text()
                            .append(Component.text("[STAFF] ").color(NamedTextColor.RED))
                            .append(LegacyComponentSerializer.legacyAmpersand().deserialize(prefix))
                            .append(Component.text(player.getName()))
                            .append(LegacyComponentSerializer.legacyAmpersand().deserialize(suffix))
                            .append(Component.text(": " + message))
                            .build();

                    target.sendMessage(
                            formattedMessage
                    );
                }
            }

        } else {
            if (staffChatEnabled.contains(player)) {
                staffChatEnabled.remove(player);
                assert player != null;
                player.sendMessage(
                        Component.text("Staff chat disabled!")
                                .color(NamedTextColor.RED)
                );
            } else {
                staffChatEnabled.add(player);
                assert player != null;
                player.sendMessage(
                        Component.text("Staff chat enabled!")
                                .color(NamedTextColor.GREEN)
                );
            }
        }

        return true;
    }
}
