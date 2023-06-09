package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.amirov.mctelegramchat.properties.DisplayMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors the event of player's joining to the server.
 */
public final class PlayerJoinListener implements Listener {

    /**
     * Called when a player joins a server.
     * <p>
     * Greets a player.
     */
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        final Player joinedPlayer = event.getPlayer();

        final Title title = joinedPlayer.hasPlayedBefore() ?
                Title.title(
                        Component.text(DisplayMessage.ON_JOIN_PLAYED_BEFORE_WELCOME.getTitle(), NamedTextColor.GREEN),
                        Component.text(DisplayMessage.ON_JOIN_PLAYED_BEFORE_ENJOY.getTitle())) :
                Title.title(
                        Component.text(DisplayMessage.ON_JOIN_WELCOME.getTitle(), NamedTextColor.BLUE),
                        Component.text(DisplayMessage.ON_JOIN_ENJOY.getTitle()));
        joinedPlayer.showTitle(title);
    }
}
