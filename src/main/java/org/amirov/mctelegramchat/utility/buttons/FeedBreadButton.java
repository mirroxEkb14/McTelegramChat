package org.amirov.mctelegramchat.utility.buttons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.utility.CustomItemConstants;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Provides a bread item that triggers an event of feeding the player.
 */
public final class FeedBreadButton {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent ITEM_TITLE = Component.text(
            "Feed", NamedTextColor.DARK_GREEN);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "Restore hunger points",
            NamedTextColor.GOLD)); }
//</editor-fold>


    /**
     * Works as a getter. Sets up custom bread that restores player's hunger points.
     *
     * @return Custom bread item.
     *
     * @see #getHungerBreadMeta(ItemStack)
     */
    public static @NotNull ItemStack getFeedBread() {
        final ItemStack item = new ItemStack(
                Material.BREAD,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta itemMeta = getHungerBreadMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the metadata of the item and returns it as an {@link ItemMeta} object.
     *
     * @param hungerBread Item itself.
     * @return {@link ItemMeta} object with the name and description of the item.
     */
    private static @NotNull ItemMeta getHungerBreadMeta(@NotNull ItemStack hungerBread) {
        final ItemMeta hungerBreadMeta = hungerBread.getItemMeta();
        hungerBreadMeta.displayName(ITEM_TITLE);
        hungerBreadMeta.lore(loreList);
        return hungerBreadMeta;
    }
}
