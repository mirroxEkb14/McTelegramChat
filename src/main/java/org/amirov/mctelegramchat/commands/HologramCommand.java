package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.properties.HologramLine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Creates and provides the hologram represented as an invisible armor stand with a visible custom name.
 */
public final class HologramCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final double SECOND_LINE_X_ANGLE = 0D;
    private static final double SECOND_LINE_Y_ANGLE = -0.5D;
    private static final double SECOND_LINE_Z_ANGLE = 0D;
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof Player player) {
            createFirstLineHologram(player);
            createSecondLineHologram(player);
        }
        return true;
    }

    private void createFirstLineHologram(@NotNull Player player) {
        final ArmorStand hologram = (ArmorStand) player.getWorld().spawnEntity(
                player.getLocation(), EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setCustomNameVisible(true);
        hologram.customName(Component.text(HologramLine.HOLOGRAM_LINE_FIRST.getLine(), NamedTextColor.BLUE));
    }

    private void createSecondLineHologram(@NotNull Player player) {
        final ArmorStand hologram = (ArmorStand) player.getWorld().spawnEntity(
                player.getLocation().add(SECOND_LINE_X_ANGLE, SECOND_LINE_Y_ANGLE, SECOND_LINE_Z_ANGLE),
                EntityType.ARMOR_STAND);
        hologram.setVisible(false);
        hologram.setGravity(false);
        hologram.setCustomNameVisible(true);
        hologram.customName(Component.text(HologramLine.HOLOGRAM_LINE_SECOND.getLine(), NamedTextColor.GREEN));
    }
}
