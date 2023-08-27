package org.amirov.mctelegramchat.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.commands.subcommands.quartermaster.LockCommand;
import org.amirov.mctelegramchat.gui.LockConfirmationGUI;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * This class processes the "yes" and "no" cases of the {@link LockCommand} depending on the player's choice.
 */
public final class LockConfirmationHandler {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent LOCK_SUCCESS = Component.text(
            "Chest been Locked", NamedTextColor.GREEN);
//</editor-fold>

    /**
     * If the player selected {@code yes}, a lock on this chest is created and now it can be opened only by its owner;
     * if the {@code no} was selected, the conformation menu closes. A player can also try to move another items in the
     * menu, then the logs are printed to the console.
     *
     * @param event Event of a player clicking on some item in the menu.
     * @param player Player who triggered the event.
     * @param currentItem Item was this player clicked on.
     *
     * @see #isYesClicked(ItemStack)
     * @see #isNoClicked(ItemStack)
     */
    public static void performLockConfirmationClick(@NotNull InventoryClickEvent event,
                                                    @NotNull Player player,
                                                    @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        if (isYesClicked(currentItem)) {
            LockPerformer.createNewLock(player, McTelegramChat.getCreatedLocks().get(player));
            player.sendMessage(LOCK_SUCCESS);
        } else if (isNoClicked(currentItem)) {
            player.closeInventory();
        } else {
            Loggers.printSevereLog(LoggingMessage.LOCK_CONFORMATION_INVENTORY_WRONG_ITEM.getMessage());
        }
    }

    /**
     * Determines either the passed {@link ItemStack} is a "no button" or not.
     *
     * @param item Item that the player clicked on.
     *
     * @return {@code true}, if it was a "no" button, {@code false} otherwise.
     */
    private static boolean isNoClicked(@NotNull ItemStack item) {
        return item.getType().equals(LockConfirmationGUI.getNoButtonMaterial());
    }

    /**
     * Determines either the passed {@link ItemStack} is a "yes button" or not.
     *
     * @param item Item that was clicked.
     *
     * @return {@code true}, if it was a "yes" button, {@code false} otherwise.
     */
    private static boolean isYesClicked(@NotNull ItemStack item) {
        return item.getType().equals(LockConfirmationGUI.getYesButtonMaterial());
    }
}
