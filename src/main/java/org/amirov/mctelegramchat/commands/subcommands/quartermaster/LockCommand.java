package org.amirov.mctelegramchat.commands.subcommands.quartermaster;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.gui.LockConfirmationGUI;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * If a player looking at a chest does {@code /lock} it should bring him an interface asking the player if he wants
 * to lock that chest.
 */
public final class LockCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String LOCK_COMMAND_NAME = "lock";
    private static final String LOCK_COMMAND_DESC = "Locks a Chest";
    private static final String LOCK_COMMAND_SYNTAX = "/qm lock";

    private static final int MAX_VIEW_RANGE = 5;
//</editor-fold>

    @Override
    public @NotNull String getName() { return LOCK_COMMAND_NAME; }

    @Override
    public @NotNull String getDescription() { return LOCK_COMMAND_DESC; }

    @Override
    public @NotNull String getSyntax() { return LOCK_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String[] args) {
        if (isBlockAtCertainRangeNull(performer)) {
            performer.sendMessage(Component.text(
                    ChatMessage.ON_COMMAND_LOCK_WRONG_DISTANCE.getMessage(), NamedTextColor.GRAY));
            return;
        }
        final Block target = performer.getTargetBlockExact(MAX_VIEW_RANGE);
        Objects.requireNonNull(target);

        final List<String> lockableBlocks =
                McTelegramChat.getPlugin().getConfig().getStringList(ConfigProperty.LOCKABLE_BLOCKS.getKeyName());
        for (String lockableBlock : lockableBlocks) {
            if (isLockableBlock(target, lockableBlock)) {
                if (LockPerformer.isChestLocked(target)) {
                    final Player chestOwnerPlayer = LockPerformer.getPlayerWhoLocked(target);
                    if (chestOwnerPlayer.equals(performer))
                        notifyOwnerAboutLock(performer);
                    else
                        notifyThiefAboutLock(performer, chestOwnerPlayer.getName());
                } else {
                    LockConfirmationGUI.openLockConfirmationGUI(performer);
                    McTelegramChat.getCreatedLocks().put(performer, target);
                }
            } else {
                performer.sendMessage(Component.text(
                        ChatMessage.ON_COMMAND_LOCK_WRONG_BLOCK.getMessage(), NamedTextColor.GRAY));
            }
        }
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
     * Checks if the block passed as a parameter is a block from the "lockable-blocks" list (chest).
     *
     * @param targetBlock Block that is to be checked.
     * @param lockableBlock Block from the list of "lockable-blocks".
     *
     * @return {@code true} if this block is a type of chest, {@code false} otherwise.
     */
    private boolean isLockableBlock(@NotNull Block targetBlock, String lockableBlock) {
        return targetBlock.getType().equals(Material.valueOf(lockableBlock));
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
