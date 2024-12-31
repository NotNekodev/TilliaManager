package xyz.volartrix.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import xyz.volartrix.util.Storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class BanListener implements Listener {
    private final Storage storage;

    public BanListener(Storage storage) {
        this.storage = storage;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        String banInfo = storage.get("bans." + playerUUID);
        if (banInfo != null && !banInfo.isEmpty()) {
            String[] parts = banInfo.split("\\|");
            if (parts.length == 3) {
                long unbanTimeMillis = Long.parseLong(parts[0]);
                String reason = parts[1];
                long banTimeMillis = Long.parseLong(parts[2]);

                Objects.requireNonNull(Bukkit.getPlayer("Nekodev")).sendMessage("User: " + player.getName() + "\n Reason: " + reason + "\n Ban Time: " + banTimeMillis + "\n Unban Time: " + unbanTimeMillis);

                long currentTimeMillis = System.currentTimeMillis();
                if (unbanTimeMillis > currentTimeMillis) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, h:mm[ a] yyyy");

                    LocalDateTime unbanDateTime = LocalDateTime.ofEpochSecond(unbanTimeMillis / 1000, 0, java.time.ZoneOffset.UTC);
                    LocalDateTime banDateTime = LocalDateTime.ofEpochSecond(banTimeMillis / 1000, 0, java.time.ZoneOffset.UTC);

                    String formattedUnbanTime = unbanDateTime.format(formatter);
                    String formattedBanTime = banDateTime.format(formatter);

                    player.kick(Component.text("§cYou have been banned from this server!\n\n§fReason: " + reason + "\n§fBanned on: " + formattedBanTime + "\n§fUnban date: " + formattedUnbanTime + "\n\n§cIf you believe this is a mistake, please contact support."));

                } else {
                    storage.remove("bans." + playerUUID);
                }
            }
        }
    }
}