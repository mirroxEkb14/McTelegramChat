package org.amirov.mctelegramchat.commands.nonsubcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.files.ConfigFilesNotSetupException;
import org.amirov.mctelegramchat.commands.files.ConfigManager;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Saves the current player's location to a custom configuration file.
 */
public final class SaveLocationCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int NAME_WORD_LENGTH = 1;
    private static final int LOCATION_KEY_INDEX = 0;

    private static final TextComponent WRONG_COMMAND_ARGUMENTS = Component.text(
            "Only One Word for Point Name", NamedTextColor.RED);
    private static final TextComponent LOCATION_SAVED = Component.text(
            "Location Point Saved", NamedTextColor.GREEN);
//</editor-fold>

    /**
     * Checks the presence of command arguments and either sends a message that no arguments were typed or saves this
     * player's location to a custom file.
     *
     * @param sender Source of the command.
     * @param command Executed command itself.
     * @param label Alias of the used command.
     * @param args Command arguments.
     *
     * @return {@code true} always due to this command is always valid.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length != NAME_WORD_LENGTH) {
                player.sendMessage(WRONG_COMMAND_ARGUMENTS);
                return true;
            }
            try {
                final UUID playerId = player.getUniqueId();
                final String locKeyName = args[LOCATION_KEY_INDEX];
                final Location playerLocation = player.getLocation();

                ConfigManager.savePlayerLocation(playerId, locKeyName, playerLocation);
                player.sendMessage(LOCATION_SAVED);
            } catch (ConfigFilesNotSetupException e) {
                Loggers.printSevereLog(LoggingMessage.SAVING_CONFIG_FILE_ERROR.getMessage());
            }
        }
        return true;
    }
}
