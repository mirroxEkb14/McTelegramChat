package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.amirov.mctelegramchat.listeners.properties.DisplayMessage;
import org.amirov.mctelegramchat.utility.TeleportBowUtils;
import org.amirov.mctelegramchat.utility.UrsineCrossbowUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Monitors the event of player's joining to the server.
 */
public record PlayerJoinListener(McTelegramChat plugin) implements Listener {

    /**
     * Called when a player joins a server.
     * <p>
     * Greets a player.
     */
    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        final Player joinedPlayer = event.getPlayer();

        final boolean wasBefore = joinedPlayer.hasPlayedBefore();
        final Title title = getTitle(wasBefore);

        teleportToSpawnPoint(joinedPlayer, wasBefore);
        giveTeleportBow(joinedPlayer);
        giveLightingCrossbow(joinedPlayer);

        joinedPlayer.showTitle(title);
    }

    /**
     * If this player has permission, then gives him a teleport bow.
     *
     * @param joinedPlayer Player who joined the server.
     */
    private void giveTeleportBow(@NotNull Player joinedPlayer) {
        if (plugin.getConfig().getBoolean(ConfigProperty.TELEPORT_BOW_GIVE_BOW.getKeyName())) {
            joinedPlayer.getInventory().addItem(TeleportBowUtils.getTeleportBow());
            joinedPlayer.getInventory().addItem(new ItemStack(Material.ARROW, TeleportBowUtils.getBowAmount()));
        }
    }

    /**
     * If this player has permission, then gives him a lighting crossbow.
     *
     * @param joinedPlayer Player who this lighting crossbow will be given to.
     */
    private void giveLightingCrossbow(@NotNull Player joinedPlayer) {
        if (plugin.getConfig().getBoolean(ConfigProperty.LIGHTNING_CROSSBOW_GIVE_CROSSBOW.getKeyName())) {
            joinedPlayer.getInventory().addItem(UrsineCrossbowUtils.getLightningCrossbow());
            joinedPlayer.getInventory().addItem(new ItemStack(Material.ARROW, UrsineCrossbowUtils.getCrossbowAmount()));
        }
    }

    /**
     * Teleports the player who joins the server to his spawn point if such has been specified before.
     *
     * @param joinedPlayer Player who joined the server.
     * @param wasBefore    {@code boolean} parameter that tells us either this player has already joined this server
     *                     or it's his first time.
     */
    private void teleportToSpawnPoint(Player joinedPlayer, boolean wasBefore) {
        if (wasBefore) {
            final Location location = getConfigSpawnLocation();
            if (location != null)
                joinedPlayer.teleport(location);
        }
    }

    /**
     * @return Location from "spawn" from the "config.yml" file if it is set, {@code null} otherwise.
     */
    @Nullable
    private Location getConfigSpawnLocation() {
        return plugin.getConfig().getLocation(ConfigProperty.SPAWN_LOCATION.getKeyName());
    }

    /**
     * Called when a player respawns.
     * <p>
     * When a player dies, respawns him at the spawn point if it is set in the "config.yml" file.
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Location location = getConfigSpawnLocation();
        if (location != null)
            event.setRespawnLocation(location);
    }

    /**
     * Forms a title to show to the player who joins the server.
     *
     * @param wasBefore {@code true} if this player has joined the server before, {@code false} otherwise.
     * @return Title for displaying on the screen.
     */
    private @NotNull
    Title getTitle(boolean wasBefore) {
        return wasBefore ?
                Title.title(
                        Component.text(DisplayMessage.ON_JOIN_PLAYED_BEFORE_WELCOME.getTitle(), NamedTextColor.GREEN),
                        Component.text(DisplayMessage.ON_JOIN_PLAYED_BEFORE_ENJOY.getTitle())) :
                Title.title(
                        Component.text(DisplayMessage.ON_JOIN_WELCOME.getTitle(), NamedTextColor.BLUE),
                        Component.text(DisplayMessage.ON_JOIN_ENJOY.getTitle()));
    }
}
