package xyz.volartrix.util;

import io.papermc.paper.chat.ChatRenderer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatRenderer implements ChatRenderer {

    @Override
    public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience audience) {
        String prefix = PlaceholderAPI.setPlaceholders(source, "%luckperms_prefix%");
        String suffix = PlaceholderAPI.setPlaceholders(source, "%luckperms_suffix%");


        String rawPrefix = LegacyComponentSerializer.legacyAmpersand().serialize(Component.text(prefix));
        String rawSuffix = LegacyComponentSerializer.legacyAmpersand().serialize(Component.text(suffix));
        Component prefixComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(rawPrefix);
        Component suffixComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(rawSuffix);

        return Component.text("", NamedTextColor.WHITE)
                .append(Component.text("[STAFF] ", NamedTextColor.RED))
                .append(Component.text("", NamedTextColor.WHITE))
                .append(prefixComponent)
                .append(sourceDisplayName)
                .append(suffixComponent)
                .append(Component.text(": ", NamedTextColor.WHITE))
                .append(message);
    }
}
