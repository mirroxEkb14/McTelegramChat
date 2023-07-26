package org.amirov.mctelegramchat.utility.buttons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.BanInventoryCommand;
import org.amirov.mctelegramchat.utility.CustomItemConstants;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and provides a barrier item that represents a cancel button while banning a player in {@link BanInventoryCommand}.
 */
public final class BanBarrierButton {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent ITEM_TITLE = Component.text(
            "Back", NamedTextColor.RED);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
        "No ban",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Works as a getter. Sets up the custom barrier as a back button and returns it.
     *
     * @return Custom barrier item.
     */
    public static @NotNull ItemStack getBanBarrier() {
        final ItemStack item = new ItemStack(
                Material.BARRIER,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta itemMeta = getBanBarrierMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the {@link ItemMeta} object for the item and returns it.
     *
     * @param banBarrier Item itself.
     * @return {@link ItemMeta} object with the name and description of the item.
     */
    private static @NotNull ItemMeta getBanBarrierMeta(@NotNull ItemStack banBarrier) {
        final ItemMeta banBarrierMeta = banBarrier.getItemMeta();
        banBarrierMeta.displayName(ITEM_TITLE);
        banBarrierMeta.lore(loreList);
        return banBarrierMeta;
    }
}
