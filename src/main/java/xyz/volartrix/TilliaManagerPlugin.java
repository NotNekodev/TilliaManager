package xyz.volartrix;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.volartrix.commands.CommandBan;
import xyz.volartrix.commands.CommandBroadcast;
import xyz.volartrix.commands.CommandTempban;
import xyz.volartrix.commands.CommandUnban;
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
        Objects.requireNonNull(this.getCommand("broadcast")).setExecutor(new CommandBroadcast());
        Objects.requireNonNull(this.getCommand("unban")).setExecutor(new CommandUnban(storage));
        Objects.requireNonNull(this.getCommand("ban")).setExecutor(new CommandBan(storage));

        Bukkit.getPluginManager().registerEvents(new BanListener(storage), this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(Component.empty());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.quitMessage(Component.empty());
    }
}
