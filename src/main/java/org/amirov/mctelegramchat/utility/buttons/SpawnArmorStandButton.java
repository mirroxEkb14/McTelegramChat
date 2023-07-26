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
 * Provides a custom armor stand object.
 */
public final class SpawnArmorStandButton {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent ITEM_TITLE = Component.text(
            "Armor Stand", NamedTextColor.WHITE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
        "Spawn a custom armor stand",
            NamedTextColor.GOLD)); }
//</editor-fold>


    /**
     * Getter. Sets up an armor stand and returns it as an {@link ItemStack} object.
     *
     * @return Custom armor stand item.
     */
    public static @NotNull ItemStack getSpawnArmorStand() {
        final ItemStack item = new ItemStack(
                Material.ARMOR_STAND,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta itemMeta = getCustomArmorStandMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the item name and description and returns them as an {@link ItemMeta} object.
     *
     * @param customArmorStand Item itself.
     * @return Name and description of the item wrapped in the {@link ItemMeta} object.
     */
    private static @NotNull ItemMeta getCustomArmorStandMeta(@NotNull ItemStack customArmorStand) {
        final ItemMeta customArmorStandMeta = customArmorStand.getItemMeta();
        customArmorStandMeta.displayName(ITEM_TITLE);
        customArmorStandMeta.lore(loreList);
        return customArmorStandMeta;
    }
}
