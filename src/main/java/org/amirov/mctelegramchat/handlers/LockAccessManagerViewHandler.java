package org.amirov.mctelegramchat.handlers;

import org.amirov.mctelegramchat.gui.enums.ConfirmationGUIConstants;
import org.amirov.mctelegramchat.gui.LockAccessManagerGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes clicks on items inside the "view players" inventory menu.
 */
public class LockAccessManagerViewHandler {

    /**
     * In this menu a player can only click on the "close" button, other players' heads are meant to be viewed only.
     *
     * @param event Event of an inventory click.
     * @param player Player who opened the inventory.
     * @param currentItem Item the player interacted with.
     */
    public static void performLockAccessManagerViewClick(@NotNull InventoryClickEvent event,
                                                         @NotNull Player player,
                                                         @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (ConfirmationGUIConstants.isCloseButton(currentItem))
            LockAccessManagerGUI.openLockAccessManagerGUI(player);
    }
}
