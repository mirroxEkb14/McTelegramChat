package org.amirov.mctelegramchat.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Is triggered when a player interacts with a chest.
 */
public final class ChestListener implements Listener {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent LOCK_DELETED = Component.text(
            "Lock Been Deleted", NamedTextColor.GREEN);
    private static final TextComponent TRIED_TO_BREAK_THIEF = Component.text(
            "Cannot be Broken Due to Locking", NamedTextColor.RED);
    private static final TextComponent TRIED_TO_OPEN_THIEF = Component.text(
            "Chest Locked by ", NamedTextColor.RED);
    private static final TextComponent TRIED_TO_OPEN_OWNER = Component.text(
            "You Own Chest", NamedTextColor.WHITE);
//</editor-fold>

    /**
     * Checks either a player tried to open a chest that is already locked or not.
     *
     * @param event Event of a player interacting with some block.
     *
     * @see #playerRightClicked(PlayerInteractEvent)
     * @see #isBlockChest(Block)
     * @see #sendMessageToOwner(Player)
     * @see #sendMessageToThief(Player, String)
     */
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
                        sendMessageToOwner(triedToOpenPlayer);
                    } else {
                        event.setCancelled(true);
                        sendMessageToThief(triedToOpenPlayer, chestOwnerPlayer.getName());
                    }
                }
            }
        }
    }

    /**
     * Checks if the chest was broken by its owner, then the lock on this chest should be deleted from the DB; it
     * not an owner of this chest tried to break it, then he's not allowed to do such thing.
     *
     * @param event Event of breaking a block.
     *
     * @see #isBlockChest(Block)
     */
    @EventHandler
    public void onChestBreak(@NotNull BlockBreakEvent event) {
        final Block target = event.getBlock();
        if (isBlockChest(target) && LockPerformer.isChestLocked(target)) {
            final Player chestOwnerPlayer = LockPerformer.getPlayerWhoLocked(target);
            final Player triedToOpenPlayer = event.getPlayer();
            if (chestOwnerPlayer.equals(triedToOpenPlayer)) {
                LockPerformer.deleteLock(target);
                chestOwnerPlayer.sendMessage(LOCK_DELETED);
            } else {
                event.setCancelled(true);
                triedToOpenPlayer.sendMessage(TRIED_TO_BREAK_THIEF);
            }
        }
    }

    /**
     * Sends a message to a player tried to access the chest, although it's already locked by another one.
     *
     * @param thief Player who tried to open the chest.
     * @param ownerName Name of the owner of this chest.
     */
    private void sendMessageToThief(@NotNull Player thief, String ownerName) {
        final TextComponent fullMessage = TRIED_TO_OPEN_THIEF
                .append(Component.text(ownerName, NamedTextColor.GRAY));
        thief.sendMessage(fullMessage);
    }

    /**
     * Sends a message to a player who opens the chest (owner in this case) that he possesses this chest.
     *
     * @param owner Owner of this chest.
     */
    private void sendMessageToOwner(@NotNull Player owner) {
        owner.sendMessage(TRIED_TO_OPEN_OWNER);
    }

    /**
     * Determines either the passed block is a type of chest or not.
     *
     * @param block Block that was clicked on.
     *
     * @return {@code true} if this block is a chest, {@code false} otherwise.
     */
    private boolean isBlockChest(@NotNull Block block) {
        return block.getType().equals(Material.CHEST);
    }

    /**
     * Determines either the player right-clicked or not.
     *
     * @param event Event that a player triggered by interacting with something.
     *
     * @return {@code true} if this is an event of a right-click, {@code false} otherwise.
     */
    private boolean playerRightClicked(@NotNull PlayerInteractEvent event) {
        return event.getAction().equals(Action.RIGHT_CLICK_BLOCK);
    }
}
