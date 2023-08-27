package org.amirov.mctelegramchat.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.gui.enums.ConfirmationGUIConstants;
import org.amirov.mctelegramchat.gui.LockAccessManagerGUI;
import org.amirov.mctelegramchat.gui.LockManagerGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Processes cases when a player wants to add another players for them to be able to access his locked chests.
 */
public final class LockAccessManagerAddHandler {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent PLAYER_WAS_ADDED = Component.text(
            " Granted You Access to the Lock", NamedTextColor.GREEN);
    private static final TextComponent YOU_WERE_ADDED = Component.text(
            " Was Added to the Lock", NamedTextColor.GREEN);
//</editor-fold>

    /**
     * If-else blocks explained:
     * <ol>
     * <li> {@code if} the player clicked on the "close" button, he goes back to the "lock access manager" gui.
     * <li> {@code else if} the player clicked on the "player head", the logic of adding a new player to the lock performs.
     * </ol>
     *
     * @param event Event of a player clicking in an inventory.
     * @param player Player who performed the click.
     * @param currentItem Item that was clicked on.
     *                    
     * @see #sendNotificationToOwner(Player, String) 
     * @see #sendNotificationToPlayerToAdd(Player, String) 
     */
    public static void performLockAccessManagerAddClick(@NotNull InventoryClickEvent event,
                                                        @NotNull Player player,
                                                        @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (ConfirmationGUIConstants.isCloseButton(currentItem)) {
            LockAccessManagerGUI.openLockAccessManagerGUI(player);
        } else if (ConfirmationGUIConstants.isPlayerHead(currentItem)) {
            final Player playerToAdd = ConfirmationGUIConstants.getPlayerFromHead(currentItem);
            LockPerformer.addOrRemovePlayerToLock(LockManagerGUI.getCurrentLockId(),  playerToAdd,true);

            sendNotificationToOwner(player, playerToAdd.getName());
            sendNotificationToPlayerToAdd(playerToAdd, player.getName());

            LockManagerGUI.openLockManagerGUI(player);
        }
    }

    /**
     * Sends a messages to a player who was granted the access to a lock.
     *
     * @param playerToAdd Player who was added to the lock access.
     * @param ownerName Name of the owner of this lock.
     */
    private static void sendNotificationToPlayerToAdd(@NotNull Player playerToAdd, String ownerName) {
        final TextComponent ownerNameComponent = Component.text(ownerName, NamedTextColor.WHITE);
        final TextComponent fullMessage = ownerNameComponent.append(PLAYER_WAS_ADDED);
        playerToAdd.sendMessage(fullMessage);
    }

    /**
     * Sends a messages to the lock owner that he successfully granted access to his lock to another player.
     *
     * @param owner Owner of the lock.
     * @param playerToAddName Name of a player who was granted the access.
     */
    private static void sendNotificationToOwner(@NotNull Player owner, String playerToAddName) {
        final TextComponent playerToAddNameComponent = Component.text(playerToAddName, NamedTextColor.WHITE);
        final TextComponent fullMessage = playerToAddNameComponent.append(YOU_WERE_ADDED);
        owner.sendMessage(fullMessage);
    }
}
