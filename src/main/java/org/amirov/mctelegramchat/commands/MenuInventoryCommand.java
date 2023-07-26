package org.amirov.mctelegramchat.commands;

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
public final class MenuInventoryCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Static Instance Variables">
    private static MenuInventoryCommand instance;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public static MenuInventoryCommand getInstance() {
        if (instance == null)
            return new MenuInventoryCommand();

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
