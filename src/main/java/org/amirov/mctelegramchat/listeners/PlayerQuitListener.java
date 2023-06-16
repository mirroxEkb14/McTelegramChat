package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.listeners.properties.DisplayMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors player's leaving the server.
 */
public final class PlayerQuitListener implements Listener {

    /**
     * Called when a player leaves a server.
     * <p>
     * Sends a message to the game chat about a player leaving a server.
     */
    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        final Player quittedPlayer = event.getPlayer();

        final Component chatMessage =  quittedPlayer.displayName().color(NamedTextColor.YELLOW)
                .append(Component.text(DisplayMessage.ON_QUIT_FAREWELL.getTitle()));
        event.quitMessage(chatMessage);
    }
}
