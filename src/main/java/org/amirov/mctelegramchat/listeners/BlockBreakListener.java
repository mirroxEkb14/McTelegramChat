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

    @EventHandler
    public void onBlockBreak(@NotNull BlockBreakEvent e) {
        final Player breaker = e.getPlayer();
        final Block brokenBlock = e.getBlock();
        if (brokenBlock.getType().equals(Material.SPAWNER) && isItemWithSilkTouch(breaker)) {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnerBreakEvent(breaker, brokenBlock));
        }
    }

    private boolean isItemWithSilkTouch(@NotNull Player player) {
        return player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH);
    }
}
