package org.amirov.mctelegramchat.utility.buttons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.listeners.performers.ArmoryInventoryPerformer;
import org.amirov.mctelegramchat.listeners.performers.MenuInventoryPerformer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * This class represents a back button for inventories.
 * <p>
 * Usage example:
 * <br>
 * After clicking on this button the player goes back from {@link  ArmoryInventoryPerformer} to {@link MenuInventoryPerformer}.
 */
public class BackWoolButton {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int ITEM_AMOUNT = 1;
    private static final TextComponent ITEM_TITLE = Component.text(
            "Wool Button", NamedTextColor.RED);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "Go back",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Works as a getter. Sets up a custom wool representing a back button.
     *
     * @return Custom red wool item.
     */
    public static @NotNull ItemStack getBackWool() {
        final ItemStack item = new ItemStack(Material.RED_WOOL, ITEM_AMOUNT);
        final ItemMeta itemMeta = getBackWoolMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the item name and description and returns them as an {@link ItemMeta} object.
     *
     * @param backWool Item itself.
     * @return Name and description of the item wrapped in the {@link ItemMeta} object.
     */
    private static @NotNull ItemMeta getBackWoolMeta(@NotNull ItemStack backWool) {
        final ItemMeta backWoolMeta = backWool.getItemMeta();
        backWoolMeta.displayName(ITEM_TITLE);
        backWoolMeta.lore(loreList);
        return backWoolMeta;
    }
}
