package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.enchantments.GlowEnchantment;
import org.amirov.mctelegramchat.enchantments.HemorrhageEnchantment;
import org.amirov.mctelegramchat.tasks.BleedOutTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors hits made by a player to entities.
 * <p>
 * {@link GlowEnchantment} and {@link HemorrhageEnchantment} enchants are used.
 */
public record EntityHitListener(Plugin plugin) implements Listener {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent ENTITY_HIT = Component.text(
            "Made Your Victim Glow And bleed", NamedTextColor.RED);
//</editor-fold>

    /**
     * After a player hits an entity, this entity stars glowing and bleeding out.
     *
     * @param event Event of an entity getting damaged by another entity.
     *              
     * @see #performGlowing(Player, Entity) 
     * @see #performBleeding(Player, Entity) 
     */
    @EventHandler
    public void onEntityHit(@NotNull EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            final Entity entity = event.getEntity();
            performGlowing(player, entity);
            performBleeding(player, entity);
        }
    }

    /**
     * Performs the effect of the glowing enchantment.
     *
     * @param playerWhoHit Entity who performed the hit.
     * @param entityWhoWasHit Entity who was hit.
     */
    private void performGlowing(@NotNull Player playerWhoHit, @NotNull Entity entityWhoWasHit) {
        if (GlowEnchantment.entityHasGlowEnchantment(playerWhoHit)) {
            entityWhoWasHit.setGlowing(true);
        }
    }

    /**
     * Performs the effect of the bleeding enchantment.
     *
     * @param playerWhoHit Entity who performed the hit.
     * @param entityWhoWasHit Entity who was hit.
     */
    private void performBleeding(@NotNull Player playerWhoHit, @NotNull Entity entityWhoWasHit) {
        final ItemStack item = playerWhoHit.getInventory().getItemInMainHand();
        if (HemorrhageEnchantment.entityHasBleedingEnchantment(item)) {
            final LivingEntity victim = (LivingEntity) entityWhoWasHit;
            new BleedOutTask(victim).runTaskTimer(plugin, BleedOutTask.TASK_DELAY, BleedOutTask.TASK_PERIOD);
            playerWhoHit.sendMessage(ENTITY_HIT);
        }
    }
}
