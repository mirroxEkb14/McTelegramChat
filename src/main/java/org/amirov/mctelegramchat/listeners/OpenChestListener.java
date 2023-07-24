package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Is triggered when a player opens a chest. Is needed to check either a player tried to open a chest that is locked
 * or not.
 */
public final class OpenChestListener implements Listener {

    @EventHandler
    public void onChestOpen(@NotNull PlayerInteractEvent event) {
        if (playerRightClicked(event)) {
            final Block target = event.getClickedBlock();
            Objects.requireNonNull(target);
            if (isBlockChest(target)) {
                if (LockPerformer.isChestLocked(target)) {
                    final Player chestOwnerPlayer = LockPerformer.getPlayerWhoLocked(target);
                    final Player triedToOpenPlayer = event.getPlayer();
                    if (chestOwnerPlayer == triedToOpenPlayer) {
                        triedToOpenPlayer.sendMessage(Component.text(
                                ChatMessage.TRIED_TO_OPEN_LOCKED_CHEST_OWNER.getMessage(), NamedTextColor.BLUE));
                    } else {
                        event.setCancelled(true);
                        final TextComponent thiefWarning = Component.text(
                                ChatMessage.TRIED_TO_OPEN_LOCKED_CHEST_THIEF.getMessage(), NamedTextColor.DARK_RED);
                        final TextComponent fullMessage = thiefWarning
                                .append(Component.text(chestOwnerPlayer.getName(), NamedTextColor.GRAY));
                        triedToOpenPlayer.sendMessage(fullMessage);
                    }
                }
            }
        }
    }


    /**
     * Determines either the passed block is a type of chest or not.
     *
     * @param b Block that was clicked on.
     *
     * @return {@code true} if this block is a chest, {@code false} otherwise.
     */
    private boolean isBlockChest(@NotNull Block b) {
        return b.getType().equals(Material.CHEST);
    }

    /**
     * Determines either the player right-clicked or not.
     *
     * @param e Event that a player triggered by interacting with something.
     *
     * @return {@code true} if this is an event of a right-click, {@code false} otherwise.
     */
    private boolean playerRightClicked(@NotNull PlayerInteractEvent e) {
        return e.getAction().equals(Action.RIGHT_CLICK_BLOCK);
    }
}
