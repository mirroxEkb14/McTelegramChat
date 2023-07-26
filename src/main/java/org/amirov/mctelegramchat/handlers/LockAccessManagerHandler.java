package org.amirov.mctelegramchat.handlers;

import org.amirov.mctelegramchat.gui.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes the clicks on items in the access manager menu.
 */
public final class LockAccessManagerHandler {

    /**
     * if-else blocks explained:
     * <lo>
     * <li> "close" button, the player goes back to the "lock manager" gui.
     * <li> "view players" button, opens another gui with a list of players who have access to the lock.
     * <li> "add" button, opens another gui with the online players currently on the server to add to the lock.
     * <li> "delete" button, opens another gui with the online players currently on the server to delete from the lock.
     * </lo>
     *
     * @param event Event of interacting with the inventory.
     * @param player Player triggered the event.
     * @param currentItem Item that was clicked on.
     */
    public static void performLockAccessManagerClick(@NotNull InventoryClickEvent event,
                                                     @NotNull Player player,
                                                     @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (ConfirmationGUIConstants.isCloseButton(currentItem)) {
            LockManagerGUI.openLockManagerGUI(player);
        } else if (isViewButton(currentItem)) {
            ApprovedPlayerListGUI.openApprovedPlayerListGUI(player, LockManagerGUI.getCurrentLockId());
        } else if (isAddButton(currentItem)) {
            PlayersOnlineGUI.openPlayersOnlineGUI(player, true);
        } else if (isDeleteButton(currentItem)) {
            PlayersOnlineGUI.openPlayersOnlineGUI(player, false);
        }
    }


    /**
     * Determines either the item passed as an argument is a "remove" button or not.
     *
     * @param item Item itself.
     *
     * @return {@code true} if this item is a "remove" button for this menu, {@code false} otherwise.
     */
    private static boolean isDeleteButton(@NotNull ItemStack item) {
        return item.getType().equals(LockAccessManagerGUI.getRemoveButtonMaterial());
    }


    /**
     * Determines either the item passed as an argument is an "add" button or not.
     *
     * @param item Item itself.
     *
     * @return {@code true} if this item is an "add" button for this menu, {@code false} otherwise.
     */
    private static boolean isAddButton(@NotNull ItemStack item) {
        return item.getType().equals(LockAccessManagerGUI.getAddButtonMaterial());
    }

    /**
     * Determines either the item passed as an argument is a "view" button or not.
     *
     * @param item Item itself.
     *
     * @return {@code true} if this item is a "view" button for this menu, {@code false} otherwise.
     */
    private static boolean isViewButton(@NotNull ItemStack item) {
        return item.getType().equals(LockAccessManagerGUI.getViewButtonMaterial());
    }
}
