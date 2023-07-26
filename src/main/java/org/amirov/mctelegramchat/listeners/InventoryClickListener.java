package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.commands.MenuInventoryCommand;
import org.amirov.mctelegramchat.gui.*;
import org.amirov.mctelegramchat.handlers.*;
import org.amirov.mctelegramchat.listeners.performers.LockDeleteConfirmationHandler;
import org.amirov.mctelegramchat.utility.buttons.BackWoolButton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors the clicks on items in the inventory.
 * <p>
 * In this plugin except the player's inventory there are also custom ones. This class keeps track of both player's and
 * custom inventories.
 */
public final class InventoryClickListener implements Listener {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final Component mainMenuName = MainMenuGUI.getInventoryName();
    private static final Component armoryInventoryName = ArmoryGUI.getArmoryInventoryName();
    private static final Component banListName = BanListGUI.getInventoryName();
    private static final Component banConfirmationName = BanConfirmationGUI.getInventoryName();
    private static final Component lockConfirmationInventoryName = LockConfirmationGUI.getInventoryTitle();
    private static final Component lockListInventoryName = LockListGUI.getInventoryTitle();
    private static final Component lockManagerInventoryName = LockManagerGUI.getInventoryTitle();
    private static final Component lockDeleteConfirmationInventoryName = LockDeleteConfirmationGUI.getInventoryName();
    private static final Component lockAccessManagerInventoryName = LockAccessManagerGUI.getInventoryName();
    private static final Component lockAccessManagerAddName = PlayersOnlineGUI.getInventoryAddName();
    private static final Component lockAccessManagerViewInventoryName = ApprovedPlayerListGUI.getInventoryName();
    private static final Component lockAccessManagerRemoveInventoryName = PlayersOnlineGUI.getInventoryRemoveName();
//</editor-fold>

    /**
     * Checks from what inventory an item was clicked and calls the appropriate method.
     * <p>
     * The first {@code if-else} blocks check if the item that was clicked on is a back button. If so, checks from
     * what inventory it was clicked and navigates the player back to the previous one.
     * <p>
     * The second {@code if-else} sequence checks what inventory is currently open and calls the appropriate methods
     * to perform certain actions with the item that was clicked on.
     *
     * @param event Player's click on item event.
     */
    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        final Component curName = event.getView().title();
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        } else if (currentItem.getType() == BackWoolButton.getBackWool().getType()) {
            if (curName.equals(armoryInventoryName)) {
                MenuInventoryCommand.getInstance().onCommand(player);
            }
            return;
        }

        if (curName.equals(mainMenuName)) {
            MainMenuHandler.performMenuInventoryClick(event, currentItem, player);
        } else if (curName.equals(banListName)) {
            BanConfirmationGUI.openBanConfirmationGUI(event, player, currentItem);
        } else if (curName.equals(banConfirmationName)) {
            BanConfirmationHandler.performBanConfirmationInventoryClick(event, player, currentItem);
        } else if (curName.equals(lockConfirmationInventoryName)) {
            LockConfirmationHandler.performLockConfirmationClick(event, player, currentItem);
        } else if (curName.equals(lockListInventoryName)) {
            LockListHandler.performLockListClick(event, player, currentItem);
        } else if (curName.equals(lockManagerInventoryName)) {
            LockManagerHandler.performLockManagerClick(event, player, currentItem);
        } else if (curName.equals(lockDeleteConfirmationInventoryName)) {
            LockDeleteConfirmationHandler.performLockDeleteConfirmationClick(event, player, currentItem);
        } else if (curName.equals(lockAccessManagerInventoryName)) {
            LockAccessManagerHandler.performLockAccessManagerClick(event, player, currentItem);
        } else if (curName.equals(lockAccessManagerAddName)) {
            LockAccessManagerAddHandler.performLockAccessManagerAddClick(event, player, currentItem);
        } else if (curName.equals(lockAccessManagerViewInventoryName)) {
            LockAccessManagerViewHandler.performLockAccessManagerViewClick(event, player, currentItem);
        } else if (curName.equals(lockAccessManagerRemoveInventoryName)) {
            LockAccessManagerDeleteHandler.performLockAccessManagerDeleteClick(event, player, currentItem);
        }
    }
}
