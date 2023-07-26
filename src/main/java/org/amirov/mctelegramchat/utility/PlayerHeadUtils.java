package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and provides a player's head with his health and exp stats.
 */
public final class PlayerHeadUtils {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String PLAYER_HEAD_LORE_HEALTH = "Health: ";
    private static final String PLAYER_HEAD_LORE_EXP = "EXP: ";
    private static final ArrayList<TextComponent> PLAYER_HEAD_LORE = new ArrayList<>();
//</editor-fold>

    /**
     * Returns the player's head that was passed as an argument.
     *
     * @param currentPlayer Player whose head the method returns.
     * @return Passed player's head with his stats.
     */
    public static @NotNull ItemStack getPlayerHead(@NotNull Player currentPlayer) {
        final ItemStack item = new ItemStack(
                Material.PLAYER_HEAD,
                CustomItemConstants.CUSTOM_ITEM_AMOUNT.getValue());
        final ItemMeta headMeta = getPlayerHeadMeta(item, currentPlayer);
        item.setItemMeta(headMeta);
        return item;
    }

    /**
     * Sets the {@link ItemMeta} for the item and returns it.
     *
     * @param playerHead Item itself.
     * @param currentPlayer {@link Player} who is currently online for the ban menu.
     *
     * @return {@link ItemMeta} object with the set name and description of the item.
     */
    private static @NotNull ItemMeta getPlayerHeadMeta(@NotNull ItemStack playerHead, @NotNull Player currentPlayer) {
        final ItemMeta playerHeadMeta = playerHead.getItemMeta();
        playerHeadMeta.displayName(currentPlayer.displayName());
        initHeadLore(currentPlayer.getHealth(), currentPlayer.getExp());
        playerHeadMeta.lore(PLAYER_HEAD_LORE);
        resetHeadLore();
        return playerHeadMeta;
    }

    /**
     * Sets the lore list for the head.
     * <p>
     * Each row of the lore represents a single player's stat.
     *
     * @param health Health stat.
     * @param exp Experience stat.
     */
    private static void initHeadLore(double health, float exp) {
        final TextComponent healthTitle = Component.text(
                PLAYER_HEAD_LORE_HEALTH, NamedTextColor.GOLD);
        final TextComponent healthComponent = Component.text(
                health, NamedTextColor.RED);
        PLAYER_HEAD_LORE.add(healthTitle.append(healthComponent));

        final TextComponent expTitle = Component.text(
                PLAYER_HEAD_LORE_EXP, NamedTextColor.GOLD);
        final TextComponent expComponent = Component.text(
                exp, NamedTextColor.AQUA);
        PLAYER_HEAD_LORE.add(expTitle.append(expComponent));
    }

    /**
     * Clears all the contents in the player's head lore.
     */
    private static void resetHeadLore() { PLAYER_HEAD_LORE.clear(); }
}
