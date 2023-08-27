package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and provides a unique crossbow.
 */
public final class UrsineCrossbowUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int MAX_PIERCING_LEVEL = 4;
    private static final int MAX_QUICK_CHARGE_LEVEL = 3;
    private static final TextComponent CROSSBOW_NAME = Component.text(
            "Ursine crossbow", NamedTextColor.GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "This crossbow is part of the Bear School Gear", NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Getter. Returns the item as a {@link ItemStack} object.
     *
     * @return Created ursine crossbow.
     *
     * @see #getUrsineCrossbowMeta(ItemStack)
     */
    public static @NotNull ItemStack getUrsineCrossbow() {
        final ItemStack ursineCrossbow = new ItemStack(
                Material.CROSSBOW,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta ursineCrossbowMeta = getUrsineCrossbowMeta(ursineCrossbow);
        ursineCrossbow.setItemMeta(ursineCrossbowMeta);
        return ursineCrossbow;
    }

    /**
     * Sets the metadata of the item and returns it as a {@link ItemMeta} object.
     *
     * @param ursineCrossbow Item itself.
     * @return {@link ItemMeta} object with the set data.
     */
    private static @NotNull ItemMeta getUrsineCrossbowMeta(@NotNull ItemStack ursineCrossbow) {
        final ItemMeta ursineCrossbowMeta = ursineCrossbow.getItemMeta();
        ursineCrossbowMeta.displayName(CROSSBOW_NAME);
        ursineCrossbowMeta.lore(loreList);
        ursineCrossbowMeta.addEnchant(Enchantment.PIERCING, MAX_PIERCING_LEVEL, false);
        ursineCrossbowMeta.addEnchant(Enchantment.QUICK_CHARGE, MAX_QUICK_CHARGE_LEVEL, false);
        return ursineCrossbowMeta;
    }
}
