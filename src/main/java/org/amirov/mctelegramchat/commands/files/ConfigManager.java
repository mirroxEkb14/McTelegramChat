package org.amirov.mctelegramchat.commands.files;

import org.amirov.mctelegramchat.commands.files.properties.ConfigFileKey;
import org.amirov.mctelegramchat.commands.files.properties.ConfigFileName;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Runs the methods for configuration files, such as generating, saving and reloading files.
 */
public final class ConfigManager {

    private static final String PROJECT_NAME = "McTelegramChat";
    /**
     * {@link File} object.
     * <p>
     * The configuration file that is imported directly from the plugin folder.
     */
    private static File file;
    /**
     * {@link FileConfiguration} object.
     * <p>
     * Converted {@code File} object, so that we have access to a bunch of methods of the {@code FileConfiguration} class.
     */
    private static FileConfiguration configFile;

    public static boolean savePlayerLocation(@NotNull UUID playerUUID, String locationName, @NotNull Location location)
            throws ConfigFilesNotSetupException {
        if (file == null || configFile == null)
            throw new ConfigFilesNotSetupException(LoggingMessage.SAVING_CONFIG_FILE_ERROR.getMessage());
        try {
            configFile.set(ConfigFileKey.PLAYER_UUID.getKey(), playerUUID);
            configFile.set(getFullLocationKey(locationName), location);
            configFile.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Contract(pure = true)
    private static @NotNull String getFullLocationKey(String locationName) {
        return ConfigFileKey.PLAYER_LOCATION.getKey() + locationName;
    }

    /**
     * Sets up the file objects
     */
    public static void setupConfigFiles() {
        setupFileObject();
        configFile = new YamlConfiguration();
    }

    private static void setupFileObject() {
        try {
            final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(PROJECT_NAME);
            Objects.requireNonNull(plugin);
            file = new File(plugin.getDataFolder(), ConfigFileName.LOCATION_FILE.getName());
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            Loggers.printSevereLog(LoggingMessage.CREATING_CONFIG_FILE_ERROR.getMessage());
        }
    }
}
