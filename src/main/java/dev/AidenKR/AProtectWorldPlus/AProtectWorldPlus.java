package dev.AidenKR.AProtectWorldPlus;

import dev.AidenKR.AProtectWorldPlus.command.ProtectWorldCommand;
import dev.AidenKR.AProtectWorldPlus.data.ProtectedType;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class AProtectWorldPlus extends JavaPlugin {

    @Getter
    private static AProtectWorldPlus instance = null;

    public static String prefix = "§l§a[!] §r§7";

    private File cacheFile;
    private FileConfiguration configuration;

    @Override
    public void onLoad() {
        instance = this;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        cacheFile = new File(getDataFolder(), "cache.yml");
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventListener(this), this);

        getServer().getCommandMap().register(getName(), new ProtectWorldCommand(this));

        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile();
            } catch (IOException e) {
                getLogger().warning(e.getMessage());
            }
        }
        configuration = YamlConfiguration.loadConfiguration(cacheFile);

        getServer().getWorlds().forEach(world -> {
            String worldName = world.getName().toLowerCase();

            if (!configuration.isConfigurationSection(worldName)) {
                List<ProtectedType> data = List.of(ProtectedType.values());

                for (ProtectedType name : data) {
                    configuration.set(worldName + "." + name.getName(), "off");
                }
            }
        });
    }

    @Override
    public void onDisable() {
        try {
            configuration.save(cacheFile);
        } catch (IOException e) {
            getLogger().warning(e.getMessage());
        }
    }

    public boolean hasProtectWorld(String worldName, String dataName) {
        return configuration.contains(worldName + "." + dataName);
    }

    public String getProtectWorld(String worldName, String dataName) {
        return configuration.getString(worldName + "." + dataName, "off");
    }

    public void setProtectWorld(String worldName, String dataName, String value) {
        configuration.set(worldName + "." + dataName, value);
    }
}
