package org.amirov.mctelegramchat.commands.subcommands.admin;

import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.commands.performers.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Manages all the subcommands of the {@code admin} command.
 * <p>
 * /admin *ban*
 * <p>
 * Command typing explanation:
 * <ol>
 * <li> Typing only {@code /admin} would trigger sending a warning message.
 * <li> Typing {@code /admin *random text instead of subcommand*} would trigger sending a warning message along with the
 * help message.
 * <li> Typing {@code /admin *correct subcommand* *random text*} would trigger sending a warning message.
 * </ol>
 */
public final class AdminManager implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    /**
     * List of all the subcommands.
     */
    private static final ArrayList<SubCommand> adminSubcommands = new ArrayList<>();

    /**
     * Needed to display a help message.
     */
    private static final String ADMIN_COMMAND_TITLE = "Admin Command";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public AdminManager() {
        adminSubcommands.add(new BanCommand());
    }
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player performer) {
            if (CommandUtils.cmdArgumentsZero(args)) {
                CommandUtils.askPerformerForArgs(performer);
            } else if (CommandUtils.cmdArgumentsOne(args)) {
                if (CommandUtils.isSubcommand(adminSubcommands, args)) {
                    CommandUtils.performSubcommand(ADMIN_COMMAND_TITLE, adminSubcommands, performer, args);
                    return true;
                }
                CommandUtils.sendMessageWrongArgument(performer);
                CommandUtils.sendHelpMessage(ADMIN_COMMAND_TITLE, adminSubcommands, performer);
            } else {
                CommandUtils.sendMessageTooManyArguments(performer);
            }
        }
        return true;
    }
}
