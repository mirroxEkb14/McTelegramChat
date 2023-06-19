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
 * Creates and provides a custom steel sword.
 */
public final class SteelSwordUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int STEEL_SWORD_AMOUNT = 1;
    private static final int MAX_SHARPNESS_ENCHANT_LEVEL = 5;
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

    /**
     * Works as a getter. Sets the metadata of the object and returns it.
     *
     * @return Newly created iron sword of the type of {@link ItemStack}.
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

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getSteelSwordName() { return STEEL_SWORD_NAME; }
//</editor-fold>
}
