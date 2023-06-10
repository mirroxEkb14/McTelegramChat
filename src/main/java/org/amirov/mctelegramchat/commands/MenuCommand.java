package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Creates an additional inventory for storing items.
 */
public final class MenuCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int INVENTORY_SIZE = 9;
    private static final int STEEL_SWORD_AMOUNT = 1;
    private static final int MAX_SHARPNESS_ENCHANT_LEVEL = 5;
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Witcher gear", NamedTextColor.DARK_GRAY);
    private static final TextComponent STEEL_SWORD_NAME = Component.text(
            "Teigr", NamedTextColor.GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static {
        Collections.addAll(loreList,
                Component.text("Tier - Witcher Item", NamedTextColor.AQUA),
                Component.text("Type - Steel sword", NamedTextColor.AQUA),
                Component.text("Weight - 1.61", NamedTextColor.AQUA),
                Component.text("Item ID - mq1058_cat_school_sword", NamedTextColor.AQUA));
    }
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final Inventory inventory = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);
            final ItemStack steelSword = new ItemStack(Material.IRON_SWORD, STEEL_SWORD_AMOUNT);
            final ItemMeta steelSwordMeta = getSteelSwordMetaData(steelSword);

            steelSword.setItemMeta(steelSwordMeta);
            inventory.addItem(steelSword);
            player.openInventory(inventory);
        }
        return true;
    }

    /**
     * Sets the item's name, description, enchantments, etc.
     *
     * @param steelSword Item that needs to be set.
     * @return Instance of {@code ItemMeta} of the passed item.
     */
    private @NotNull ItemMeta getSteelSwordMetaData(@NotNull ItemStack steelSword) {
        final ItemMeta steelSwordMeta = steelSword.getItemMeta();
        Objects.requireNonNull(steelSwordMeta);

        steelSwordMeta.displayName(STEEL_SWORD_NAME);
        steelSwordMeta.lore(loreList);
        steelSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, MAX_SHARPNESS_ENCHANT_LEVEL, true);
        steelSwordMeta.setUnbreakable(true);

        return steelSwordMeta;
    }

    public static TextComponent getInventoryName() { return INVENTORY_NAME; }

    public static TextComponent getSteelSwordName() { return STEEL_SWORD_NAME; }
}
