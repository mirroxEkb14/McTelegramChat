package org.amirov.mctelegramchat.commands.cmdprank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.CommandUtils;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Explodes a player.
 */
public final class ExplodeCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String EXPLODE_COMMAND_NAME = "explode";
    private static final String EXPLODE_COMMAND_DESC = "Explode a Player Into Smithereens";
    private static final String EXPLODE_COMMAND_SYNTAX = "/prank explode <player name>";

    private static final int SUBCOMMAND_ARGUMENT_OF_PLAYER_NAME = 1;
    private static final int LOWER_BOUND_OF_ARGUMENTS_AMOUNT = 1;

    private static final int EXPLODE_SOUND_VOLUME = 1;
    private static final int EXPLODE_SOUND_PITCH = 1;

    private static final String TO_PERFORMER_MESSAGE_SCRATCH = "You Exploded %s";
    private static final String TO_TARGET_MESSAGE_SCRATCH = "You Were Exploded by %s";

    private static final String TO_PERFORMER_NO_NAME_MESSAGE = "No Target Name Provided";

    private static final String TO_PERFORMER_WRONG_NAME_MESSAGE = "Wrong Target Name: ";
//</editor-fold>

    @Contract(pure = true)
    @Override
    public @NotNull String getName() { return EXPLODE_COMMAND_NAME; }

    @Contract(pure = true)
    @Override
    public @NotNull String getDescription() { return EXPLODE_COMMAND_DESC; }

    @Contract(pure = true)
    @Override
    public @NotNull String getSyntax() { return EXPLODE_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String @NotNull [] args) {
        String targetName = "";
        try {
            if (areCommandArguments(args)) {
                targetName = args[SUBCOMMAND_ARGUMENT_OF_PLAYER_NAME];
                final Player target = Bukkit.getPlayer(targetName);

                Objects.requireNonNull(target);
                target.playSound(target.getLocation(),
                        Sound.ENTITY_GENERIC_EXPLODE,
                        EXPLODE_SOUND_VOLUME,
                        EXPLODE_SOUND_PITCH);
                target.setHealth(Double.MIN_VALUE);
                target.sendMessage(Component.text());

                sendNotificationToPerformer(performer, target.getName());
                sendNotificationToTarget(target, performer.getName());
            } else if (isTargetNameProvided(args)) {
                sendNotificationToPerformer(performer);
            }
        } catch (NullPointerException ex) {
            sendMessageWrongPlayerNameToPerformer(performer, targetName);
        }
    }

    /**
     * Sends a message to the performer that we typed a wrong player name.
     *
     * @param performer Performer of this command.
     * @param targetName Name of a player who should be exploded.
     */
    private void sendMessageWrongPlayerNameToPerformer(@NotNull Player performer, String targetName) {
        final TextComponent firstMsgPart = Component.text(TO_PERFORMER_WRONG_NAME_MESSAGE, NamedTextColor.DARK_RED);
        performer.sendMessage(firstMsgPart
                .append(Component.text(targetName, NamedTextColor.BLUE)));
    }

    /**
     * Sends a message to the command performer that he didn't provide a target's name.
     *
     * @param performer Performer of this command.
     */
    private void sendNotificationToPerformer(@NotNull Player performer) {
        final TextComponent firstMsgPart = Component.text(TO_PERFORMER_NO_NAME_MESSAGE, NamedTextColor.DARK_RED);
        performer.sendMessage(firstMsgPart
                .append(Component.text(CommandUtils.MESSAGE_LINE_DELIMITER))
                .append(Component.text(getSyntax(), NamedTextColor.LIGHT_PURPLE)));
    }

    /**
     * Sends a message to the command target that this command was executed with him as a target.
     *
     * @param target Target player of this command.
     * @param performerPlayerName Name of the performer of this command.
     */
    private void sendNotificationToTarget(@NotNull Player target, String performerPlayerName) {
        target.sendMessage(Component.text(
                String.format(TO_TARGET_MESSAGE_SCRATCH, performerPlayerName), NamedTextColor.DARK_RED));
    }

    /**
     * Sends a message to the command performer that the command was successfully executed.
     *
     * @param performer Performer of this command.
     * @param targetPlayerName Name of the target player.
     */
    private void sendNotificationToPerformer(@NotNull Player performer, String targetPlayerName) {
        performer.sendMessage(Component.text(
                String.format(TO_PERFORMER_MESSAGE_SCRATCH, targetPlayerName), NamedTextColor.BLUE));
    }


    /**
     * Determines either a target's name was provided as a command argument or not.
     *
     * @param args Array of command arguments.
     *
     * @return {@code true} if there are more than {@code 1} command arguments, {@code false} otherwise.
     */
    @Contract(pure = true)
    private boolean isTargetNameProvided(String @NotNull [] args) {
        return args.length == LOWER_BOUND_OF_ARGUMENTS_AMOUNT;
    }

    /**
     * Determines either there are command arguments more than 1 (which means except the {@code explode} argument there
     * is also a player's name) or not.
     *
     * @param args Array of command arguments.
     *
     * @return {@code true} if there are command arguments except of a subcommand name, {@code false} otherwise.
     */
    @Contract(pure = true)
    private boolean areCommandArguments(String @NotNull [] args) {
        return args.length > LOWER_BOUND_OF_ARGUMENTS_AMOUNT;
    }
}
