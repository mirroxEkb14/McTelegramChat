package org.amirov.mctelegramchat.handlers.performers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * This performer class creates a hologram with text.
 * <p>
 * In fact, this hologram is just an invisible armor stand with a custom visible name.
 */
public final class HologramCreatingPerformer {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final double SECOND_LINE_X_ANGLE = 0D;
    private static final double SECOND_LINE_Y_ANGLE = -0.5D;
    private static final double SECOND_LINE_Z_ANGLE = 0D;

    private static final String HOLOGRAM_LINE_FIRST = "1. Line One";
    private static final String HOLOGRAM_LINE_SECOND = "2. Line Second";
//</editor-fold>

    /**
     * Sets the text for the hologram.
     *
     * @param player Player who clicked on the armor stand in the main inventory.
     */
    public static void performHologramCreating(@NotNull Player player) {
        createFirstLineHologram(player);
        createSecondLineHologram(player);
    }

    /**
     * Sets the first line of the hologram text.
     *
     * @param player Player who triggered the event.
     */
    private static void createFirstLineHologram(@NotNull Player player) {
        final ArmorStand hologram = (ArmorStand) player.getWorld().spawnEntity(
                player.getLocation(), EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setCustomNameVisible(true);
        hologram.customName(Component.text(HOLOGRAM_LINE_FIRST, NamedTextColor.BLUE));
    }

    /**
     * Sets the second line of the armor stand name.
     *
     * @param player Player who triggered the event.
     */
    private static void createSecondLineHologram(@NotNull Player player) {
        final ArmorStand hologram = (ArmorStand) player.getWorld().spawnEntity(
                player.getLocation().add(SECOND_LINE_X_ANGLE, SECOND_LINE_Y_ANGLE, SECOND_LINE_Z_ANGLE),
                EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setCustomNameVisible(true);
        hologram.customName(Component.text(HOLOGRAM_LINE_SECOND, NamedTextColor.GREEN));
    }
}
