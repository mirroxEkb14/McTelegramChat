package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.commands.GUICommand;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors the clicks on items in the inventory.
 */
public class GUIListener implements Listener {

    @EventHandler
    public void onGuiClick(@NotNull InventoryClickEvent event) {
        if (event.getView().title().equals(GUICommand.getInventoryName())) {
            final Player player = (Player) event.getWhoClicked();
            final ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null)
                return;
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
}
