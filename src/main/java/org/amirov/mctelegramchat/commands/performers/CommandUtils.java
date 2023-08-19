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
 * This class contains methods to check user's input command arguments and perform the subcommand itself.
 */
public final class CommandUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int SUBCOMMAND_ARGUMENT_INDEX = 0;

    private static final int SUBCOMMAND_ZERO_ARGUMENT = 0;
    private static final int SUBCOMMAND_ONE_ARGUMENT = 1;
    private static final int SUBCOMMAND_TWO_ARGUMENT = 2;
    private static final int SUBCOMMAND_THREE_ARGUMENT = 3;

    private static final String HELP_MESSAGE_CEILING_DELIMITER = "=======";
    private static final String HELP_MESSAGE_FLOOR_DELIMITER = "===================================";
    private static final String HELP_MESSAGE_SUBCOMMAND_DELIMITER = " - ";
    private static final String COMMAND_TITLE_SEPARATOR = " ";

    private static final int COMMAND_TITLE_NAME_WORD_INDEX = 0;
    private static final int COMMAND_TITLE_COMMAND_WORD_INDEX = 1;

    private static final String NO_ARGUMENTS_MESSAGE = "Command Arguments Needed";
    private static final String WRONG_ARGUMENT_MESSAGE =
            ChatColor.RED +  "Wrong Subcommand Typed" + ChatColor.ITALIC;
    private static final String MUCH_ARGUMENTS_MESSAGE =
            ChatColor.RED + "Too Much Command Arguments. See Help Message Below:" + ChatColor.ITALIC;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Public Static Constants">
    public static final String MESSAGE_LINE_DELIMITER = "\n";
//</editor-fold>

    /**
     * Loops through all the subcommands and checks if the command typed was one of those in the list. If that is the
     * case, performs the subcommand, sends a warning message to the performer otherwise.
     *
     * @param commandTitle The title of this command.
     * @param subCommands List of subcommands of some certain command.
     * @param performer Player who triggered the command.
     * @param args Arguments of this command.
     *
     * @see #isSubcommand(ArrayList, String[])
     * @see #sendMessageWrongArgument(Player)
     * @see #sendHelpMessage(String, ArrayList, Player)
     */
    public static void performSubcommand(String commandTitle,
                                         @NotNull ArrayList<SubCommand> subCommands,
                                         @NotNull Player performer,
                                         String @NotNull [] args) {
        for (SubCommand subCommand : subCommands) {
            if (isSubcommand(subCommands, args)){
                subCommand.perform(performer, args);
                return;
            }
            sendMessageWrongArgument(performer);
            sendHelpMessage(commandTitle, subCommands, performer);
            break;
        }
    }

    /**
     * Sends a help message of all the subcommands if this certain command to the performer.
     *
     * @param commandTitle The title of this command.
     * @param subCommands List of subcommands of this particular command.
     * @param performer Performer of this command.
     *
     * @see #getCommandTitle(String)
     */
    private static void sendHelpMessage(String commandTitle,
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
                    .append(msgDesc)
                    .append(MESSAGE_LINE_DELIMITER);
        }
        int messageCurrentLength = stringBuilder.length();
        stringBuilder.deleteCharAt(--messageCurrentLength);
        final String floorDelimiter = ChatColor.DARK_RED + HELP_MESSAGE_FLOOR_DELIMITER;
        stringBuilder
                .append(MESSAGE_LINE_DELIMITER)
                .append(floorDelimiter);
        performer.sendMessage(stringBuilder.toString());
    }

    /**
     * Determines either user's typed argument is actually a subcommand or just random text.
     *
     * @param subCommands List of subcommands.
     * @param args User's typed arguments for this command.
     *
     * @return {@code true}, if this user typed a real subcommand, {@code false} otherwise.
     */
    @Contract(pure = true)
    public static boolean isSubcommand(@NotNull ArrayList<SubCommand> subCommands,
                                       String @NotNull [] args) {
        for (SubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(args[SUBCOMMAND_ARGUMENT_INDEX])) { return true; }
        }
        return false;
    }

    /**
     * Sends a message about wrong subcommand typed by this user.
     *
     * @param performer Player who performed the command.
     */
    public static void sendMessageWrongArgument(@NotNull Player performer) {
        performer.sendMessage(WRONG_ARGUMENT_MESSAGE);
    }

    /**
     * Sends a message about too many arguments that the player typed.
     *
     * @param performer Player who performed the command.
     */
    public static void sendMessageTooManyArguments(@NotNull Player performer) {
        performer.sendMessage(MUCH_ARGUMENTS_MESSAGE);
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
        performer.sendMessage(Component.text(NO_ARGUMENTS_MESSAGE, NamedTextColor.DARK_RED));
    }

    /**
     * Determines either a player provided command arguments or not.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true}, if the command arguments' length is zero, {@code false} otherwise.
     */
    public static boolean cmdArgumentsZero(String @NotNull [] args) {
        return args.length == SUBCOMMAND_ZERO_ARGUMENT;
    }

    /**
     * Determines either a player typed only one command argument.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true}, if the command argument's length is one, {@code false} otherwise.
     */
    public static boolean cmdArgumentsOne(String @NotNull [] args) {
        return args.length == SUBCOMMAND_ONE_ARGUMENT;
    }

    /**
     * Determines either a player typed two command arguments - no more, no less.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true}, if the command argument's length is two, {@code false} otherwise.
     */
    public static boolean cmdArgumentsTwo(String @NotNull [] args) {
        return args.length == SUBCOMMAND_TWO_ARGUMENT;
    }


    /**
     * Determines either a player typed three command arguments - no more, no less.
     *
     * @param args Command arguments as an array of {@link String}s.
     *
     * @return {@code true}, if the command argument's length is three, {@code false} otherwise.
     */
    public static boolean cmdArgumentsThree(String @NotNull [] args) { return args.length == SUBCOMMAND_THREE_ARGUMENT; }
}
