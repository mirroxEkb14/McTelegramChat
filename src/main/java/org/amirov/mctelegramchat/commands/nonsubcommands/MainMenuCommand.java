package org.amirov.mctelegramchat.commands.nonsubcommands;

import org.amirov.mctelegramchat.gui.MainMenuGUI;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Opens the main inventory with the items that trigger certain events.
 * <p>
 * Singleton class.
 */
public final class MainMenuCommand implements CommandExecutor {

    private static MainMenuCommand instance;

    public static MainMenuCommand getInstance() {
        if (instance == null)
            return new MainMenuCommand();

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
     * Opens the inventory representing the main menu.
     * <p>
     * This logic was taken out of the main {@code onCommand()} method due to be able to call the method outside this
     * class without creating multiple instances and passing unnecessary arguments.
     *
     * @param sender Player who triggered the command.
     */
    public void onCommand(@NotNull CommandSender sender) {
        if (sender instanceof Player player) {
            MainMenuGUI.openMainMenuGUI(player);
        } else if (sender instanceof ConsoleCommandSender) {
            Loggers.printWarningLog(LoggingMessage.MENU_INVENTORY_CMD_WARNING.getMessage());
        } else if (sender instanceof BlockCommandSender) {
            Loggers.printWarningLog(LoggingMessage.MENU_INVENTORY_COMMAND_BLOCK_WARNING.getMessage());
        }
    }
}
