package org.amirov.mctelegramchat.handlers;

import org.amirov.mctelegramchat.gui.LockListGUI;
import org.amirov.mctelegramchat.gui.LockManagerGUI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The class processes cases when a player interacts with items inside the lock list menu.
 */
public final class LockListHandler {

    /**
     * Player clicks on some item inside the lock list menu (either chest or glass panel).
     *
     * @param event Event of a player clicking on some item in the menu.
     * @param player Player who triggered the event.
     * @param currentItem Item was this player clicked on.
     *
     * @see #isLockableBlock(ItemStack)
     */
    public static void performLockListClick(@NotNull InventoryClickEvent event,
                                            @NotNull Player player,
                                            @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (isLockableBlock(currentItem)) {
            final int itemSlotIndex = event.getSlot();
            LockManagerGUI.setCurrentLockId(LockManagerGUI.getLockId(itemSlotIndex));
            LockManagerGUI.openLockManagerGUI(player);
        }
    }

    /**
     * Determines either the passed {@link ItemStack} block is a lockable one from the list in the config.yml or not.
     *
     * @param block Block that is to be checked.
     *
     * @return {@code true} if the passed block is a lockable one, {@code false} otherwise.
     */
    private static boolean isLockableBlock(@NotNull ItemStack block) {
        return block.getType().equals(LockListGUI.currentLockMaterial);
    }
}
