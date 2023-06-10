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
 * Kills a player if such a command was entered to the chat and if the sender of this command either a player himself,
 * someone typed it through the console, or it was executed in the command block.
 */
public final class DieCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (isDieCommand(command) && sender instanceof Player player) {
            player.setHealth(Double.MIN_VALUE);
            player.sendMessage(Component.text(ChatMessage.ON_COMMAND_DIE.getMessage()));
        } else if (sender instanceof ConsoleCommandSender) {
            Loggers.printWarningLog(LoggingMessage.COMMAND_DIE_CMD_WARNING.getMessage());
        } else if (sender instanceof BlockCommandSender) {
            Loggers.printWarningLog(LoggingMessage.COMMAND_DIE_COMMAND_BLOCK_WARNING.getMessage());
        }
        return true;
    }

    private boolean isDieCommand(@NotNull Command command) {
        return command.getName().equalsIgnoreCase(CommandName.DIE_COMMAND.getName());
    }
}
