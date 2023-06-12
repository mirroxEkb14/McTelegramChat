package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and provides a tnt that will kill a player when he tries to grab it.
 */
public final class LethalTntUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int LETHAL_TNT_AMOUNT = 1;
    private static final TextComponent LETHAL_TNT_NAME = Component.text(
            UtilityProperty.LETHAL_TNT_NAME.getValue(), NamedTextColor.GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            UtilityProperty.LETHAL_TNT_DESCRIPTION.getValue(),
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * @return Created a lethal tnt.
     */
    public static @NotNull ItemStack getLethalTnt() {
        final ItemStack lethalTnt = new ItemStack(Material.TNT, LETHAL_TNT_AMOUNT);
        final ItemMeta lethalTntMeta = getLethalTntMeta(lethalTnt);
        lethalTnt.setItemMeta(lethalTntMeta);
        return lethalTnt;
    }

    private static @NotNull ItemMeta getLethalTntMeta(@NotNull ItemStack lethalTnt) {
        final ItemMeta lethalTntMeta = lethalTnt.getItemMeta();
        lethalTntMeta.displayName(LETHAL_TNT_NAME);
        lethalTntMeta.lore(loreList);
        return lethalTntMeta;
    }
}
