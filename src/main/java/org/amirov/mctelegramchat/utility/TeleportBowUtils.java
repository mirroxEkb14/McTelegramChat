package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and provides a teleport bow.
 */
public final class TeleportBowUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int BOW_AMOUNT = 1;
    private static final int MAX_ARROW_INFINITE_LEVEL = 1;
    private static final TextComponent BOW_NAME = Component.text(
            "Teleport Bow", NamedTextColor.BLUE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text("Teleport to arrow location")); }
//</editor-fold>

    /**
     * Getter. Sets up the {@link ItemStack} object and returns it.
     *
     * @return Created teleport bow.
     */
    public static @NotNull ItemStack getTeleportBow() {
        final ItemStack teleportBow = new ItemStack(Material.BOW, BOW_AMOUNT);
        final ItemMeta teleportBowMeta = getTeleportBowMeta(teleportBow);
        teleportBow.setItemMeta(teleportBowMeta);
        return teleportBow;
    }

    /**
     * Sets the {@link ItemMeta} object and returns it.
     *
     * @param teleportBow Item itself.
     * @return Set {@link ItemMeta} object with a custom name and description.
     */
    private static @NotNull ItemMeta getTeleportBowMeta(@NotNull ItemStack teleportBow) {
        final ItemMeta teleportBowMeta = teleportBow.getItemMeta();
        teleportBowMeta.displayName(BOW_NAME);
        teleportBowMeta.lore(loreList);
        teleportBowMeta.addEnchant(Enchantment.ARROW_INFINITE, MAX_ARROW_INFINITE_LEVEL, false);
        return teleportBowMeta;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getBowName() { return BOW_NAME; }
//</editor-fold>
}
