package xyz.volartrix.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.volartrix.TilliaManagerPlugin;

public class CommandSpawn implements CommandExecutor {
    private final FileConfiguration config;

    public CommandSpawn(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Location spawn = new Location(Bukkit.getWorld("world"), 0.0, -58, 0.0);
        Player player = (Player) sender;
        player.teleport(spawn);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10F, 1.0F);

        player.sendMessage(Component.text("You have been teleported to the spawn!", NamedTextColor.GREEN));
        return true;
    }
}
