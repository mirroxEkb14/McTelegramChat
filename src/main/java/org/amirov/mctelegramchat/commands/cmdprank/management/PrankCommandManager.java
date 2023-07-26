package org.amirov.mctelegramchat.commands.cmdprank.management;

import org.amirov.mctelegramchat.commands.CommandUtils;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.commands.cmdprank.ExplodeCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages the subcommands of the {@code prank} command.
 * <p>
 * Command {@code prank} has a bunch of subcommands like {@code explode} or {@code freeze}.
 */
public final class PrankCommandManager implements CommandExecutor {

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
    public PrankCommandManager() {
        prankSubcommands.add(new ExplodeCommand());
    }
//</editor-fold>

    /**
     * Whenever a player runs a command, these methods checks if the person who types the command also typed command
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
            if (CommandUtils.cmdArgumentsMoreThanZero(args)) {
                CommandUtils.performSubcommand(prankSubcommands, performer, args);
            } else if (CommandUtils.cmdArgumentsZero(args)) {
                CommandUtils.askPerformerForArgs(performer);
                CommandUtils.sendHelpMessage(PRANK_COMMAND_TITLE, prankSubcommands, performer);
            }
        }
        return true;
    }
}
