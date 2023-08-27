package org.amirov.mctelegramchat.commands.subcommands.prank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.CommandUtils;
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

    private static final int EXPLODE_SOUND_VOLUME = 1;
    private static final int EXPLODE_SOUND_PITCH = 1;

    private static final String EXPLODE_TARGET_SCRATCH = "You Were Exploded by %s";
    private static final String EXPLODE_KILLER_SCRATCH = "You Exploded %s";
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
        } catch (NullPointerException ex) {
            CommandUtils.sendMessageWrongPlayerNameToPerformer(performer, targetName);
        }
    }

    /**
     * Sends a message to the command target that this command was executed with him as a target.
     *
     * @param target Target player of this command.
     * @param performerPlayerName Name of the performer of this command.
     */
    private void sendNotificationToTarget(@NotNull Player target, String performerPlayerName) {
        target.sendMessage(Component.text(
                String.format(EXPLODE_TARGET_SCRATCH, performerPlayerName),
                NamedTextColor.RED));
    }

    /**
     * Sends a message to the command performer that the command was successfully executed.
     *
     * @param performer Performer of this command.
     * @param targetPlayerName Name of the target player.
     */
    private void sendNotificationToPerformer(@NotNull Player performer, String targetPlayerName) {
        performer.sendMessage(Component.text(
                String.format(EXPLODE_KILLER_SCRATCH, targetPlayerName),
                NamedTextColor.BLUE));
    }
}
