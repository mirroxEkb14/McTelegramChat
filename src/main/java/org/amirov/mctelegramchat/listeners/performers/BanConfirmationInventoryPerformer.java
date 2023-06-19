package org.amirov.mctelegramchat.listeners.performers;

import org.amirov.mctelegramchat.commands.BanInventoryCommand;
import org.amirov.mctelegramchat.listeners.properties.BanReason;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.bukkit.BanList;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * Opens an inventory where the player has to confirm if he wants to ban the chosen player or not.
 */
public class BanConfirmationInventoryPerformer {

    /**
     * The variable represents time in milliseconds for which a player will be banned.
     * <p>
     * The value is 10 seconds.
     */
    private static final long EXPIRES_TIME = 10_000L;

    public static void performBanConfirmationInventoryClick(@NotNull InventoryClickEvent event,
                                                            @NotNull Player player,
                                                            @NotNull ItemStack currentItem) {
        event.setCancelled(true);
        switch (currentItem.getType()) {
            case WOODEN_AXE -> {
                final String playerToBanName = BanInventoryPerformer.getCurrentPlayerToBanName();
                final Date expires = new Date();
                expires.setTime(new Date().getTime() + EXPIRES_TIME);

                player.getServer().getBanList(BanList.Type.NAME).addBan(
                        playerToBanName,
                        BanReason.BECAUSE_I_AM_ADMIN_REASON.getReason(),
                        expires,
                        null);
            }
            case BARRIER -> BanInventoryCommand.getInstance().onCommand(player);
            default -> Loggers.printSevereLog(LoggingMessage.BAN_CONFIRMATION_INVENTORY_WRONG_ITEM.getMessage());
        }
    }
}
