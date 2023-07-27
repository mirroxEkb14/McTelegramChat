package org.amirov.mctelegramchat.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.gui.enums.ConfirmationGUIConstants;
import org.amirov.mctelegramchat.gui.LockAccessManagerGUI;
import org.amirov.mctelegramchat.gui.LockManagerGUI;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes clicks on items inside the "delete players" inventory menu.
 */
public class LockAccessManagerDeleteHandler {

    /**
     * if-else blocks explained:
     * <lo>
     * <li> {@code if} a player clicks on the "close" button, he goes back to the "lock access manager" gui.
     * <li> {@code else if} a player selects the "player head" item, the player whose head was chosen is added to /
     * removed from the lock.
     * </lo>
     *
     * @param event Event of an inventory click.
     * @param player Player who clicked.
     * @param currentItem Item a player clicked on.
     */
    public static void performLockAccessManagerDeleteClick(@NotNull InventoryClickEvent event,
                                                           @NotNull Player player,
                                                           @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (ConfirmationGUIConstants.isCloseButton(currentItem)) {
            LockAccessManagerGUI.openLockAccessManagerGUI(player);
        } else if (ConfirmationGUIConstants.isPlayerHead(currentItem)) {
            final Player playerToDelete = ConfirmationGUIConstants.getPlayerFromHead(currentItem);
            LockPerformer.addOrRemovePlayerToLock(LockManagerGUI.getCurrentLockId(),  playerToDelete,false);

            sendNotificationToOwner(player, playerToDelete.getName());
            sendNotificationToPlayerToDelete(playerToDelete, player.getName());

            LockManagerGUI.openLockManagerGUI(player);
        }
    }

    /**
     * Sends a messages to a player who was deleted from the lock.
     *
     * @param playerToDelete Player who was deleted from the lock.
     * @param ownerName Name of the owner of this lock.
     */
    private static void sendNotificationToPlayerToDelete(@NotNull Player playerToDelete, String ownerName) {
        final TextComponent ownerNameComponent = Component.text(ownerName, NamedTextColor.YELLOW);
        final TextComponent fullMessage = ownerNameComponent.append(
                Component.text(ChatMessage.ON_LOCK_ADD_PLAYER_YOU_WERE_DELETED_FROM_LOCK.getMessage(), NamedTextColor.GREEN));
        playerToDelete.sendMessage(fullMessage);
    }

    /**
     * Sends a messages to the lock owner that he successfully deleted another player, and now he doesn't have access
     * to his lock.
     *
     * @param owner Owner of the lock.
     * @param playerToDeleteName Name of a player who was deleted from this lock.
     */
    private static void sendNotificationToOwner(@NotNull Player owner, String playerToDeleteName) {
        final TextComponent playerToAddNameComponent = Component.text(playerToDeleteName, NamedTextColor.YELLOW);
        final TextComponent fullMessage = playerToAddNameComponent.append(
                Component.text(ChatMessage.ON_LOCK_ADD_PLAYER_YOU_DELETED_FROM_LOCK.getMessage(), NamedTextColor.GREEN));
        owner.sendMessage(fullMessage);
    }
}
