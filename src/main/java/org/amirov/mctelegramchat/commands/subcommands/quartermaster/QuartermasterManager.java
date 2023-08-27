package org.amirov.mctelegramchat.commands.subcommands.quartermaster;

import org.amirov.mctelegramchat.commands.performers.CommandUtils;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages the subcommands of the {@code quartermaster} plugin.
 * <p>
 * /qm *lock*
 * <br>
 * /qm *manage*
 * <p>
 * Command typing explanation:
 * <ol>
 * <li> Typing only {@code /qm} would trigger sending a warning message.
 * <li> Typing {@code /qm *random text instead of subcommand*} would trigger sending a warning message along with the
 * help message.
 * <li> Typing {@code /qm *correct subcommand* *random text*} would trigger sending a warning message.
 * </ol>
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
     * the command performer didn't specify any arguments, this method calls a help command.
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
                CommandUtils.performSubcommand(QUARTERMASTER_COMMAND_TITLE, qmSubcommands, performer, args);
            } else {
                CommandUtils.sendMessageTooManyArguments(performer);
            }
        }
        return true;
    }
}
