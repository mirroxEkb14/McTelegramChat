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
 * Creates and provides a unique silver sword.
 */
public final class SilverSwordUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int MAX_LOOTING_LEVEL = 3;
    private static final TextComponent SILVER_SWORD_NAME = Component.text(
            "Ursine silver sword", NamedTextColor.GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
        "This sword is part of the Bear School Gear",
            NamedTextColor.BLUE)); }
//</editor-fold>

    /**
     * Works as a getter. Sets up the item and returns it.
     *
     * @return Created unique silver sword.
     *
     * @see #getNetheriteSwordMeta(ItemStack)
     */
    public static @NotNull ItemStack getNetheriteSword() {
        final ItemStack netheriteSword = new ItemStack(
                Material.NETHERITE_SWORD,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta netheriteSwordMeta = getNetheriteSwordMeta(netheriteSword);
        netheriteSword.setItemMeta(netheriteSwordMeta);
        return netheriteSword;
    }

    /**
     * Sets the {@link ItemStack} object for the item and returns it.
     *
     * @param netheriteSword Item itself.
     * @return Set name and description of the item wrapped into the {@link ItemStack} object.
     */
    private static @NotNull ItemMeta getNetheriteSwordMeta(@NotNull ItemStack netheriteSword) {
        final ItemMeta netheriteSwordMeta = netheriteSword.getItemMeta();
        netheriteSwordMeta.displayName(SILVER_SWORD_NAME);
        netheriteSwordMeta.lore(loreList);
        netheriteSwordMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, MAX_LOOTING_LEVEL, false);
        return netheriteSwordMeta;
    }
}
