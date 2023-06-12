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
public final class UrsineCrossbowUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int CROSSBOW_AMOUNT = 1;
    private static final int MAX_MULTISHOT_LEVEL = 1;
    private static final TextComponent CROSSBOW_NAME = Component.text(
            UtilityProperty.CROSSBOW_NAME.getValue(), NamedTextColor.BLUE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(UtilityProperty.CROSSBOW_DESCRIPTION.getValue(), NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * @return Created lighting crossbow.
     */
    public static @NotNull ItemStack getLightningCrossbow() {
        final ItemStack lightningCrossbow = new ItemStack(Material.CROSSBOW, CROSSBOW_AMOUNT);
        final ItemMeta lightningCrossbowMeta = getLightningCrossbowMeta(lightningCrossbow);
        lightningCrossbow.setItemMeta(lightningCrossbowMeta);
        return lightningCrossbow;
    }

    private static @NotNull ItemMeta getLightningCrossbowMeta(@NotNull ItemStack lightningCrossbow) {
        final ItemMeta lightningCrossbowMeta = lightningCrossbow.getItemMeta();
        lightningCrossbowMeta.displayName(CROSSBOW_NAME);
        lightningCrossbowMeta.lore(loreList);
        lightningCrossbowMeta.addEnchant(Enchantment.MULTISHOT, MAX_MULTISHOT_LEVEL, false);
        return lightningCrossbowMeta;
    }

    public static TextComponent getCrossbowName() { return CROSSBOW_NAME; }

    public static int getCrossbowAmount() { return CROSSBOW_AMOUNT; }
}
