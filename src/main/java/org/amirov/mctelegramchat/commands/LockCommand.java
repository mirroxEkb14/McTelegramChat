package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.commands.performers.LockConformationGUI;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * If a player looking at a chest does {@code /lock} it should bring him an interface asking the player if he wants
 * to lock that chest.
 */
public final class LockCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int MAX_VIEW_RANGE = 5;
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (isBlockAtCertainRangeNull(player)) { return true; }
            final Block target = player.getTargetBlockExact(MAX_VIEW_RANGE);
            Objects.requireNonNull(target);
            if (isChest(target)) {
                if (LockPerformer.isChestLocked(target)) {
                    final Player chestOwnerPlayer = LockPerformer.getPlayerWhoLocked(target);
                    if (chestOwnerPlayer.equals(player))
                        notifyOwnerAboutLock(player);
                    else
                        notifyThiefAboutLock(player, chestOwnerPlayer.getName());
                } else {
                    LockConformationGUI.openLockConfirmationGUI(player);
                    McTelegramChat.getCreatedLocks().put(player, target);
                }
            }
        }
        return true;
    }

    /**
     * Sends a message to a player who tries to open this locked chest that this chest has an owner.
     *
     * @param thief Player who tries to open this chest.
     * @param ownerName Name of the chest owner.
     */
    private void notifyThiefAboutLock(@NotNull Player thief, String ownerName) {
        final TextComponent thiefWarning = Component.text(
                ChatMessage.ON_COMMAND_LOCK_THIEF_ALREADY_LOCKED.getMessage(), NamedTextColor.DARK_RED);
        final TextComponent fullMessage = thiefWarning
                .append(Component.text(ownerName, NamedTextColor.GRAY));
        thief.sendMessage(fullMessage);
    }

    /**
     * Sends a message to the chest owner that he already owns this chest, no need in the locking command again.
     *
     * @param owner Owner of this chest.
     */
    private void notifyOwnerAboutLock(@NotNull Player owner) {
        owner.sendMessage(Component.text(
                ChatMessage.ON_COMMAND_LOCK_OWNER_ALREADY_LOCKED.getMessage(), NamedTextColor.BLUE));
    }

    /**
     * Checks if the block passed as a parameter is a chest or not.
     *
     * @param block Block that is to be checked.
     *
     * @return {@code true} if this block is a type of chest, {@code false} otherwise.
     */
    private boolean isChest(@NotNull Block block) {
        return block.getType().equals(Material.CHEST);
    }

    /**
     * Checks either the block the player looking at within the distance of {@code MAX_VIEW_RANGE} is {@code null} or
     * not. In case it is {@code null}, that means this player looks at the sky, for example, but not at any block.
     *
     * @param player Player who is looking at the block.
     *
     * @return {@code true} if this block within the {@code MAX_VIEW_RANGE} range is {@code null}, {@code false}
     * otherwise.
     */
    private boolean isBlockAtCertainRangeNull(@NotNull Player player) {
        return player.getTargetBlockExact(MAX_VIEW_RANGE) == null;
    }
}
