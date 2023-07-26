package org.amirov.mctelegramchat.listeners.performers;

import org.amirov.mctelegramchat.gui.*;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes the events of clicking on items inside the lock manager menu.
 */
public final class LockManagerInventoryPerformer {


    /**
     * Player clicks on some item inside the lock manager menu.
     *
     * @param event Event of a player clicking on some item in this menu.
     * @param player Player who triggered the event.
     * @param currentItem Item that this player clicked on.
     */
    public static void performLockManagerInventoryClick(@NotNull InventoryClickEvent event,
                                                        @NotNull Player player,
                                                        @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (isManageAccessBtn(currentItem)) {
            LockAccessManagerGUI.openLockAccessManagerGUI(player);
        } else if (isDeleteBtn(currentItem)) {
            LockDeleteConfirmationGUI.openLockDeleteConfirmationGUI(player);
        } else if (isInfoBtn(currentItem)) {
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
    private static boolean isManageAccessBtn(@NotNull ItemStack item) {
        return item.getType().equals(LockManagerGUI.getManageAccessName());
    }


    /**
     * Checks either the item a player clicked on was a "delete" button.
     *
     * @param item Item that was clicked on inside the menu.
     *
     * @return {@code true} if that was the case, {@code false} otherwise.
     */
    private static boolean isDeleteBtn(@NotNull ItemStack item) {
        return item.getType().equals(LockManagerGUI.getDeleteLockButtonMaterial());
    }

    /**
     * Checks either the item a player clicked on was an "info" button.
     *
     * @param item Item that was clicked on inside the menu.
     *
     * @return {@code true} if that was the case, {@code false} otherwise.
     */
    private static boolean isInfoBtn(@NotNull ItemStack item) {
        return item.getType().equals(LockManagerGUI.getInfoButtonMaterial());
    }
}
