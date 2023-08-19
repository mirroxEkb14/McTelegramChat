package org.amirov.mctelegramchat.commands.subcommands.prank;

import org.amirov.mctelegramchat.commands.performers.CommandUtils;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages the subcommands of the {@code prank} command.
 * <p>
 * /prank *explode* *player name*
 * <br>
 * /prank *kill* *player name*
 * <p>
 * Command typing explanation:
 * <ol>
 * <li> Typing only {@code /prank} would trigger sending a warning message.
 * <li> Typing {@code /prank *random text instead of subcommand*} would trigger sending a warning message along with the
 * help message.
 * <li> Typing {@code /prank *correct subcommand* *random text*} would trigger sending a warning message.
 * </ol>
 */
public final class PrankManager implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    /**
     * List of all the subcommands.
     */
    private static final ArrayList<SubCommand> prankSubcommands = new ArrayList<>();

    /**
     * Needed to display a help message.
     */
    private static final String PRANK_COMMAND_TITLE = "Prank Command";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public PrankManager(Plugin plugin) {
        prankSubcommands.add(new ExplodeCommand());
        prankSubcommands.add(new KillCommand(plugin));
    }
//</editor-fold>

    /**
     * Whenever a player runs a command, these methods checks if the person who types the command also typed command
     * arguments, that are subcommands. If there are no subcommands typed, the method runs the {@code help} command.
     * Otherwise, when a player specified a subcommand, the method loops through the list of the subcommands of this
     * command and performs the one specified by the user.
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
            if (CommandUtils.cmdArgumentsZero(args)) {
                CommandUtils.askPerformerForArgs(performer);
            } else if (CommandUtils.cmdArgumentsOne(args)) {
                if (CommandUtils.isSubcommand(prankSubcommands, args)) {
                    CommandUtils.askPerformerForArgs(performer);
                    return true;
                }
                CommandUtils.sendMessageWrongArgument(performer);
                CommandUtils.sendHelpMessage(PRANK_COMMAND_TITLE, prankSubcommands, performer);
            } else if (CommandUtils.cmdArgumentsTwo(args)) {
                CommandUtils.performSubcommand(PRANK_COMMAND_TITLE, prankSubcommands, performer, args);
            } else if (CommandUtils.cmdArgumentsThree(args)) {
                CommandUtils.sendMessageTooManyArguments(performer);
            }
        }
        return true;
    }
}
