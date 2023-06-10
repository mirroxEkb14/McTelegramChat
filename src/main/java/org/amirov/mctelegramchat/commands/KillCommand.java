package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

/**
 * Kills a certain player if the one who runs the command has the appropriate permission.
 * <p>
 * This command runs with the help of targeting players with command arguments.
 */
public final class KillCommand implements CommandExecutor {

    /**
     * key -> UUID of the player.
     * <p>
     * value -> the epoch time of when the player ran the command.
     */
    private final HashMap<UUID, Long> cooldown;

    /**
     * Cooldown time in milliseconds.
     */
    private static final long COOLDOWN_TIME = 10000L;

    public KillCommand() { cooldown = new HashMap<>(); }

    /**
     * Execution principle:
     * Checks if this command was run by a player.
     * <p>
     * Checks the {@code args} argument:
     * <ol>
     * <li> for {@code 0}: sends a message to the player that there should be some command argument typed.
     * <li> for {@code default}: sends a message that there should be only one word after the command itself.
     * <li> for {@code 1}: checks if the target player is on-line; then checks if this player is already in the map,
     * puts him there and kills the target if not, goes to the {@code else} block otherwise; inside the {@code else}
     * block checks if the time that has already passed equals or is more than the {@code COOLDOWN_TIME} constant,
     * updates player's time in the map and kills the target if so, sends a message that the time has not yet passed
     * otherwise.
     * </ol>
     */
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

                    final UUID playerId = player.getUniqueId();
                    if (!cooldown.containsKey(playerId)) {
                        cooldown.put(playerId, System.currentTimeMillis());
                        finishKilling(player, target);
                    } else {
                        final long timeElapsed = System.currentTimeMillis() - cooldown.get(playerId);
                        if (timeElapsed >= COOLDOWN_TIME) {
                            cooldown.put(playerId, System.currentTimeMillis());
                            finishKilling(player, target);
                            return true;
                        }
                        final TextComponent cooldownMessage = Component.text(String.format(
                                ChatMessage.ON_COMMAND_KILL_COOLDOWN.getMessage(), COOLDOWN_TIME - timeElapsed));
                        player.sendMessage(cooldownMessage);
                    }
                }
                default -> sendMessageWrongCommandArguments(player);
            }
        }
        return true;
    }

    /**
     * Kills the target and sends notification to a player who killed and an explanation to the player who was killed.
     *
     * @param player Who ran the command and killed.
     * @param target Who was killed.
     */
    private void finishKilling(Player player, @NotNull Player target) {
        target.setHealth(Double.MIN_VALUE);
        sendNotificationToPlayer(player, target);
        sendExplanationToTarget(target, player);
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
