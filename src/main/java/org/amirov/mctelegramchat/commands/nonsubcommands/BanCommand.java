package org.amirov.mctelegramchat.commands.nonsubcommands;

import org.amirov.mctelegramchat.gui.BanListGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Opens an inventory with the heads of the players on the server currently online.
 * <p>
 * Singleton class.
 */
public final class BanCommand implements CommandExecutor {

    private static BanCommand instance;

    public static BanCommand getInstance() {
        if (instance == null)
            return new BanCommand();

        return instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        onCommand(sender);
        return true;
    }

    /**
     * Performs the command of opening the ban list of players who are currently online.
     * <p>
     * The logic was taken out of the main method {@code onCommand()} to be able to call this command from other parts
     * of the program.
     *
     * @param sender Player who performed the click.
     */
    public void onCommand(@NotNull CommandSender sender) {
        if (sender instanceof Player player)
            BanListGUI.openBanListGUI(player);
    }
}
