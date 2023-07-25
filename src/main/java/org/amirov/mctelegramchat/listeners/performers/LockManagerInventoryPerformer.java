package org.amirov.mctelegramchat.listeners.performers;

import org.amirov.mctelegramchat.commands.performers.LockManagerGUI;
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

        } else if (isDeleteBtn(currentItem)) {

        } else if (isInfoBtn(currentItem)) {

        } else if (isCloseBtn(currentItem)) {

        } else {

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

    /**
     * Checks either the item a player clicked on was a "close" button.
     *
     * @param item Item that was clicked on inside the menu.
     *
     * @return {@code true} if that was the case, {@code false} otherwise.
     */
    private static boolean isCloseBtn(@NotNull ItemStack item) {
        return item.getType().equals(LockManagerGUI.getCloseButtonMaterial());
    }
}
