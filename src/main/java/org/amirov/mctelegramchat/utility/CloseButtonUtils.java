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
 * Creates and provides a barrier that represents a close button for GUI menu.
 */
public final class CloseButtonUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int CLOSE_BUTTON_AMOUNT = 1;
    private static final TextComponent CLOSE_BUTTON_NAME = Component.text(
            UtilityProperty.CLOSE_BUTTON_NAME.getValue(), NamedTextColor.RED);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            UtilityProperty.CLOSE_BUTTON_DESCRIPTION.getValue(),
        NamedTextColor.LIGHT_PURPLE)); }
//</editor-fold>

    /**
     * @return Created close button.
     */
    public static @NotNull ItemStack getCloseButton() {
        final ItemStack closeButton = new ItemStack(Material.BARRIER, CLOSE_BUTTON_AMOUNT);
        final ItemMeta closeButtonMeta = getCloseButtonMeta(closeButton);
        closeButton.setItemMeta(closeButtonMeta);
        return closeButton;
    }

    private static @NotNull ItemMeta getCloseButtonMeta(@NotNull ItemStack closeButton) {
        final ItemMeta closeButtonMeta = closeButton.getItemMeta();
        closeButtonMeta.displayName(CLOSE_BUTTON_NAME);
        closeButtonMeta.lore(loreList);
        return closeButtonMeta;
    }
}
