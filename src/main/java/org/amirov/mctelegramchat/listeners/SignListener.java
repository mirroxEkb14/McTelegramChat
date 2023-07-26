package org.amirov.mctelegramchat.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors the changes of text of a sign. When a player enters some text, a diamond block is spawn instead of a sign.
 */
public final class SignListener implements Listener {

    /**
     * Spawns a diamond block instead of a sign.
     *
     * @param event Event of a sign being changed by a player.
     */
    @EventHandler
    public void onSignChanged(@NotNull SignChangeEvent event) {
        event.getBlock().setType(Material.DIAMOND_BLOCK);
    }
}
