package org.amirov.mctelegramchat.handlers;

import org.amirov.mctelegramchat.gui.BanConfirmationGUI;
import org.amirov.mctelegramchat.gui.BanListGUI;
import org.amirov.mctelegramchat.gui.enums.ConfirmationGUIConstants;
import org.amirov.mctelegramchat.listeners.properties.BanReason;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.utility.buttons.BanWoodenAxeButton;
import org.bukkit.BanList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Processes the player's choice in the ban confirmation menu, either he wants to ban the chosen player or not.
 */
public final class BanConfirmationHandler {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    /**
     * The variable represents time in milliseconds for which a player will be banned.
     * <p>
     * The value is 5 seconds.
     */
    private static final long EXPIRES_TIME = 5_000L;
//</editor-fold>

    /**
     * Depending on either the player clicked on the wooden axe or a close button, this method processes these cases
     * and either bans the previously selected player or closes this inventory gui.
     *
     * @param event Event of a player clicking an inventory item.
     * @param currentItem Item that was clicked on.
     * @param player Player who performed the click.
     *
     * @see #performBan(Player)
     */
    public static void performBanConfirmationInventoryClick(@NotNull InventoryClickEvent event,
                                                            @NotNull Player player,
                                                            @NotNull ItemStack currentItem) {
        event.setCancelled(true);

        final Material banWoodenAxe = BanWoodenAxeButton.getBanWoodenAxe().getType();
        if (currentItem.getType() == banWoodenAxe)
            performBan(player);
        else if (ConfirmationGUIConstants.isCloseButton(currentItem))
            BanListGUI.openBanListGUI(player);
        else
            Loggers.printSevereLog(LoggingMessage.BAN_CONFIRMATION_INVENTORY_WRONG_ITEM.getMessage());
    }

    /**
     * Bans the passed player.
     * <p>
     * Note: When a player clicks on the ban button (wooden axe), he doesn't leave the server immediately, he can
     * continue playing, but if he leaves the server, then he cannot enter for the next {@code EXPIRES_TIME} seconds.
     *
     * @param player Player who is to be banned.
     */
    private static void performBan(final @NotNull Player player) {
        final String playerToBanName = BanConfirmationGUI.getCurrentPlayerToBanName();
        final Date expires = new Date();
        expires.setTime(new Date().getTime() + EXPIRES_TIME);

        player.getServer().getBanList(BanList.Type.NAME).addBan(
                playerToBanName,
                BanReason.BECAUSE_I_AM_ADMIN_REASON.getReason(),
                expires,
                null);
    }
}
