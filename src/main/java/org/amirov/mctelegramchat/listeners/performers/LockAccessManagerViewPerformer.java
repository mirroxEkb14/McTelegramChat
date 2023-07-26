package org.amirov.mctelegramchat.listeners.performers;

import org.amirov.mctelegramchat.gui.ConfirmationGUIConstants;
import org.amirov.mctelegramchat.gui.LockAccessManagerGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes clicks on items inside the "view players" inventory menu.
 */
public class LockAccessManagerViewPerformer {

    public static void performLockAccessManagerViewClick(@NotNull InventoryClickEvent event,
                                                         @NotNull Player player,
                                                         @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (ConfirmationGUIConstants.isCloseButton(currentItem))
            LockAccessManagerGUI.openLockAccessManagerGUI(player);
    }
}
