package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.utility.SteelSwordUtils;
import org.amirov.mctelegramchat.utility.UtilityProperty;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Creates an additional inventory for storing items.
 */
public final class MenuCommand implements CommandExecutor {

    private static final int INVENTORY_SIZE = 9;
    private static final TextComponent INVENTORY_NAME = Component.text(
            UtilityProperty.INVENTORY_NAME.getValue(), NamedTextColor.DARK_GRAY);

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final Inventory inventory = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

            final ItemStack steelSword = SteelSwordUtils.getIronSword();
            inventory.addItem(steelSword);
            player.openInventory(inventory);
        }
        return true;
    }

    public static TextComponent getInventoryName() { return INVENTORY_NAME; }
}
