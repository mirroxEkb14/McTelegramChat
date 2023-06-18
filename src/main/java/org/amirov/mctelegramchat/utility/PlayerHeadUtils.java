package org.amirov.mctelegramchat.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.logging.Loggers;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Creates and provides a player's head with his health and exp stats.
 */
public class PlayerHeadUtils {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int PLAYER_HEAD_AMOUNT = 1;
    private static final ArrayList<TextComponent> PLAYER_HEAD_LORE = new ArrayList<>();
//</editor-fold>

    public static @NotNull ItemStack getPlayerHead(@NotNull Player currentPlayer) {
        final ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, PLAYER_HEAD_AMOUNT);
        final ItemMeta headMeta = playerHead.getItemMeta();

        Loggers.printInfoLog("BEFORE");
        headMeta.displayName(currentPlayer.displayName());
        Loggers.printInfoLog("AFTER");
        initHeadLore(currentPlayer.getHealth(), currentPlayer.getExp());
        headMeta.lore(PLAYER_HEAD_LORE);
        playerHead.setItemMeta(headMeta);
        return playerHead;
    }

    private static void initHeadLore(double health, float exp) {
        final TextComponent healthTitle = Component.text(
                UtilityProperty.PLAYER_HEAD_LORE_HEALTH.getValue(), NamedTextColor.GOLD);
        final TextComponent healthComponent = Component.text(
                health, NamedTextColor.RED);
        PLAYER_HEAD_LORE.add(healthTitle.append(healthComponent));

        final TextComponent expTitle = Component.text(
                UtilityProperty.PLAYER_HEAD_LORE_EXP.getValue(), NamedTextColor.GOLD);
        final TextComponent expComponent = Component.text(
                exp, NamedTextColor.AQUA);
        PLAYER_HEAD_LORE.add(expTitle.append(expComponent));
    }
}
