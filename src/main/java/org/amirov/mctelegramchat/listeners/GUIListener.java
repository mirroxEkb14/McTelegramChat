package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.BanGUICommand;
import org.amirov.mctelegramchat.commands.GUICommand;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.amirov.mctelegramchat.utility.BanBackBarrier;
import org.amirov.mctelegramchat.utility.BanWoodenAxeUtils;
import org.amirov.mctelegramchat.utility.PlayerHeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Monitors the clicks on items in the inventory.
 */
public class GUIListener implements Listener {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int INVENTORY_SIZE = 9;
    private static final TextComponent INVENTORY_TITLE = Component.text(
            "Ban Confirm", NamedTextColor.RED);

    private static final int BAN_ITEM_INDEX = 0;
    private static final int PLAYER_HEAD_INDEX = 4;
    private static final int BACK_ITEM_INDEX = 8;
//</editor-fold>

    @EventHandler
    public void onGuiClick(@NotNull InventoryClickEvent event) {
        if (event.getView().title().equals(GUICommand.getInventoryName())) {
            final Player player = (Player) event.getWhoClicked();
            final ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null)
                return;

            if (event.getView().title().equals(BanGUICommand.getInventoryName())) {
                onBanGUIClick(event, player, currentItem);
                return;
            }
            switch (currentItem.getType()) {
                case TNT -> {
                    player.setHealth(Double.MIN_VALUE);
                    player.sendMessage(Component.text(ChatMessage.ON_COMMAND_DIE.getMessage()));
                }
                case BARRIER -> player.closeInventory();
                default -> Loggers.printInfoLog(LoggingMessage.COMMAND_GUI_WRONG_ITEM_SELECTED.getMessage());
            }
            player.closeInventory();
            event.setCancelled(true);
        }
    }

    private void onBanGUIClick(InventoryClickEvent event, Player player, @NotNull ItemStack currentItem) {
        if (currentItem.getType() == Material.PLAYER_HEAD) {
            final Player playerToBan = getPlayerToBan(event, player);
            final Inventory confirmBanMenu = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_TITLE);
            setInventoryItems(confirmBanMenu, playerToBan);
            player.openInventory(confirmBanMenu);
        }
    }

    private void setInventoryItems(@NotNull Inventory confirmBanMenu, Player playerToBan) {
        final ItemStack banItem = BanWoodenAxeUtils.getBanWoodenAxe();
        confirmBanMenu.setItem(BAN_ITEM_INDEX, banItem);

        final ItemStack playerHead = PlayerHeadUtils.getPlayerHead(playerToBan);
        confirmBanMenu.setItem(PLAYER_HEAD_INDEX, playerHead);

        final ItemStack backItem = BanBackBarrier.getBanBackBarrier();
        confirmBanMenu.setItem(BACK_ITEM_INDEX, backItem);
    }

    private Player getPlayerToBan(@NotNull InventoryClickEvent event, @NotNull Player player) {
        final ItemStack currentItem = event.getCurrentItem();
        Objects.requireNonNull(currentItem);
        final Component playerToBanName = currentItem.getItemMeta().displayName();
        Objects.requireNonNull(playerToBanName);
        return player.getServer().getPlayerExact(playerToBanName.toString());
    }
}
