package xyz.volartrix;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.volartrix.commands.CommandBroadcast;
import xyz.volartrix.commands.CommandTempban;
import xyz.volartrix.listeners.BanListener;
import xyz.volartrix.util.Storage;

import java.util.Objects;

public class TilliaManagerPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        Storage storage = new Storage();

        Bukkit.getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(this.getCommand("tempban")).setExecutor(new CommandTempban(storage));
        this.getCommand("broadcast").setExecutor(new CommandBroadcast());

        Bukkit.getPluginManager().registerEvents(new BanListener(storage), this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = getConfig();

        // Get the custom join message from the config
        String joinMessage = config.getString("join-message", "Welcome %player% to the server!");

        // Replace the placeholder with the player's name
        joinMessage = joinMessage.replace("%player%", event.getPlayer().getName());

        // Set the join message, disable the default yellow message by setting it to null
        event.setJoinMessage(joinMessage.isEmpty() ? null : joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        FileConfiguration config = getConfig();

        // Get the custom leave message from the config
        String quitMessage = config.getString("quit-message", "%player% has left the server.");

        // Replace the placeholder with the player's name
        quitMessage = quitMessage.replace("%player%", event.getPlayer().getName());

        // Set the quit message, disable the default yellow message by setting it to null
        event.setQuitMessage(quitMessage.isEmpty() ? null : quitMessage);
    }
}
