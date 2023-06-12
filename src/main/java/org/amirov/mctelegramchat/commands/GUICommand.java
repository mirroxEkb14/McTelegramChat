package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.utility.CloseButtonUtils;
import org.amirov.mctelegramchat.utility.LethalTntUtils;
import org.amirov.mctelegramchat.utility.UtilityProperty;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Opens an inventory with the items that trigger certain events.
 */
public final class GUICommand implements CommandExecutor {

    private static final int INVENTORY_SIZE = 9;
    private static final int CLOSE_BUTTON_INDEX = 8;
    private static final TextComponent INVENTORY_NAME = Component.text(
            UtilityProperty.INVENTORY_NAME.getValue(), NamedTextColor.DARK_GRAY);

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final Inventory gui = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

            final ItemStack lethalTnt = LethalTntUtils.getLethalTnt();
            final ItemStack closeButton = CloseButtonUtils.getCloseButton();

            final ItemStack[] menu_items = {lethalTnt};
            gui.setContents(menu_items);

            gui.setItem(CLOSE_BUTTON_INDEX, closeButton);

            player.openInventory(gui);
        }
        return true;
    }

    public static TextComponent getInventoryName() { return INVENTORY_NAME; }
}
