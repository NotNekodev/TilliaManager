package xyz.volartrix;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.volartrix.commands.*;
import xyz.volartrix.listeners.BanListener;
import xyz.volartrix.listeners.ChatListener;
import xyz.volartrix.tabcompleters.*;
import xyz.volartrix.util.Storage;

import java.util.Objects;

public class TilliaManagerPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        saveDefaultConfig();

        Storage storage = new Storage();

        Objects.requireNonNull(this.getCommand("tempban")).setExecutor(new CommandTempban(storage));
        Objects.requireNonNull(this.getCommand("unban")).setExecutor(new CommandUnban(storage));
        Objects.requireNonNull(this.getCommand("ban")).setExecutor(new CommandBan(storage));
        Objects.requireNonNull(this.getCommand("staffchat")).setExecutor(new CommandStaffchat());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new CommandSpawn());
        Objects.requireNonNull(this.getCommand("tp2p")).setExecutor(new CommandTP2P());

        Objects.requireNonNull(this.getCommand("ban")).setTabCompleter(new BanCommandCompleter());
        Objects.requireNonNull(this.getCommand("spawn")).setTabCompleter(new SpawnCommandCompleter());
        Objects.requireNonNull(this.getCommand("staffchat")).setTabCompleter(new StaffChatCommandCompleter());
        Objects.requireNonNull(this.getCommand("tempban")).setTabCompleter(new TempbanCommandCompleter());
        Objects.requireNonNull(this.getCommand("tp2p")).setTabCompleter(new TP2PCommandCompleter());
        Objects.requireNonNull(this.getCommand("unban")).setTabCompleter(new UnbanCommandCompleter(storage));

        Bukkit.getPluginManager().registerEvents(new BanListener(storage), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
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
