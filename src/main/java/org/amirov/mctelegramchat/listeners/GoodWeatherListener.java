package org.amirov.mctelegramchat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Monitors the weather.
 * <p>
 * No rain.
 */
public final class GoodWeatherListener implements Listener {

    /**
     * Weather doesn't ever change.
     *
     * @param event Event of the weather changing in the world.
     */
    @EventHandler
    public void onWeatherChange(@NotNull WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
