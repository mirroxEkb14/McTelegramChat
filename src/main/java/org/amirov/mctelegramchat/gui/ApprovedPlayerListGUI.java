package org.amirov.mctelegramchat.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.gui.enums.ConfirmationGUIConstants;
import org.amirov.mctelegramchat.strings.LockCommandDBProperties;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Opens a menu with all the players who have access to the lock.
 */
public final class ApprovedPlayerListGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 45;
    private static final TextComponent INVENTORY_NAME = Component.text(
            "View Players", NamedTextColor.AQUA);
    private static final TextComponent NO_PLAYERS_TO_VIEW = Component.text(
            "You Didn't Add Players", NamedTextColor.RED);

    private static final int CLOSE_BUTTON_INDEX = 44;
//</editor-fold>

    /**
     * Gets the list of players from the DB who are allowed to use this lock, then shows this list as players' heads
     * in the menu.
     *
     * @param player Player who triggered the event.
     * @param lockId Id of the lock in the DB.
     *
     * @see #getPlayerHead(Player)
     */
    public static void openApprovedPlayerListGUI(@NotNull Player player, String lockId) {
        final Inventory approvedPlayerListGUI = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

        final ArrayList<String> accessList = (ArrayList<String>)
                LockPerformer.getLockById(lockId).get(LockCommandDBProperties.ACCESS_KEY_NAME.getKey());

        if (accessList.isEmpty()) {
            player.sendMessage(NO_PLAYERS_TO_VIEW);
        } else {
            for (String s : accessList) {
                final UUID uuid = UUID.fromString(s);
                final Player playerWithAccess = Bukkit.getPlayer(uuid);

                Objects.requireNonNull(playerWithAccess);
                final ItemStack playerHead = getPlayerHead(playerWithAccess);
                approvedPlayerListGUI.addItem(playerHead);
            }
        }
        final ItemStack closeButton = ConfirmationGUIConstants.getCloseButton();
        approvedPlayerListGUI.setItem(CLOSE_BUTTON_INDEX, closeButton);

        ConfirmationGUIConstants.fillEmptySlots(approvedPlayerListGUI);
        player.openInventory(approvedPlayerListGUI);
    }

    /**
     * Creates and returns a head of a player who has access to the lock of a player who has this menu opened.
     *
     * @param playerWithAccess Player who has access to the lock.
     *
     * @return Player's head.
     */
    private static @NotNull ItemStack getPlayerHead(@NotNull Player playerWithAccess) {
        final ItemStack playerHead = new ItemStack(
                Material.PLAYER_HEAD,
                ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta itemMeta = playerHead.getItemMeta();
        itemMeta.displayName(playerWithAccess.displayName());
        playerHead.setItemMeta(itemMeta);
        return playerHead;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getApprovedPlayerListName() { return INVENTORY_NAME; }
//</editor-fold>
}
