package org.amirov.mctelegramchat.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.gui.LockDeleteConfirmationGUI;
import org.amirov.mctelegramchat.gui.LockListGUI;
import org.amirov.mctelegramchat.gui.LockManagerGUI;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes the player's choice, either he really wants to delete this lock or not.
 */
public final class LockDeleteConfirmationHandler {

    /**
     * if-else blocks explained:
     * <ol>
     * <li> "no" button, the player goes back to the "lock manager" gui.
     * <li> "yes" button, the player whose head was selected is now deleted from the lock, the player who performed
     * the deletion is now 
     * </ol>
     *
     * @param event
     * @param player
     * @param currentItem
     */
    public static void performLockDeleteConfirmationClick(@NotNull InventoryClickEvent event,
                                                          @NotNull Player player,
                                                          @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (isNoButton(currentItem)) {
            LockManagerGUI.openLockManagerGUI(player);
        } else if (isYesButton(currentItem)) {
            LockPerformer.deleteLock(LockManagerGUI.getCurrentLockId());
            player.sendMessage(Component.text(
                    ChatMessage.ON_LOCK_DELETED.getMessage(), NamedTextColor.GREEN));
            LockListGUI.openLockListGUI(player);
        }
    }


    /**
     * Determines either the passed item is a "yes" button or not.
     *
     * @param item Item that was clicked on.
     *
     * @return {@code true} if this item is a "yes" button, {@code false} otherwise.
     */
    private static boolean isYesButton(@NotNull ItemStack item) {
        return item.getType().equals(LockDeleteConfirmationGUI.getYesButtonMaterial());
    }

    /**
     * Determines either the passed item is a "no" button or not.
     *
     * @param item Item that was clicked on.
     *
     * @return {@code true} if this item is a "no" button, {@code false} otherwise.
     */
    private static boolean isNoButton(@NotNull ItemStack item) {
        return item.getType().equals(LockDeleteConfirmationGUI.getNoButtonMaterial());
    }
}
