package org.amirov.mctelegramchat.listeners.performers;

import org.amirov.mctelegramchat.commands.performers.LockManagerGUI;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * The class processes cases when a player interacts with items inside the lock list menu.
 */
public final class LockListInventoryPerformer {


    /**
     * Player clicks on some item inside the lock list menu (either chest or glass panel).
     *
     * @param event Event of a player clicking on some item in the menu.
     * @param player Player who triggered the event.
     * @param currentItem Item was this player clicked on.
     */
    public static void performLockListInventoryClick(@NotNull InventoryClickEvent event,
                                                     @NotNull Player player,
                                                     @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (currentItem.getType().equals(Material.CHEST)) {
            final int itemSlotIndex = event.getSlot();
            LockManagerGUI.openLockManagerGUI(
                    player,
                    LockPerformer.getLockById(LockManagerGUI.getLockId(itemSlotIndex)));
        }
    }
}
