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
 * Creates and provides a unique armor (chestplate).
 */
public final class UrsineChestplateUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int MAX_PROJECTILE_PROTECTION_LEVEL = 4;
    private static final TextComponent CHESTPLATE_NAME = Component.text(
            "Mastercrafted Legendary Ursine Armor", NamedTextColor.BLUE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
        "Heavy armor that is part of the Bear School Gear",
            NamedTextColor.LIGHT_PURPLE)); }
//</editor-fold>

    /**
     * Getter. Returns a custom chestplate of the type of {@link ItemStack}.
     *
     * @return Created a unique armor.
     *
     * @see #getUrsineChestplateMeta(ItemStack)
     */
    public static @NotNull ItemStack getUrsineChestplate() {
        final ItemStack chestplate = new ItemStack(
                Material.NETHERITE_CHESTPLATE,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta chestplateMeta = getUrsineChestplateMeta(chestplate);
        chestplate.setItemMeta(chestplateMeta);
        return chestplate;
    }

    /**
     * Sets the metadata and returns them as a {@link ItemMeta} object.
     *
     * @param chestplate Item itself.
     * @return {@link ItemMeta} object with the set name and description of the item.
     */
    private static @NotNull ItemMeta getUrsineChestplateMeta(@NotNull ItemStack chestplate) {
        final ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.displayName(CHESTPLATE_NAME);
        chestplateMeta.lore(loreList);
        chestplateMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,
                MAX_PROJECTILE_PROTECTION_LEVEL,
                false);
        return chestplateMeta;
    }
}
