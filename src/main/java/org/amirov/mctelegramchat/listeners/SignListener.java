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

    @EventHandler
    public void onSignChanged(@NotNull SignChangeEvent event) {
        event.getBlock().setType(Material.DIAMOND_BLOCK);
    }
}
