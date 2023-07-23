package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.enchantments.HemorrhageEnchantment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Provides a sword with a custom enchantment of hemorrhage after a hit.
 */
public final class HemorrhageSword {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int SWORD_AMOUNT = 1;
    private static final TextComponent SWORD_NAME = Component.text(
            "Hemorrhage Sword", NamedTextColor.YELLOW);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "This Sword is of a Custom Enchantment of Hemorrhage",
            NamedTextColor.BLUE)); }
//</editor-fold>

    /**
     * Provides a getter. Sets up and returns the item.
     *
     * @return Created sword with a custom enchantment of hemorrhage.
     */
    public static @NotNull ItemStack getHemorrhageSword() {
        final ItemStack hemorrhageSword = new ItemStack(Material.IRON_SWORD, SWORD_AMOUNT);
        hemorrhageSword.addUnsafeEnchantment(
                HemorrhageEnchantment.getInstance(),
                HemorrhageEnchantment.getInstance().getMaxLevel());
        final ItemMeta hemorrhageSwordMeta = getHemorrhageSwordMeta(hemorrhageSword);
        hemorrhageSword.setItemMeta(hemorrhageSwordMeta);
        return hemorrhageSword;
    }

    /**
     * Sets the {@link ItemStack} object for the item and returns it.
     *
     * @param hemorrhageSword Item itself.
     *
     * @return Set name and description of the item wrapped into the {@link ItemStack} object.
     */
    private static @NotNull ItemMeta getHemorrhageSwordMeta(@NotNull ItemStack hemorrhageSword) {
        final ItemMeta hemorrhageSwordMeta = hemorrhageSword.getItemMeta();
        hemorrhageSwordMeta.displayName(SWORD_NAME);
        hemorrhageSwordMeta.lore(loreList);
        return hemorrhageSwordMeta;
    }
}
