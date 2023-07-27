package org.amirov.mctelegramchat.commands.nonsubcommands;

import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A player who triggers the command becomes invisible to other players on the server.
 */
public record VanishCommand(McTelegramChat plugin) implements CommandExecutor {

    /**
     * Makes the player invisible to the others.
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
            if (plugin.getInvisibleList().contains(player)) {
                removePlayerFromVanishList(player);
                return true;
            }
            addPlayerToVanishList(player);
        }
        return true;
    }

    /**
     * Makes the passed player visible again.
     *
     * @param player Player to be visible.
     */
    private void removePlayerFromVanishList(@NotNull Player player) {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            onlinePlayers.showPlayer(plugin, player);
        }
        plugin.getInvisibleList().remove(player);
        player.sendMessage(ChatMessage.ON_COMMAND_VANISH_REMOVE.getMessage());
    }

    /**
     * Makes the passed player invisible.
     *
     * @param player Player to be invisible.
     */
    private void addPlayerToVanishList(@NotNull Player player) {
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            onlinePlayers.showPlayer(plugin, player);
        }
        plugin.getInvisibleList().add(player);
        player.sendMessage(ChatMessage.ON_COMMAND_VANISH_ADD.getMessage());
    }
}
