package org.amirov.mctelegramchat.commands.files;

import org.amirov.mctelegramchat.commands.files.properties.ConfigFileKey;
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

    /**
     * Saves player's location to a custom location file.
     *
     * @param playerUUID Player's unique id.
     * @param locationName Name of this location specified by the player himself.
     * @param location {@link Location} object containing the current location of the player.
     *
     * @return {@code true} if the location was successfully saved, {@code false} otherwise.
     *
     * @throws ConfigFilesNotSetupException Thrown is the method is called when the {@link File} and
     * {@link FileConfiguration} objects are not set in advance.
     *
     * @see #getFullLocationKey(String)
     */
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

    /**
     * Finds and returns the full key name for storing the location for this player.
     *
     * @param locationName Name for this location specified by the player.
     *
     * @return {@link String} representing location key to store this player's location.
     */
    @Contract(pure = true)
    private static @NotNull String getFullLocationKey(String locationName) {
        return ConfigFileKey.PLAYER_LOCATION.getKey() + locationName;
    }

    /**
     * Sets up the file objects
     *
     * @see #setupFileObject()
     */
    public static void setupConfigFiles() {
        setupFileObject();
        configFile = new YamlConfiguration();
    }

    /**
     * Sets up the {@link File} object.
     */
    private static void setupFileObject() {
        try {
            final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(PROJECT_NAME);
            Objects.requireNonNull(plugin);
            file = new File(plugin.getDataFolder(), ConfigFileKey.LOCATION_FILE.getKey());
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            Loggers.printSevereLog(LoggingMessage.CREATING_CONFIG_FILE_ERROR.getMessage());
        }
    }
}
