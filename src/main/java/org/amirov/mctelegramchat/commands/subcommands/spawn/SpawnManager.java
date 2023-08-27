package org.amirov.mctelegramchat.commands.subcommands.spawn;

import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.commands.performers.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages the subcommands of the {@code spawn} command.
 * <p>
 * /spawn *set*
 * <br>
 * /spawn *me*
 * <br>
 * /spawn *sign* *row number from 1 to 4* *text*
 * <p>
 * Command typing explanation:
 * <ol>
 * <li> Typing only {@code /spawn} would trigger sending a warning message.
 * <li> Typing {@code /spawn *random text instead of subcommand*} would trigger sending a warning message along with the
 * help message.
 * <li> Typing {@code /spawn *correct subcommand* *random text*} would trigger sending a warning message.
 * </ol>
 */
public final class SpawnManager implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    /**
     * List of all the subcommands.
     */
    private static final ArrayList<SubCommand> spawnSubcommands = new ArrayList<>();

    /**
     * Needed to display a help message.
     */
    private static final String SPAWN_COMMAND_TITLE = "Spawn Command";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public SpawnManager(Plugin plugin) {
        spawnSubcommands.add(new SetSpawnCommand(plugin));
        spawnSubcommands.add(new TpSpawnCommand(plugin));
        spawnSubcommands.add(new SignSpawnCommand());
    }
//</editor-fold>

    /**
     * A series of {@code if-else} blocks checks, either the player specified any command arguments or not. According to
     * it, sends a warning messages, asks for more arguments, sends a help message or performs specified subcommand.
     *
     * @param sender Source of the command.
     * @param command Command which was executed.
     * @param label Alias of the command which was used.
     * @param args Passed command arguments.
     *
     * @return {@code true} since it is always a valid command.
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
                if (CommandUtils.isSubcommand(spawnSubcommands, args)) {
                    CommandUtils.performSubcommand(SPAWN_COMMAND_TITLE, spawnSubcommands, performer, args);
                    return true;
                }
                CommandUtils.sendMessageWrongArgument(performer);
                CommandUtils.sendHelpMessage(SPAWN_COMMAND_TITLE, spawnSubcommands, performer);
            } else {
                final String subcommandName = args[CommandUtils.getSubcommandArgumentIndex()];
                if (SignSpawnCommand.isSignSpawnSubcommand(subcommandName)) {
                    CommandUtils.performSubcommand(SPAWN_COMMAND_TITLE, spawnSubcommands, performer, args);
                    return true;
                }
                CommandUtils.sendMessageTooManyArguments(performer);
            }
        }
        return true;
    }
}
