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
public final class NetheriteSword {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int SILVER_SWORD_AMOUNT = 1;
    private static final int MAX_LOOTING_LEVEL = 3;
    private static final TextComponent SILVER_SWORD_NAME = Component.text(
            UtilityProperty.SILVER_SWORD_NAME.getValue(), NamedTextColor.GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            UtilityProperty.SILVER_SWORD_DESCRIPTION.getValue(),
            NamedTextColor.BLUE)); }
//</editor-fold>

    /**
     * @return Created a unique silver sword.
     */
    public static @NotNull ItemStack getNetheriteSword() {
        final ItemStack netheriteSword = new ItemStack(Material.NETHERITE_SWORD, SILVER_SWORD_AMOUNT);
        final ItemMeta netheriteSwordMeta = getNetheriteSwordMeta(netheriteSword);
        netheriteSword.setItemMeta(netheriteSwordMeta);
        return netheriteSword;
    }

    private static @NotNull ItemMeta getNetheriteSwordMeta(@NotNull ItemStack netheriteSword) {
        final ItemMeta netheriteSwordMeta = netheriteSword.getItemMeta();
        netheriteSwordMeta.displayName(SILVER_SWORD_NAME);
        netheriteSwordMeta.lore(loreList);
        netheriteSwordMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, MAX_LOOTING_LEVEL, false);
        return netheriteSwordMeta;
    }
}
