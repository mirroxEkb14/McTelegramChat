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
            "Suicide", NamedTextColor.DARK_RED);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
        "Kill yourself instantly",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Works as a getter. Sets up the custom tnt object and returns it.
     *
     * @return Created lethal tnt.
     */
    public static @NotNull ItemStack getLethalTnt() {
        final ItemStack lethalTnt = new ItemStack(Material.TNT, LETHAL_TNT_AMOUNT);
        final ItemMeta lethalTntMeta = getLethalTntMeta(lethalTnt);
        lethalTnt.setItemMeta(lethalTntMeta);
        return lethalTnt;
    }

    /**
     * Sets up the name and description for the tnt item and return them as an {@link ItemMeta} object.
     *
     * @param lethalTnt Item itself.
     * @return {@link ItemMeta} object with the set name and description of the item.
     */
    private static @NotNull ItemMeta getLethalTntMeta(@NotNull ItemStack lethalTnt) {
        final ItemMeta lethalTntMeta = lethalTnt.getItemMeta();
        lethalTntMeta.displayName(LETHAL_TNT_NAME);
        lethalTntMeta.lore(loreList);
        return lethalTntMeta;
    }
}
