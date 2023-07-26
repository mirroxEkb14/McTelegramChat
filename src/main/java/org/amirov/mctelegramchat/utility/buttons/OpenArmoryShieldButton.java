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
 * Provides a custom shield that represents a button by clicking on which an inventory with the gear opens.
 */
public final class OpenArmoryShieldButton {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent ITEM_TITLE = Component.text(
            "Gear", NamedTextColor.GRAY);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "Open armory",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Getter. Sets up a custom shield representing a button that opens an armory with some unique items.
     *
     * @return Custom shield item.
     */
    public static @NotNull ItemStack getOpenArmoryShield() {
        final ItemStack item = new ItemStack(
                Material.SHIELD,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta itemMeta = getOpenArmoryShieldMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the item metadata and returns it as an {@link ItemMeta} object.
     *
     * @param openArmoryShield Item itself.
     * @return {@link ItemMeta} object with the set metadata of the item.
     */
    private static @NotNull ItemMeta getOpenArmoryShieldMeta(@NotNull ItemStack openArmoryShield) {
        final ItemMeta openArmoryShieldMeta = openArmoryShield.getItemMeta();
        openArmoryShieldMeta.displayName(ITEM_TITLE);
        openArmoryShieldMeta.lore(loreList);
        return openArmoryShieldMeta;
    }
}
