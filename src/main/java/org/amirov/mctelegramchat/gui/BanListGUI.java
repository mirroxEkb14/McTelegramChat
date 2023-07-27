package org.amirov.mctelegramchat.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.utility.PlayerHeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Opens a menu with a list of players who can be banned.
 */
public final class BanListGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 45;
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Player list", NamedTextColor.BLUE);
//</editor-fold>

    /**
     * Creates and opens a gui with players to be banned.
     *
     * @param player Player who opened the menu.
     */
    public static void openBanListGUI(@NotNull Player player) {
        final ArrayList<Player> playerList = new ArrayList<>(player.getServer().getOnlinePlayers());
        final Inventory banInventory = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

        for (Player currentPlayer : playerList) {
            final ItemStack playerHead = PlayerHeadUtils.getPlayerHead(currentPlayer);
            banInventory.addItem(playerHead);
        }
        player.openInventory(banInventory);
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getBanListName() { return INVENTORY_NAME; }
//</editor-fold>
}
