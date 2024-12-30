package xyz.volartrix.listeners;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.volartrix.util.ChatRenderer;
import xyz.volartrix.util.StaffChatRenderer;

import static xyz.volartrix.util.PublicVars.staffChatEnabled;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        // Fetch the prefix and suffix using PlaceholderAPI
        String prefix = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");
        String suffix = PlaceholderAPI.setPlaceholders(player, "%luckperms_suffix%");

        if (prefix == null) prefix = "";
        if (suffix == null) suffix = "";

        // Check if the sender is in the staff chat
        if (staffChatEnabled.contains(player)) {
            Component message = new StaffChatRenderer().render(player, Component.text(player.getName()), event.message(), Audience.empty());
            for (Player p : staffChatEnabled) {
                p.sendMessage(message);
            }
            event.setCancelled(true);
        } else {
            event.renderer(new ChatRenderer());
        }
    }
}