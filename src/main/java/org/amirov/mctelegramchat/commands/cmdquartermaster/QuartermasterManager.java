package org.amirov.mctelegramchat.commands.cmdquartermaster;

import org.amirov.mctelegramchat.commands.performers.CommandUtils;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages the subcommands of the "quartermaster" plugin.
 * <p>
 * /qm *manage* *command argument 2*
 */
public final class QuartermasterManager implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final ArrayList<SubCommand> qmSubcommands = new ArrayList<>();

    /**
     * Needed to display a help message.
     */
    private static final String QUARTERMASTER_COMMAND_TITLE = "Quartermaster Command";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public QuartermasterManager() {
        qmSubcommands.add(new LockCommand());
        qmSubcommands.add(new LockListCommand());
    }
//</editor-fold>

    /**
     * Checks either a player who typed the command specified any command arguments. If that is the case, the method
     * loops through the list of the subcommands of this certain command and performs this subcommand. Otherwise, if
     * the command performer didn't specify any arguments, this method rung a help command.
     * <p>
     * If-else blocks explained:
     * <ol>
     * <li> {@code 0} would mean that a player typed just {@code /qm} with no command arguments.
     * <li> {@code 1} would mean a player typed {@code /qm lock} or {@code /qm manage}.
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
                CommandUtils.performSubcommand(qmSubcommands, performer, args);
            } else if (CommandUtils.cmdArgumentsZero(args)) {
                CommandUtils.askPerformerForArgs(performer);
                CommandUtils.sendHelpMessage(QUARTERMASTER_COMMAND_TITLE, qmSubcommands, performer);
            }
        }
        return true;
    }
}
