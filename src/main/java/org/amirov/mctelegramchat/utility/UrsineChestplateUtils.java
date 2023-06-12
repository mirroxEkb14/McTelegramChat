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
 * Creates and provides a unique armor.
 */
public final class UrsineChestplateUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int CHESTPLATE_AMOUNT = 1;
    private static final int MAX_PROJECTILE_PROTECTION_LEVEL = 4;
    private static final TextComponent CHESTPLATE_NAME = Component.text(
            UtilityProperty.CHESTPLATE_NAME.getValue(), NamedTextColor.BLUE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            UtilityProperty.CHESTPLATE_DESCRIPTION.getValue(),
            NamedTextColor.LIGHT_PURPLE)); }
//</editor-fold>

    /**
     * @return Created a unique armor.
     */
    public static @NotNull ItemStack getChestplate() {
        final ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE, CHESTPLATE_AMOUNT);
        final ItemMeta chestplateMeta = getChestplateMeta(chestplate);
        chestplate.setItemMeta(chestplateMeta);
        return chestplate;
    }

    private static @NotNull ItemMeta getChestplateMeta(@NotNull ItemStack chestplate) {
        final ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.displayName(CHESTPLATE_NAME);
        chestplateMeta.lore(loreList);
        chestplateMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE,
                MAX_PROJECTILE_PROTECTION_LEVEL,
                false);
        return chestplateMeta;
    }
}
