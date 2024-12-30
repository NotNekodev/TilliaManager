package xyz.volartrix.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import xyz.volartrix.util.Storage;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

public class BanListener implements Listener {
    private final Storage storage; // Reference to the Storage class

    public BanListener(Storage storage) {
        this.storage = storage; // Initialize the storage
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // Check if the player has a ban record
        String banInfo = storage.get("bans." + playerUUID);
        if (banInfo != null && !banInfo.isEmpty()) {
            // Parse the ban info: unbanTime|reason|banTime
            String[] parts = banInfo.split("\\|");
            if (parts.length == 3) {
                long unbanTimeMillis = Long.parseLong(parts[0]);
                String reason = parts[1];
                long banTimeMillis = Long.parseLong(parts[2]);

                Objects.requireNonNull(Bukkit.getPlayer("Nekodev")).sendMessage("User: " + player.getName() + "\n Reason: " + reason + "\n Ban Time: " + banTimeMillis + "\n Unban Time: " + unbanTimeMillis);

                // Check if the ban is still active
                long currentTimeMillis = System.currentTimeMillis();
                if (unbanTimeMillis > currentTimeMillis) {
                    if (unbanTimeMillis == -1) {
                        // Ban is still active, kick the player
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, h:mm[ a] yyyy");

                        // Convert the times from milliseconds to LocalDateTime
                        LocalDateTime banDateTime = LocalDateTime.ofEpochSecond(banTimeMillis / 1000, 0, ZoneOffset.UTC);

                        String formattedBanTime = banDateTime.format(formatter);
                        player.kick(Component.text("§cYou have been permanently banned from this server!\n\n§fReason: " + reason + "\n§fBanned on: " + formattedBanTime + "\n§fUnban date: Never\n\n§cIf you believe this is a mistake, please contact support."));
                    } else {
                        // Ban is still active, kick the player
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, h:mm[ a] yyyy");

                        // Convert the times from milliseconds to LocalDateTime
                        LocalDateTime unbanDateTime = LocalDateTime.ofEpochSecond(unbanTimeMillis / 1000, 0, java.time.ZoneOffset.UTC);
                        LocalDateTime banDateTime = LocalDateTime.ofEpochSecond(banTimeMillis / 1000, 0, java.time.ZoneOffset.UTC);

                        // Format the date and time
                        String formattedUnbanTime = unbanDateTime.format(formatter);
                        String formattedBanTime = banDateTime.format(formatter);

                        // Kick the player with the formatted message
                        player.kick(Component.text("§cYou have been banned from this server!\n\n§fReason: " + reason + "\n§fBanned on: " + formattedBanTime + "\n§fUnban date: " + formattedUnbanTime + "\n\n§cIf you believe this is a mistake, please contact support."));
                    }
                } else {
                    // Ban has expired, remove the ban data
                    storage.remove("bans." + playerUUID);
                }
            }
        }
    }
}