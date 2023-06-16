package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.commands.files.ConfigFilesNotSetupException;
import org.amirov.mctelegramchat.commands.files.ConfigManager;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.properties.ChatMessage;
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
public class SaveLocCommand implements CommandExecutor {

    private static final int NAME_WORD_LENGTH = 1;

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length != NAME_WORD_LENGTH) {
                player.sendMessage(ChatMessage.ON_COMMAND_LOCATION_WRONG_COMMAND_ARGUMENTS.getMessage());
                return true;
            }
            try {
                final UUID playerId = player.getUniqueId();
                final String locKeyName = args[0];
                final Location playerLoc = player.getLocation();

                ConfigManager.savePlayerLocation(playerId, locKeyName, playerLoc);
                player.sendMessage(Component.text(ChatMessage.ON_COMMAND_LOCATION_SAVED.getMessage()));
            } catch (ConfigFilesNotSetupException e) {
                Loggers.printSevereLog(LoggingMessage.SAVING_CONFIG_FILE_ERROR.getMessage());
            }
        }
        return true;
    }
}
