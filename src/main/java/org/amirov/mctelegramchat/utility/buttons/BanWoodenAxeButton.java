package org.amirov.mctelegramchat.utility.buttons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.BanInventoryCommand;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and provides a wooden axe for a player to ban in {@link BanInventoryCommand}.
 */
public final class BanWoodenAxeButton {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int ITEM_AMOUNT = 1;
    private static final TextComponent ITEM_TITLE = Component.text(
            "Ban", NamedTextColor.DARK_GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
        "Ban this noob",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Works as a getter. Sets up a custom wooden axe representing a confirmation button and returns it.
     *
     * @return Custom wooden axe item.
     */
    public static @NotNull ItemStack getBanWoodenAxe() {
        final ItemStack item = new ItemStack(Material.WOODEN_AXE, ITEM_AMOUNT);
        final ItemMeta itemMeta = getBanWoodenAxeMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the item name and description and returns them as an {@link ItemMeta} object.
     *
     * @param banWoodenAxe Item itself.
     * @return Name and description of the item wrapped in the {@link ItemMeta} object.
     */
    private static @NotNull ItemMeta getBanWoodenAxeMeta(@NotNull ItemStack banWoodenAxe) {
        final ItemMeta banWoodenAxeMeta = banWoodenAxe.getItemMeta();
        banWoodenAxeMeta.displayName(ITEM_TITLE);
        banWoodenAxeMeta.lore(loreList);
        return banWoodenAxeMeta;
    }
}
