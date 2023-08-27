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
 * Provides a custom totem item that represents a god mode for a player who becomes invulnerable.
 */
public final class GodUndyingTotemUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent ITEM_TITLE = Component.text(
            "God mode", NamedTextColor.DARK_AQUA);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "Become invulnerable",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Works as a getter. Sets up the item and returns it as an {@link ItemStack} object.
     *
     * @return Custom undying totem item.
     *
     * @see #getGodUndyingTotemMeta(ItemStack)
     */
    public static @NotNull ItemStack getGodUndyingTotem() {
        final ItemStack item = new ItemStack(
                Material.TOTEM_OF_UNDYING,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta itemMeta = getGodUndyingTotemMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the item name and description and returns them as an {@link ItemMeta} object.
     *
     * @param godUndyingTotem Item itself.
     * @return Name and description of the item wrapped in the {@link ItemMeta} object.
     */
    private static @NotNull ItemMeta getGodUndyingTotemMeta(@NotNull ItemStack godUndyingTotem) {
        final ItemMeta godUndyingTotemMeta = godUndyingTotem.getItemMeta();
        godUndyingTotemMeta.displayName(ITEM_TITLE);
        godUndyingTotemMeta.lore(loreList);
        return godUndyingTotemMeta;
    }
}
