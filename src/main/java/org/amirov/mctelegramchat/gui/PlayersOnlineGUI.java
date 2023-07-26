package org.amirov.mctelegramchat.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Opens a menu with a list of players who are currently online on the server. Used to be able to select a player to add
 * or remove from the lock access.
 */
public final class PlayersOnlineGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 54;
    private static final TextComponent INVENTORY_ADD_NAME = Component.text(
            "Player To Add", NamedTextColor.GREEN);
    private static final TextComponent INVENTORY_REMOVE_NAME = Component.text(
            "Player To Remove", NamedTextColor.GREEN);

    private static final int CLOSE_BUTTON_INDEX = 53;
//</editor-fold>

    private static final Material PLAYER_HEAD_MATERIAL = Material.PLAYER_HEAD;

    public static void openPlayersOnlineGUI(@NotNull Player player, boolean toAdd) {
        final Inventory playersToAddGUI = Bukkit.createInventory(
                player,
                INVENTORY_SIZE,
                toAdd ? INVENTORY_ADD_NAME : INVENTORY_REMOVE_NAME);

        final ArrayList<Player> onlinePlayers = new ArrayList<>(player.getServer().getOnlinePlayers());
        for (Player onlinePlayer : onlinePlayers) {
            final ItemStack onlinePlayerHead = getOnlinePlayerHead(onlinePlayer);
            if (!isOwner(player, onlinePlayer))  playersToAddGUI.addItem(onlinePlayerHead);
        }
        final ItemStack closeButton = ConfirmationGUIConstants.getCloseButton();
        playersToAddGUI.setItem(CLOSE_BUTTON_INDEX, closeButton);
        ConfirmationGUIConstants.fillEmptySlots(playersToAddGUI);

        player.openInventory(playersToAddGUI);
    }

    /**
     * Determines either an online player on this for-loop iteration is an owner of this lock or not.
     *
     * @param owner {@link Player} object of the lock owner.
     * @param onlinePlayer {@link Player} object of a random online player.
     *
     * @return {@code true} if it is an owner of this lock, {@code false} otherwise.
     */
    private static boolean isOwner(@NotNull Player owner, @NotNull Player onlinePlayer) {
        return owner.equals(onlinePlayer);
    }

    /**
     * Creates and returns a head of a player who is currently online on the server.
     *
     * @param onlinePlayer Player who is currently online.
     *
     * @return Player's head.
     */
    private static @NotNull ItemStack getOnlinePlayerHead(@NotNull Player onlinePlayer) {
        final ItemStack playerHead = new ItemStack(
                PLAYER_HEAD_MATERIAL,
                ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta itemMeta = playerHead.getItemMeta();
        itemMeta.displayName(onlinePlayer.displayName());
        playerHead.setItemMeta(itemMeta);
        return playerHead;
    }

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    public static TextComponent getInventoryAddName() { return INVENTORY_ADD_NAME; }

    public static TextComponent getInventoryRemoveName() { return INVENTORY_REMOVE_NAME; }
//</editor-fold>
}
