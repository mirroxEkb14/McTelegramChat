package org.amirov.mctelegramchat.commands.subcommands.management;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.subcommands.ExplodeCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages the subcommands.
 * <p>
 * Command {@code prank} has a bunch of subcommands like {@code explode} or {@code freeze}.
 */
public final class PrankCommandManager implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    /**
     * List of all the subcommands.
     */
    private static final ArrayList<SubCommand> subCommands = new ArrayList<>();

    private static final int LOWER_BOUND_OF_SUBCOMMANDS = 0;
    private static final int SUBCOMMAND_ARGUMENT_INDEX = 0;

    private static final String NO_ARGUMENTS_MESSAGE = "Command Arguments Needed";

    private static final String HELP_MESSAGE_DELIMITER = "-----------------------------------";
    private static final String HELP_MESSAGE_SUBCOMMAND_DELIMITER = " - ";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Public Static Constants">
    public static final String MESSAGE_LINE_DELIMITER = "\n";
//</editor-fold>

    public PrankCommandManager() {
        subCommands.add(new ExplodeCommand());
    }

    /**
     * Whenever a player runs a command, this methods checks if the person who types the command also typed command
     * arguments, that are subcommands. If there are no subcommands typed, the method runs the {@code help} command.
     * Otherwise, when a player specified some of subcommands, the method loops through the list of these subcommands
     * and performs them.
     * <p>
     * If-else blocks explained:
     * <ol>
     * <li> {@code 0} would mean that a player typed just {@code /prank} with no command arguments.
     * <li> {@code 1} would mean a player typed {@code /prank explode}, but without a target's name.
     * <li> {@code 2} would mean that a player typed the command correctly - {@code /prank explode *player name*}.
     * </ol>
     *
     * @param sender Source of the command.
     * @param command Executed command.
     * @param label Alias of the command.
     * @param args Command arguments that were passed.
     *
     * @return {@code true} always due to a valid command was performed.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player performer) {
            if (isSubcommand(args)) {
                performSubcommand(performer, args);
            } else if (isNotSubcommand(args)) {
                askPerformerForArgs(performer);
                sendHelpMessage(performer);
            }
        }
        return true;
    }

    /**
     * Sends a help message of all the subcommands to the performer.
     *
     * @param performer Performer of this command.
     */
    private void sendHelpMessage(@NotNull Player performer) {
        final StringBuilder stringBuilder = new StringBuilder(HELP_MESSAGE_DELIMITER);
        stringBuilder.append(MESSAGE_LINE_DELIMITER);
        for (SubCommand subCommand : subCommands) {
            stringBuilder
                    .append(subCommand.getSyntax())
                    .append(HELP_MESSAGE_SUBCOMMAND_DELIMITER)
                    .append(subCommand.getDescription());
        }
        stringBuilder.append(MESSAGE_LINE_DELIMITER);
        stringBuilder.append(HELP_MESSAGE_DELIMITER);

        performer.sendMessage(stringBuilder.toString());
    }

    /**
     * Sends a message to the command performer that he didn't provide any command arguments.
     *
     * @param performer Player who typed this command.
     */
    private void askPerformerForArgs(@NotNull Player performer) {
        performer.sendMessage(Component.text(
                NO_ARGUMENTS_MESSAGE, NamedTextColor.DARK_RED));
    }

    /**
     * Loops through all the subcommands and checks if the command typed was one of those in the list. If that is the
     * case, performs the subcommand.
     *
     * @param performer Player who triggered the command.
     * @param args Arguments of this command.
     */
    private void performSubcommand(@NotNull Player performer, String @NotNull [] args) {
        for (SubCommand subCommand : subCommands) {
            if (args[SUBCOMMAND_ARGUMENT_INDEX].equalsIgnoreCase(subCommand.getName()))
                subCommand.perform(performer, args);
        }
    }


    /**
     * Determines either a player provided command arguments or not.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true} if there isn't at least one command argument, {@code false} otherwise.
     */
    private boolean isNotSubcommand(String @NotNull [] args) {
        return args.length == LOWER_BOUND_OF_SUBCOMMANDS;
    }

    /**
     * Determines either a player typed more than one word in the command line.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true} if there are more than {@code 0} command arguments typed, {@code false} otherwise.
     */
    private boolean isSubcommand(String @NotNull [] args) {
        return args.length > LOWER_BOUND_OF_SUBCOMMANDS;
    }
}
