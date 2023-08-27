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
 * Provides an item that allows a player to fly.
 */
public final class FlyElytraButton {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent ITEM_TITLE = Component.text(
            "Fly", NamedTextColor.LIGHT_PURPLE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "Start flying",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Getter. Sets up custom elytra that allows to fly.
     *
     * @return Custom elytra of the type of {@link ItemStack}.
     *
     * @see #getFlyElytraMeta(ItemStack)
     */
    public static @NotNull ItemStack getFlyElytra() {
        final ItemStack item = new ItemStack(
                Material.ELYTRA,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta itemMeta = getFlyElytraMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the name and description of the item and returns it as an {@link ItemMeta} object.
     *
     * @param flyElytra Item itself.
     * @return {@link ItemMeta} object.
     */
    private static @NotNull ItemMeta getFlyElytraMeta(@NotNull ItemStack flyElytra) {
        final ItemMeta flyElytraMeta = flyElytra.getItemMeta();
        flyElytraMeta.displayName(ITEM_TITLE);
        flyElytraMeta.lore(loreList);
        return flyElytraMeta;
    }
}
