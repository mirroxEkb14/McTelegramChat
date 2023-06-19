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
 * Creates and provides a crossbow that shots with a lightning.
 */
public final class LightningCrossbowUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int CROSSBOW_AMOUNT = 1;
    private static final int MAX_MULTISHOT_LEVEL = 1;
    private static final TextComponent CROSSBOW_NAME = Component.text(
            "Lightning crossbow", NamedTextColor.BLUE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "Shot with a lightning", NamedTextColor.GOLD)); }
//</editor-fold>


    /**
     * Getter. Returns the item as a {@link ItemStack} object.
     *
     * @return Created lighting crossbow.
     */
    public static @NotNull ItemStack getLightningCrossbow() {
        final ItemStack lightningCrossbow = new ItemStack(Material.CROSSBOW, CROSSBOW_AMOUNT);
        final ItemMeta lightningCrossbowMeta = getLightningCrossbowMeta(lightningCrossbow);
        lightningCrossbow.setItemMeta(lightningCrossbowMeta);
        return lightningCrossbow;
    }

    /**
     * Sets the metadata of the item and returns it as a {@link ItemMeta} object.
     *
     * @param lightningCrossbow Item itself.
     * @return {@link ItemMeta} object with the set data.
     */
    private static @NotNull ItemMeta getLightningCrossbowMeta(@NotNull ItemStack lightningCrossbow) {
        final ItemMeta lightningCrossbowMeta = lightningCrossbow.getItemMeta();
        lightningCrossbowMeta.displayName(CROSSBOW_NAME);
        lightningCrossbowMeta.lore(loreList);
        lightningCrossbowMeta.addEnchant(Enchantment.MULTISHOT, MAX_MULTISHOT_LEVEL, false);
        return lightningCrossbowMeta;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getLightningCrossbowName() { return CROSSBOW_NAME; }
//</editor-fold>
}
