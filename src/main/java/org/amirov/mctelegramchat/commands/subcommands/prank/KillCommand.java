package org.amirov.mctelegramchat.commands.subcommands.prank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.commands.performers.CommandUtils;
import org.amirov.mctelegramchat.strings.ConfigProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

/**
 * Kills a certain player if the one who runs the command has the appropriate permission.
 * <p>
 * This command runs with the help of targeting players with command arguments.
 */
public final class KillCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String KILL_COMMAND_NAME = "kill";
    private static final String KILL_COMMAND_DESC = "Kill a Player Instantly";
    private static final String KILL_COMMAND_SYNTAX = "/prank kill <player name>";

    private static final int PLAYER_NAME_INDEX = 1;

    private static final String KILL_COOLDOWN = "Cooldown Time %d";
    private static final String KILL_NO_PERMISSION = "No Permission";
    private static final String KILL_NOTIFICATION = "Player %s Was Killed";
    private static final String KILL_EXPLANATION = "You Were Killed by %s";

    /**
     * key -> UUID of the player.
     * <p>
     * value -> the epoch time of when the player ran the command.
     */
    private static final HashMap<UUID, Long> cooldown = new HashMap<>();

    /**
     * Cooldown time in milliseconds.
     */
    private static final long COOLDOWN_TIME = 10000L;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Private Instance Variables">
    private final Plugin plugin;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public KillCommand(Plugin plugin) { this.plugin = plugin; }
//</editor-fold>

    @Override
    public @NotNull String getName() { return KILL_COMMAND_NAME; }

    @Override
    public @NotNull String getDescription() { return KILL_COMMAND_DESC; }

    @Override
    public @NotNull String getSyntax() { return KILL_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String[] args) {
        if (plugin.getConfig().getBoolean(ConfigProperty.PLAYER_KILL.getKeyName())) {
            String targetName = "";
            try {
                targetName = args[PLAYER_NAME_INDEX];
                final Player target = Bukkit.getServer().getPlayerExact(targetName);
                final UUID playerId = performer.getUniqueId();
                if (!cooldown.containsKey(playerId)) {
                    cooldown.put(playerId, System.currentTimeMillis());
                    finishKilling(performer, target);
                } else {
                    final long timeElapsed = System.currentTimeMillis() - cooldown.get(playerId);
                    if (timeElapsed >= COOLDOWN_TIME) {
                        cooldown.put(playerId, System.currentTimeMillis());
                        finishKilling(performer, target);
                    }
                    final TextComponent cooldownMessage = Component.text(String.format(
                            KILL_COOLDOWN, COOLDOWN_TIME - timeElapsed));
                    performer.sendMessage(cooldownMessage);
                }
            } catch (NullPointerException e) {
                CommandUtils.sendMessageWrongPlayerNameToPerformer(performer, targetName);
            }
        } else {
            performer.sendMessage(KILL_NO_PERMISSION);
        }
    }

    /**
     * Kills the target and sends notification to a player who killed and an explanation to the player who was killed.
     *
     * @param player Who ran the command and killed.
     * @param target Who was killed.
     *
     * @see #sendNotificationToPlayer(Player, Player)
     * @see #sendExplanationToTarget(Player, Player)
     */
    private void finishKilling(Player player, @NotNull Player target) {
        target.setHealth(Double.MIN_VALUE);
        sendNotificationToPlayer(player, target);
        sendExplanationToTarget(target, player);
    }

    /**
     * Sends a message notification to the player that the player under the nickname he specified in the command
     * was successfully killed.
     *
     * @param player A player who runs the command.
     * @param target A player who was killed.
     */
    private void sendNotificationToPlayer(@NotNull Player player, @NotNull Player target) {
        final String message = String.format(KILL_NOTIFICATION, target.getName());
        final TextComponent notification = Component.text(message, NamedTextColor.BLUE);
        player.sendMessage(notification);
    }

    /**
     * Sends a message explanation to the player that he was killed by another player.
     *
     * @param target A player who was killed by using this command.
     * @param player A player who run the command.
     */
    private void sendExplanationToTarget(@NotNull Player target, @NotNull Player player) {
        final String message = String.format(KILL_EXPLANATION, player.getName());
        final TextComponent explanation = Component.text(message, NamedTextColor.RED);
        target.sendMessage(explanation);
    }
}
