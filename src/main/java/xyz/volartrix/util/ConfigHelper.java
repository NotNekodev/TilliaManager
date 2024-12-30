package xyz.volartrix.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigHelper {

    private final FileConfiguration config;

    public ConfigHelper(FileConfiguration config) {
        this.config = config;
    }

    public Location getSpawnLocation(String worldName) {
        World world = Bukkit.getWorld(worldName);
        List<Integer> location = config.getIntegerList("worlds." + worldName);
        return new Location(world, location.get(0), location.get(1), location.get(2));
    }

}
