package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Restores player's food level to its maximum if the sender of this command either a player himself,
 * someone typed it through the console, or it was executed in the command block.
 */
public final class FeedCommand implements CommandExecutor {

    private static final int MAX_FOOD_LEVEL = 20;

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            player.setFoodLevel(MAX_FOOD_LEVEL);
            player.sendMessage(Component.text(ChatMessage.ON_COMMAND_FEED.getMessage()));
        } else if (sender instanceof ConsoleCommandSender) {
            Loggers.printWarningLog(LoggingMessage.COMMAND_DIE_CMD_WARNING.getMessage());
        } else if (sender instanceof BlockCommandSender) {
            Loggers.printWarningLog(LoggingMessage.COMMAND_DIE_COMMAND_BLOCK_WARNING.getMessage());
        }
        return true;
    }
}
