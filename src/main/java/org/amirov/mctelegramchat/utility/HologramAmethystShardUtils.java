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
 * Provides an item that creates a hologram (invisible named armor stand) on the block the player is standing on.
 */
public final class HologramAmethystShardUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int ITEM_AMOUNT = 1;
    private static final TextComponent ITEM_TITLE = Component.text(
            "Hologram", NamedTextColor.BLUE);
    private static final ArrayList<TextComponent> loreList = new ArrayList<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initializer Block">
    static { loreList.add(Component.text(
            "Create hologram suspended in air",
            NamedTextColor.GOLD)); }
//</editor-fold>

    /**
     * Getter. Sets up an amethyst shard stand of the type of {@link ItemStack}.
     *
     * @return Custom amethyst shard item.
     */
    public static @NotNull ItemStack getHologramAmethystShard() {
        final ItemStack item = new ItemStack(Material.AMETHYST_SHARD, ITEM_AMOUNT);
        final ItemMeta itemMeta = getHologramAmethystShardMeta(item);
        item.setItemMeta(itemMeta);
        return item;
    }

    /**
     * Sets up the item name and description and returns them as an {@link ItemMeta} object.
     *
     * @param hologramAmethystShard Item itself.
     * @return {@link ItemMeta} object with the set name and description.
     */
    private static @NotNull ItemMeta getHologramAmethystShardMeta(@NotNull ItemStack hologramAmethystShard) {
        final ItemMeta hologramAmethystShardMeta = hologramAmethystShard.getItemMeta();
        hologramAmethystShardMeta.displayName(ITEM_TITLE);
        hologramAmethystShardMeta.lore(loreList);
        return hologramAmethystShardMeta;
    }
}
