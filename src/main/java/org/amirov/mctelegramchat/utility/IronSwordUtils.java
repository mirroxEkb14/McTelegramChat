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
import java.util.Collections;
import java.util.Objects;

/**
 * Creates and provides a custom iron sword.
 */
public final class IronSwordUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int STEEL_SWORD_AMOUNT = 1;
    private static final int MAX_SHARPNESS_ENCHANT_LEVEL = 5;
    private static final TextComponent STEEL_SWORD_NAME = Component.text(
            UtilityProperty.STEEL_SWORD_NAME.getValue(), NamedTextColor.GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static {
        Collections.addAll(loreList,
                Component.text(UtilityProperty.STEEL_SWORD_DESC_TIER.getValue(), NamedTextColor.AQUA),
                Component.text(UtilityProperty.STEEL_SWORD_DESC_TYPE.getValue(), NamedTextColor.AQUA),
                Component.text(UtilityProperty.STEEL_SWORD_DESC_WEIGHT.getValue(), NamedTextColor.AQUA),
                Component.text(UtilityProperty.STEEL_SWORD_DESC_ID.getValue(), NamedTextColor.AQUA));
    }
//</editor-fold>

    /**
     * @return Newly created iron sword of the type of {@code ItemStack}.
     */
    public static @NotNull ItemStack getIronSword() {
        final ItemStack steelSword = new ItemStack(Material.IRON_SWORD, STEEL_SWORD_AMOUNT);
        final ItemMeta steelSwordMeta = getSteelSwordMetaData(steelSword);
        steelSword.setItemMeta(steelSwordMeta);
        return steelSword;
    }

    /**
     * Sets the item's name, description, enchantments, etc.
     *
     * @param steelSword Item that needs to be set.
     * @return Instance of {@code ItemMeta} of the passed item.
     */
    private static @NotNull ItemMeta getSteelSwordMetaData(@NotNull ItemStack steelSword) {
        final ItemMeta steelSwordMeta = steelSword.getItemMeta();
        Objects.requireNonNull(steelSwordMeta);

        steelSwordMeta.displayName(STEEL_SWORD_NAME);
        steelSwordMeta.lore(loreList);
        steelSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, MAX_SHARPNESS_ENCHANT_LEVEL, true);
        steelSwordMeta.setUnbreakable(true);

        return steelSwordMeta;
    }

    public static TextComponent getSteelSwordName() { return STEEL_SWORD_NAME; }
}
