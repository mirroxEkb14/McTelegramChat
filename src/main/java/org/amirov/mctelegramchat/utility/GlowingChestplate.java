package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.enchantments.GlowEnchantment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Provides a chestplate with a custom enchantment of glowing after a hit.
 */
public final class GlowingChestplate {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent CHESTPLATE_NAME = Component.text(
            "Glowing Chestplate", NamedTextColor.YELLOW);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "This Chestplate is of a Custom Enchantment of Glowing",
            NamedTextColor.BLUE)); }
//</editor-fold>

    /**
     * Provides a getter. Sets up and returns the item.
     *
     * @return Created chestplate with a custom enchantment of glowing.
     *
     * @see #getGlowingChestplateMeta(ItemStack)
     */
    public static @NotNull ItemStack getGlowingChestplate() {
        final ItemStack glowingChestplate = new ItemStack(
                Material.IRON_CHESTPLATE,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        glowingChestplate.addUnsafeEnchantment(
                GlowEnchantment.getInstance(),
                GlowEnchantment.getInstance().getMaxLevel());
        final ItemMeta glowingChestplateMeta = getGlowingChestplateMeta(glowingChestplate);
        glowingChestplate.setItemMeta(glowingChestplateMeta);
        return glowingChestplate;
    }

    /**
     * Sets the {@link ItemStack} object for the item and returns it.
     *
     * @param glowingChestplate Item itself.
     *
     * @return Set name and description of the item wrapped into the {@link ItemStack} object.
     */
    private static @NotNull ItemMeta getGlowingChestplateMeta(@NotNull ItemStack glowingChestplate) {
        final ItemMeta glowingChestplateMeta = glowingChestplate.getItemMeta();
        glowingChestplateMeta.displayName(CHESTPLATE_NAME);
        glowingChestplateMeta.lore(loreList);
        return glowingChestplateMeta;
    }
}
