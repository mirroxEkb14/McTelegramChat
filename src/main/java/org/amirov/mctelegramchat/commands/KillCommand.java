package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Kills a certain player if the one who runs the command has the appropriate permission.
 * <p>
 * This command runs with the help of targeting players with command arguments.
 */
public final class KillCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            switch (args.length) {
                case 0 -> sendMessageNoName(player);
                case 1 -> {
                    final String playerName = args[0];
                    final Player target = Bukkit.getServer().getPlayerExact(playerName);
                    if (target == null) {
                        sendMessagePlayerOffline(player);
                        break;
                    }
                    target.setHealth(Double.MIN_VALUE);
                    sendNotificationToPlayer(player, target);
                    sendExplanationToTarget(target, player);
                }
                default -> sendMessageWrongCommandArguments(player);
            }
        }
        return true;
    }

    /**
     * Sends a message to the game chat that the player did not specify the player's name he wishes to kill.
     *
     * @param player A player who runs the command.
     */
    private void sendMessageNoName(@NotNull Player player) {
        final TextComponent message = Component.text(ChatMessage.ON_COMMAND_KILL_NO_NAME.getMessage());
        player.sendMessage(message);
    }

    /**
     * Sends a message to the player that he specified incorrect player's name.
     *
     * @param player A player who runs the command.
     */
    private void sendMessageWrongCommandArguments(@NotNull Player player) {
        final TextComponent explanation = Component.text(ChatMessage.ON_COMMAND_KILL_WRONG_COMMAND_ARGUMENTS.getMessage());
        player.sendMessage(explanation);
    }

    /**
     * Sends a message to the player that the player he wishes to kill is currently off-line.
     *
     * @param player A player who runs the command.
     */
    private void sendMessagePlayerOffline(@NotNull Player player) {
        final TextComponent message = Component.text(ChatMessage.ON_COMMAND_KILL_PLAYER_OFFLINE.getMessage());
        player.sendMessage(message);
    }

    /**
     * Sends a message notification to the player that the player under the nickname he specified in the command
     * was successfully killed.
     *
     * @param player A player who runs the command.
     * @param target A player who was killed.
     */
    private void sendNotificationToPlayer(@NotNull Player player, @NotNull Player target) {
        final String message = String.format(ChatMessage.ON_COMMAND_KILL_NOTIFICATION.getMessage(), target.getName());
        final TextComponent notification = Component.text(message);
        player.sendMessage(notification);
    }

    /**
     * Sends a message explanation to the player that he was killed by another player.
     *
     * @param target A player who was killed by using this command.
     * @param player A player who run the command.
     */
    private void sendExplanationToTarget(@NotNull Player target, @NotNull Player player) {
        final String message = String.format(ChatMessage.ON_COMMAND_KILL_EXPLANATION.getMessage(), player.getName());
        final TextComponent explanation = Component.text(message);
        target.sendMessage(explanation);
    }
}
