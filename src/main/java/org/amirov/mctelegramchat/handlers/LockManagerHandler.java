package org.amirov.mctelegramchat.handlers;

import org.amirov.mctelegramchat.gui.*;
import org.amirov.mctelegramchat.gui.enums.ConfirmationGUIConstants;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes the events of clicking on items inside the lock manager menu.
 */
public final class LockManagerHandler {

    /**
     * if-else blocks explained:
     * <lo>
     * <li> "access manager" button, the player opens up a new inventory menu of "lock access manager".
     * <li> "delete" button, a confirmation gui pops up.
     * <li> "info" button, nothing happens except a new log to the console.
     * <li> "close" button, the player goes back to the "lock list" gui.
     * <li> "glass panel" block was clicked on, a log of this event is printed to the console, for the player nothing
     * happens.
     * </lo>
     *
     * @param event Event of a player clicking on some item in this menu.
     * @param player Player who triggered the event.
     * @param currentItem Item that this player clicked on.
     */
    public static void performLockManagerClick(@NotNull InventoryClickEvent event,
                                               @NotNull Player player,
                                               @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (isManageAccessButton(currentItem)) {
            LockAccessManagerGUI.openLockAccessManagerGUI(player);
        } else if (isDeleteButton(currentItem)) {
            LockDeleteConfirmationGUI.openLockDeleteConfirmationGUI(player);
        } else if (isInfoButton(currentItem)) {
            Loggers.printInfoLog(LoggingMessage.LOCK_ACCESS_MANAGER_INFO_BUTTON_CLICKED.getMessage());
        } else if (ConfirmationGUIConstants.isCloseButton(currentItem)) {
            LockListGUI.openLockListGUI(player);
        } else {
            Loggers.printInfoLog(LoggingMessage.LOCK_ACCESS_MANAGER_OTHER_BUTTON_CLICKED.getMessage());
        }
    }

    /**
     * Checks either the item a player clicked on was a "manage access" button.
     *
     * @param item Item that was clicked on inside the menu.
     *
     * @return {@code true} if that was the case, {@code false} otherwise.
     */
    private static boolean isManageAccessButton(@NotNull ItemStack item) {
        return item.getType().equals(LockManagerGUI.getManageAccessName());
    }


    /**
     * Checks either the item a player clicked on was a "delete" button.
     *
     * @param item Item that was clicked on inside the menu.
     *
     * @return {@code true} if that was the case, {@code false} otherwise.
     */
    private static boolean isDeleteButton(@NotNull ItemStack item) {
        return item.getType().equals(LockManagerGUI.getDeleteLockButtonMaterial());
    }

    /**
     * Checks either the item a player clicked on was an "info" button.
     *
     * @param item Item that was clicked on inside the menu.
     *
     * @return {@code true} if that was the case, {@code false} otherwise.
     */
    private static boolean isInfoButton(@NotNull ItemStack item) {
        return item.getType().equals(LockManagerGUI.getInfoButtonMaterial());
    }
}
