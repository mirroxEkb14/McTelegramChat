package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.amirov.mctelegramchat.listeners.properties.DisplayMessage;
import org.amirov.mctelegramchat.properties.Symbol;
import org.amirov.mctelegramchat.utility.GlowingChestplate;
import org.amirov.mctelegramchat.utility.HemorrhageSword;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Monitors the event of player's joining to the server.
 */
public record PlayerJoinListener(McTelegramChat plugin) implements Listener {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int AMPERSAND_INDEX = 0;
//</editor-fold>

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

        updateInvisibleList(joinedPlayer);
        teleportToSpawnPoint(joinedPlayer, wasBefore);
        sendMotdMessage(joinedPlayer);
        equipPlayerWithGlowingChestplate(joinedPlayer);
        equipPlayerWithHemorrhageSword(joinedPlayer);

        joinedPlayer.showTitle(title);
    }


    /**
     * Equips the joined player with a sword with a custon enchantment of hemorrhage.
     *
     * @param joinedPlayer Player who's just joined the game.
     */
    private void equipPlayerWithHemorrhageSword(@NotNull Player joinedPlayer) {
        final ItemStack hemorrhageSword = HemorrhageSword.getHemorrhageSword();
        joinedPlayer.getEquipment().setItemInMainHand(hemorrhageSword);
    }

    /**
     * Equips the joined player with a chestplate with a custon enchantment of glowing.
     *
     * @param joinedPlayer Player who's just joined the game.
     */
    private void equipPlayerWithGlowingChestplate(@NotNull Player joinedPlayer) {
        final ItemStack glowingChestplate = GlowingChestplate.getGlowingChestplate();
        joinedPlayer.getEquipment().setChestplate(glowingChestplate);
    }

    /**
     * Sends a greeting message when a player joins the server.
     *
     * @param player Player who joined the server.
     */
    private void sendMotdMessage(@NotNull Player player) {
        final boolean motdEnabled = plugin.getConfig().getBoolean(ConfigProperty.MOTD_ENABLED.getKeyName());
        if (motdEnabled) {
            final List<?> motdMessage = plugin.getConfig().getList(ConfigProperty.MOTD_MESSAGE.getKeyName());
            Objects.requireNonNull(motdMessage);
            motdMessage.forEach(m -> {
                final String coloredMessage = ChatColor.translateAlternateColorCodes(
                        Symbol.AMPERSAND.getSymbol().charAt(AMPERSAND_INDEX), m.toString());
                player.sendMessage(coloredMessage);
            });
        }
    }

    /**
     * Each time a player joins the server, this method loops through every single player in the list and makes them
     * invisible to the player joined.
     *
     * @param joinedPlayer Player who joined the server.
     */
    private void updateInvisibleList(@NotNull Player joinedPlayer) {
        for (Player vanishedPlayer : plugin.getInvisibleList()) {
            joinedPlayer.hidePlayer(plugin, vanishedPlayer);
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
     *
     * @param event Event of a player respawning.
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
