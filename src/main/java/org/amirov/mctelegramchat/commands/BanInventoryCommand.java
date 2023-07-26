package org.amirov.mctelegramchat.commands;

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
public final class BanInventoryCommand implements CommandExecutor {

    private static BanInventoryCommand instance;

//<editor-fold default-state="collapsed" desc="Constructor">
    public static BanInventoryCommand getInstance() {
        if (instance == null)
            return new BanInventoryCommand();

        return instance;
    }
//</editor-fold>

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
