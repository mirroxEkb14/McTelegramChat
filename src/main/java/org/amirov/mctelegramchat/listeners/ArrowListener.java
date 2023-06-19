package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.properties.ChatMessage;
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

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final float soundVolume = 1.0f;
    private static final float soundPitch = 1.0f;
//</editor-fold>

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
     * @param e Event of an arrow landing.
     * @param p Player who shot this arrow.
     */
    private void teleportToArrowLocation(@NotNull ProjectileHitEvent e, @NotNull Player p) {
        final Location location = e.getEntity().getLocation();
        p.teleport(location);
        e.getEntity().remove();
        p.sendMessage(Component.text(ChatMessage.ON_ARROW_LANDING_TELEPORT.getMessage()));
        p.playSound(p, Sound.ENTITY_ARROW_HIT, soundVolume, soundPitch);
    }

    /**
     * Creates an {@link Entity} of a lightning that hits the location where the arrow was shot.
     *
     * @param e Event when an arrow is landing.
     * @param p Player shot this arrow.
     */
    private void hitLightningAtArrowLocation(@NotNull ProjectileHitEvent e, @NotNull Player p) {
        final Location arrowLocation = e.getEntity().getLocation();
        p.getWorld().spawnEntity(arrowLocation, EntityType.LIGHTNING);

        p.sendMessage(Component.text(ChatMessage.ON_ARROW_LANDING_LIGHTNING.getMessage(), NamedTextColor.BLUE));
    }

    /**
     * Checks if the projectile is an arrow.
     *
     * @param e Projectile event.
     * @return {@code true} if it is an arrow, {@code false} otherwise.
     */
    private boolean isArrow(@NotNull ProjectileHitEvent e) {
        return e.getEntity().getType() == EntityType.ARROW;
    }

    /**
     * Checks if the projectile event was triggered by a player.
     *
     * @param e Projectile event.
     * @return {@code true} if it is a player, {@code false} otherwise.
     */
    private boolean shotByPlayer(@NotNull ProjectileHitEvent e) {
        return e.getEntity().getShooter() instanceof Player;
    }

    /**
     * Checks if a player was holding a teleport bow when he shot an arrow.
     *
     * @param i Item in the player's main hand.
     * @return {@code true} if it is a teleport bow, {@code false} otherwise.
     */
    private boolean isTeleportBow(@NotNull ItemStack i) {
        final ItemMeta itemMeta = i.getItemMeta();
        if (itemMeta == null) return false;

        final Component itemComponent = itemMeta.displayName();
        Objects.requireNonNull(itemComponent);
        return itemComponent.equals(TeleportBowUtils.getBowName());
    }

    /**
     * Checks if a player was holding a lighting crossbow when he shot an arrow.
     *
     * @param i Item in the player's main hand.
     * @return {@code true} if it is a lighting crossbow, {@code false} otherwise.
     */
    private boolean isLightingCrossbow(@NotNull ItemStack i) {
        final ItemMeta itemMeta = i.getItemMeta();
        if (itemMeta == null) return false;

        final Component itemComponent = itemMeta.displayName();
        Objects.requireNonNull(itemComponent);
        return itemComponent.equals(LightningCrossbowUtils.getLightningCrossbowName());
    }
}
