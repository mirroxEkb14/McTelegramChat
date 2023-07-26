package org.amirov.mctelegramchat.listeners;

import org.amirov.mctelegramchat.events.SpawnerBreakEvent;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Triggers when a spawner was broken, then is given to the breaker.
 */
public final class SpawnerBreakListener implements Listener {

    /**
     * This methods checks what type of entity is inside the broken spawner, then creates a new {@code ItemStack} for
     * a new spawner that is going to be given to a breaker, after we change item metadata and the block state to
     * equal entity type of the entity that was just broken.
     *
     * @param e Custom spawner break event.
     */
    @EventHandler
    public void onSpawnerBreak(@NotNull SpawnerBreakEvent e) {
        final CreatureSpawner csBrokenSpawner = (CreatureSpawner) e.getSpawner().getState();

        final ItemStack spawnerToGive = new ItemStack(Material.SPAWNER);
        final BlockStateMeta metaToGive = (BlockStateMeta) spawnerToGive.getItemMeta();
        final CreatureSpawner csSpawnerToGive = (CreatureSpawner) metaToGive.getBlockState();

        csSpawnerToGive.setSpawnedType(csBrokenSpawner.getSpawnedType());
        metaToGive.setBlockState(csSpawnerToGive);
        spawnerToGive.setItemMeta(metaToGive);

        e.getBreaker().getInventory().addItem(spawnerToGive);
    }
}
