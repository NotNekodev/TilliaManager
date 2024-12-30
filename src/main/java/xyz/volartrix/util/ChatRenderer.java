package xyz.volartrix.util;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class ChatRenderer implements io.papermc.paper.chat.ChatRenderer {
    @Override
    public Component render(Player source, Component sourceDisplayName, Component message, Audience audience) {
        // Fetch the prefix and suffix for the specific player
        String prefix = PlaceholderAPI.setPlaceholders(source, "%luckperms_prefix%");
        String suffix = PlaceholderAPI.setPlaceholders(source, "%luckperms_suffix%");

        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";

        // Convert prefix and suffix into Components that retain their formatting

        String rawPrefix = LegacyComponentSerializer.legacyAmpersand().serialize(Component.text(prefix));
        String rawSuffix = LegacyComponentSerializer.legacyAmpersand().serialize(Component.text(suffix));
        Component prefixComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(rawPrefix);
        Component suffixComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(rawSuffix);

        // Construct the final chat message with prefix, player name, and suffix
        return Component.text("", NamedTextColor.WHITE)
                .append(prefixComponent)
                .append(sourceDisplayName)
                .append(suffixComponent)
                .append(Component.text(": ", NamedTextColor.WHITE))
                .append(message);
    }
}
