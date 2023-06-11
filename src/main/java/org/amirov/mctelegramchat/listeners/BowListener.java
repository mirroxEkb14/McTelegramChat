package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.amirov.mctelegramchat.utility.BowUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Monitors the events of an arrow landing.
 */
public record BowListener(McTelegramChat plugin) implements Listener {

    private static final float soundVolume = 1.0f;
    private static final float soundPitch = 1.0f;

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        if (isArrow(event) && showByPlayer(event)) {
            final Player player = (Player) event.getEntity().getShooter();
            Objects.requireNonNull(player);
            final ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (isTeleportBow(itemInMainHand))
                teleportToArrowLocation(event, player);
        }
    }

    /**
     * Teleport a player to the arrow landing location, removes the arrow itself, sends a message to this player and
     * plays a sound at the location where a player was teleported to.
     *
     * @param e Event of an arrow landing.
     * @param p Player who show this arrow.
     */
    private void teleportToArrowLocation(@NotNull ProjectileHitEvent e, @NotNull Player p) {
        final Location location = e.getEntity().getLocation();
        p.teleport(location);
        e.getEntity().remove();
        p.sendMessage(Component.text(ChatMessage.ON_ARROW_LANDING.getMessage()));
        p.playSound(p, Sound.ENTITY_ARROW_HIT, soundVolume, soundPitch);
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
    private boolean showByPlayer(@NotNull ProjectileHitEvent e) {
        return e.getEntity().getShooter() instanceof Player;
    }

    /**
     * Checks if a player was holding a teleport bow when he shot an arrow.
     *
     * @param i Item in the player's main hand.
     * @return {@code true} if it is a teleport bow, {@code false} otherwise.
     */
    private boolean isTeleportBow(@NotNull ItemStack i) {
        final Component itemComponent = i.getItemMeta().displayName();
        Objects.requireNonNull(itemComponent);
        return itemComponent.equals(BowUtils.getBowName());
    }
}
