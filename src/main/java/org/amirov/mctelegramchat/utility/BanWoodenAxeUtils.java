package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.BanGUICommand;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Creates and provides a wooden axe for a player to ban in {@link BanGUICommand}.
 */
public class BanWoodenAxeUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int ITEM_AMOUNT = 1;
    private static final TextComponent ITEM_TITLE = Component.text(
            "Ban", NamedTextColor.DARK_GREEN);
//</editor-fold>

    public static @NotNull ItemStack getBanWoodenAxe() {
        final ItemStack item = new ItemStack(Material.WOODEN_AXE, ITEM_AMOUNT);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(ITEM_TITLE);
        return item;
    }
}
