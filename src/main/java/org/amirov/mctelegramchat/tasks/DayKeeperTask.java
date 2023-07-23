package org.amirov.mctelegramchat.tasks;

import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

/**
 * This task constantly runs to keep the day.
 * <p>
 * Runs every {@code KEEP_DAY_TASK_PERIOD}.
 */
public final class DayKeeperTask extends BukkitRunnable {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final long timeTicks = 0L;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Private Constants">
    private final Plugin plugin;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public DayKeeperTask(Plugin plugin) { this.plugin = plugin; }
//</editor-fold>

    @Override
    public void run() {
        final String lobbyWorld = plugin.getConfig().getString(ConfigProperty.LOBBY_WORLD.getKeyName());
        Objects.requireNonNull(lobbyWorld);
        final World world = Bukkit.getServer().getWorld(lobbyWorld);
        Objects.requireNonNull(world);
        world.setTime(timeTicks);
    }
}
