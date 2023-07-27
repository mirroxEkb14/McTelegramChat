package org.amirov.mctelegramchat.commands.performers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * This class contains methods and constants that are needed inside this package only.
 */
public final class CommandUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int LOWER_BOUND_OF_SUBCOMMANDS = 0;
    private static final int SUBCOMMAND_ARGUMENT_INDEX = 0;

    private static final String HELP_MESSAGE_CEILING_DELIMITER = "=======";
    private static final String HELP_MESSAGE_FLOOR_DELIMITER = "===================================";
    private static final String HELP_MESSAGE_SUBCOMMAND_DELIMITER = " - ";
    private static final String COMMAND_TITLE_SEPARATOR = " ";

    private static final int COMMAND_TITLE_NAME_WORD_INDEX = 0;
    private static final int COMMAND_TITLE_COMMAND_WORD_INDEX = 1;

    private static final String NO_ARGUMENTS_MESSAGE = "Command Arguments Needed";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Public Static Constants">
    public static final String MESSAGE_LINE_DELIMITER = "\n";
//</editor-fold>

    /**
     * Loops through all the subcommands and checks if the command typed was one of those in the list. If that is the
     * case, performs the subcommand.
     *
     * @param subCommands List of subcommands of some certain command.
     * @param performer Player who triggered the command.
     * @param args Arguments of this command.
     */
    public static void performSubcommand(@NotNull ArrayList<SubCommand> subCommands,
                                         @NotNull Player performer,
                                         String @NotNull [] args) {
        for (SubCommand subCommand : subCommands) {
            if (args[SUBCOMMAND_ARGUMENT_INDEX].equalsIgnoreCase(subCommand.getName()))
                subCommand.perform(performer, args);
        }
    }

    /**
     * Sends a help message of all the subcommands if this certain command to the performer.
     *
     * @param performer Performer of this command.
     */
    public static void sendHelpMessage(String commandTitle,
                                       @NotNull ArrayList<SubCommand> subCommands,
                                       @NotNull Player performer) {
        final String msgTitle = getCommandTitle(commandTitle);
        final StringBuilder stringBuilder = new StringBuilder(msgTitle);
        stringBuilder.append(MESSAGE_LINE_DELIMITER);
        for (SubCommand subCommand : subCommands) {
            final String msgSyntax = ChatColor.YELLOW + subCommand.getSyntax();
            final String msgDesc = ChatColor.GRAY + subCommand.getDescription() + ChatColor.ITALIC;
            stringBuilder
                    .append(msgSyntax)
                    .append(HELP_MESSAGE_SUBCOMMAND_DELIMITER)
                    .append(msgDesc);
        }
        final String floorDelimiter = ChatColor.DARK_RED + HELP_MESSAGE_FLOOR_DELIMITER;
        stringBuilder
                .append(MESSAGE_LINE_DELIMITER)
                .append(floorDelimiter);
        performer.sendMessage(stringBuilder.toString());
    }

    /**
     * Formats and returns the command name.
     *
     * @param commandTitle {@link String} representing a command title.
     *
     * @return Formatted command title as a {@link String}.
     */
    @Contract(pure = true)
    private static @NotNull String getCommandTitle(@NotNull String commandTitle) {
        final String[] splittedTitle = commandTitle.split(COMMAND_TITLE_SEPARATOR);
        return ChatColor.DARK_RED + HELP_MESSAGE_CEILING_DELIMITER +
                ChatColor.BLUE + ChatColor.BOLD + splittedTitle[COMMAND_TITLE_NAME_WORD_INDEX] +
                ChatColor.YELLOW + ChatColor.ITALIC + splittedTitle[COMMAND_TITLE_COMMAND_WORD_INDEX] +
                ChatColor.DARK_RED + HELP_MESSAGE_CEILING_DELIMITER;
    }

    /**
     * Sends a message to the command performer that he didn't provide any command arguments.
     *
     * @param performer Player who typed this command.
     */
    public static void askPerformerForArgs(@NotNull Player performer) {
        performer.sendMessage(Component.text(
                NO_ARGUMENTS_MESSAGE, NamedTextColor.DARK_RED));
    }

    /**
     * Determines either a player provided command arguments or not.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true} if there isn't at least one command argument, {@code false} otherwise.
     */
    public static boolean cmdArgumentsZero(String @NotNull [] args) {
        return args.length == LOWER_BOUND_OF_SUBCOMMANDS;
    }

    /**
     * Determines either a player typed more than one word in the command line.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true} if there are more than {@code 0} command arguments typed, {@code false} otherwise.
     */
    public static boolean cmdArgumentsMoreThanZero(String @NotNull [] args) {
        return args.length > LOWER_BOUND_OF_SUBCOMMANDS;
    }
}
