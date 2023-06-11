package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.commands.MenuCommand;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.utility.IronSwordUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Monitors the events in the inventory from the {@link MenuCommand} class.
 */
public final class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(@NotNull InventoryClickEvent event) {
        if (event.getView().title().equals(MenuCommand.getInventoryName())) {
            final ItemStack selectedItem = event.getCurrentItem();
            if (selectedItem == null)
                return;

            final Component itemComponent = selectedItem.getItemMeta().displayName();
            Objects.requireNonNull(itemComponent);
            if (itemComponent.equals(IronSwordUtils.getSteelSwordName())) {
                Loggers.printInfoLog("SWORD PICKED UP");
            }
        }
    }
}
