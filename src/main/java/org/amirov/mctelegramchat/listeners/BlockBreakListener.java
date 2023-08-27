package org.amirov.mctelegramchat.listeners;

import org.amirov.mctelegramchat.events.SpawnerBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors the event of breaking a block.
 */
public final class BlockBreakListener implements Listener {

    /**
     * If the player broke a spawner with an item that has a silk touch enchant, it's broken, and he gets this spawner
     * to his inventory.
     *
     * @param event Event of a player breaking a block.
     *
     * @see #isItemWithSilkTouch(Player)
     */
    @EventHandler
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        final Player breaker = event.getPlayer();
        final Block brokenBlock = event.getBlock();
        if (brokenBlock.getType().equals(Material.SPAWNER) && isItemWithSilkTouch(breaker)) {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnerBreakEvent(breaker, brokenBlock));
        }
    }

    /**
     * Checks if the item this player is now holding in the hand has a silk touch enchant or not.
     *
     * @param player Player who triggered the event.
     *
     * @return {@code true} if the passed player hold in his main hand an item with a silk touch enchant.
     */
    private boolean isItemWithSilkTouch(@NotNull Player player) {
        return player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH);
    }
}
