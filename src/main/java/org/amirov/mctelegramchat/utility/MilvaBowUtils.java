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
 * Provides a unique bow.
 */
public final class MilvaBowUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int MAX_ARROW_INFINITE_LEVEL = 1;
    private static final int MAX_ARROW_DAMAGE_LEVEL = 5;
    private static final int MAX_ARROW_FIRE_LEVEL = 1;
    private static final TextComponent BOW_NAME = Component.text(
            "Milva's Bow", NamedTextColor.GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text("The very unique bow which was owned by Maria Barring (known as Milva). " +
            "Made in the Far North, it was put up for sale on the Cidarian Seaside Bazaar")); }
//</editor-fold>

    /**
     * Getter. Sets up the {@link ItemStack} object and returns it.
     *
     * @return Created unique bow.
     *
     * @see #getMilvaBowMeta(ItemStack)
     */
    public static @NotNull ItemStack getMilvaBow() {
        final ItemStack milvaBow = new ItemStack(
                Material.BOW,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta milvaBowMeta = getMilvaBowMeta(milvaBow);
        milvaBow.setItemMeta(milvaBowMeta);
        return milvaBow;
    }

    /**
     * Sets the {@link ItemMeta} object and returns it.
     *
     * @param milvaBow Item itself.
     * @return Set {@link ItemMeta} object with a custom name and description.
     */
    private static @NotNull ItemMeta getMilvaBowMeta(@NotNull ItemStack milvaBow) {
        final ItemMeta milvaBowMeta = milvaBow.getItemMeta();
        milvaBowMeta.displayName(BOW_NAME);
        milvaBowMeta.lore(loreList);
        milvaBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, MAX_ARROW_DAMAGE_LEVEL, false);
        milvaBowMeta.addEnchant(Enchantment.ARROW_FIRE, MAX_ARROW_FIRE_LEVEL, false);
        milvaBowMeta.addEnchant(Enchantment.ARROW_INFINITE, MAX_ARROW_INFINITE_LEVEL, false);
        return milvaBowMeta;
    }
}
