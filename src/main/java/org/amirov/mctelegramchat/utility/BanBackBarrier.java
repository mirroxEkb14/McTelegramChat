package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Creates and provides a barrier item that represents a cancel button while banning a player.
 */
public class BanBackBarrier {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int ITEM_AMOUNT = 1;
    private static final TextComponent ITEM_TITLE = Component.text(
            "Back", NamedTextColor.RED);
//</editor-fold>

    public static @NotNull ItemStack getBanBackBarrier() {
        final ItemStack item = new ItemStack(Material.BARRIER, ITEM_AMOUNT);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(ITEM_TITLE);
        return item;
    }
}
