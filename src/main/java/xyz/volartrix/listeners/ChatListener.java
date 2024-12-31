package xyz.volartrix.listeners;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import xyz.volartrix.util.ChatRenderer;
import xyz.volartrix.util.StaffChatRenderer;

import static xyz.volartrix.util.PublicVars.staffChatEnabled;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (staffChatEnabled.contains(player)) {
            Component message = new StaffChatRenderer().render(player, Component.text(player.getName()), event.message(), Audience.audience(staffChatEnabled));
            for (Player p : staffChatEnabled) {
                p.sendMessage(message);
            }
            event.setCancelled(true);
        } else {
            event.renderer(new ChatRenderer());
        }
    }
}