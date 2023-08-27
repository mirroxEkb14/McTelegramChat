package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.utility.LightningCrossbowUtils;
import org.amirov.mctelegramchat.utility.TeleportBowUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Entity;

import java.util.Objects;

/**
 * Monitors the events of an arrow landing.
 */
public record ArrowListener(McTelegramChat plugin) implements Listener {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final float soundVolume = 1.0f;
    private static final float soundPitch = 1.0f;

    private static final String ARROW_LANDING_TELEPORTED = "Teleported to Arrow Point";
    private static final String ARROW_LANDING_LIGHTNING = "Lighting Hit";
//</editor-fold>

    /**
     * Teleports a player to the point where the arrow landed.
     *
     * @param event Event of a projectile hitting an object.
     *
     * @see #isArrow(ProjectileHitEvent)
     * @see #shotByPlayer(ProjectileHitEvent)
     * @see #teleportToArrowLocation(ProjectileHitEvent, Player)
     * @see #hitLightningAtArrowLocation(ProjectileHitEvent, Player)
     */
    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        if (isArrow(event) && shotByPlayer(event)) {
            final Player player = (Player) event.getEntity().getShooter();
            Objects.requireNonNull(player);
            final ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (isTeleportBow(itemInMainHand))
                teleportToArrowLocation(event, player);
            else if (isLightingCrossbow(itemInMainHand))
                hitLightningAtArrowLocation(event, player);
        }
    }

    /**
     * Teleport a player to the arrow landing location, removes the arrow itself, sends a message to this player and
     * plays a sound at the location where a player was teleported to.
     *
     * @param event Event of an arrow landing.
     * @param player Player who shot this arrow.
     */
    private void teleportToArrowLocation(@NotNull ProjectileHitEvent event, @NotNull Player player) {
        final Location location = event.getEntity().getLocation();
        player.teleport(location);
        event.getEntity().remove();
        player.sendMessage(ARROW_LANDING_TELEPORTED);
        player.playSound(player, Sound.ENTITY_ARROW_HIT, soundVolume, soundPitch);
    }

    /**
     * Creates an {@link Entity} of a lightning that hits the location where the arrow was shot.
     *
     * @param event Event when an arrow is landing.
     * @param player Player shot this arrow.
     */
    private void hitLightningAtArrowLocation(@NotNull ProjectileHitEvent event, @NotNull Player player) {
        final Location arrowLocation = event.getEntity().getLocation();
        player.getWorld().spawnEntity(arrowLocation, EntityType.LIGHTNING);

        player.sendMessage(ARROW_LANDING_LIGHTNING);
    }

    /**
     * Checks if the projectile is an arrow.
     *
     * @param event Projectile event.
     * @return {@code true} if it is an arrow, {@code false} otherwise.
     */
    private boolean isArrow(@NotNull ProjectileHitEvent event) {
        return event.getEntity().getType() == EntityType.ARROW;
    }

    /**
     * Checks if the projectile event was triggered by a player.
     *
     * @param event Projectile event.
     * @return {@code true} if it is a player, {@code false} otherwise.
     */
    private boolean shotByPlayer(@NotNull ProjectileHitEvent event) {
        return event.getEntity().getShooter() instanceof Player;
    }

    /**
     * Checks if a player was holding a teleport bow when he shot an arrow.
     *
     * @param item Item in the player's main hand.
     * @return {@code true} if it is a teleport bow, {@code false} otherwise.
     */
    private boolean isTeleportBow(@NotNull ItemStack item) {
        final ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return false;

        final Component itemComponent = itemMeta.displayName();
        Objects.requireNonNull(itemComponent);
        return itemComponent.equals(TeleportBowUtils.getBowName());
    }

    /**
     * Checks if a player was holding a lighting crossbow when he shot an arrow.
     *
     * @param item Item in the player's main hand.
     * @return {@code true} if it is a lighting crossbow, {@code false} otherwise.
     */
    private boolean isLightingCrossbow(@NotNull ItemStack item) {
        final ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return false;

        final Component itemComponent = itemMeta.displayName();
        Objects.requireNonNull(itemComponent);
        return itemComponent.equals(LightningCrossbowUtils.getLightningCrossbowName());
    }
}
