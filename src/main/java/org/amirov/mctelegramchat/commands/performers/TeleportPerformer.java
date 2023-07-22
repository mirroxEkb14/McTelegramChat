package org.amirov.mctelegramchat.commands.performers;

import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Random;

/**
 * This class contains static methods that are used when executing the random teleportation command. It does the whole
 * logic of creating a random point in the world to teleport to.
 */
public class TeleportPerformer {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int X_BOUND = 25000;
    private static final int Y_BOUND = 150;
    private static final int Z_BOUND = 25000;

    /**
     * This constant is used to get information from the blocks below and above of the current one.
     */
    private static final int Y_COORDINATE_VALUE = 1;

    private static final HashSet<Material> dangerousBlocks = new HashSet<>();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static blocks">
    static {
        dangerousBlocks.add(Material.LAVA);
    }
//</editor-fold>

    /**
     * Generates a random and safe location for a player to teleport to.
     *
     * @param player Player who triggered the command.
     * @return Location a player will teleport to.
     */
    public static @NotNull Location generateLocation(@NotNull Plugin plugin, @NotNull Player player) {
        final Random random = new Random();
        int x, y, z;
        if (plugin.getConfig().getBoolean(ConfigProperty.WORLD_BORDER.getKeyName())) {
            final int borderValue = plugin.getConfig().getInt(ConfigProperty.BORDER.getKeyName());
            x = random.nextInt(borderValue);
            y = random.nextInt(Y_BOUND);
            z = random.nextInt(borderValue);
        } else {
            x = random.nextInt(X_BOUND);
            y = random.nextInt(Y_BOUND);
            z = random.nextInt(Z_BOUND);
        }
        Location randomLocation = new Location(player.getWorld(), x, y, z);

        y = randomLocation.getWorld().getHighestBlockYAt(randomLocation);
        randomLocation.setY(y);

        while(!isLocationSafe(randomLocation)) {
            randomLocation = generateLocation(plugin, player);
        }
        return randomLocation;
    }

    /**
     * Determines either the location is safe or not by examining the blocks on this location.
     *
     * @param location Location to teleport to.
     * @return {@code true} if the passed location is safe (location does not contain dangerous blocks),
     * {@code false} otherwise.
     */
    private static boolean isLocationSafe(@NotNull Location location) {
        final int x = location.getBlockX();
        final int y = location.getBlockY();
        final int z = location.getBlockZ();

        final Block currentBlock = location.getWorld().getBlockAt(x, y, z);
        final Block belowBlock = location.getWorld().getBlockAt(x, y - Y_COORDINATE_VALUE, z);
        final Block aboveBlock = location.getWorld().getBlockAt(x, y + Y_COORDINATE_VALUE, z);

        final boolean isNotLavaBlock = !(dangerousBlocks.contains(belowBlock.getType()));
        final boolean isCurrentBlockSolid = currentBlock.getType().isSolid();
        final boolean isAboveBlockSolid = aboveBlock.getType().isSolid();

        return isNotLavaBlock || (isCurrentBlockSolid || isAboveBlockSolid);
    }
}
