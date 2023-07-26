package org.amirov.mctelegramchat.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Custom event.
 * <p>
 * Whenever a spawner is broken, we handle this event with this class.
 */
public final class SpawnerBreakEvent extends Event {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final HandlerList handlers = new HandlerList();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Instance Variables">
    private final Player breaker;
    private final Block spawner;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public SpawnerBreakEvent(Player breaker, Block spawner) {
        this.breaker = breaker;
        this.spawner = spawner;
    }
//</editor-fold>

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static HandlerList getHandlerList() { return handlers; }

    public Player getBreaker() { return breaker; }

    public Block getSpawner() { return spawner; }
//</editor-fold>
}
