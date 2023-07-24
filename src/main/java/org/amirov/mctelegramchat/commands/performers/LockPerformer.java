package org.amirov.mctelegramchat.commands.performers;

import org.amirov.mctelegramchat.McTelegramChat;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Manages everything directly related to lock management: creating, deleting, editing locks.
 */
public final class LockPerformer {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String PLAYER_UUID_KEY_NAME = "uuid";
    private static final String BLOCK_TYPE_KEY_NAME = "type";
    private static final String BLOCK_TYPE_VALUE_NAME = "chest";
    private static final String BLOCK_LOCATION_KEY_NAME = "location";
    private static final String CREATION_DATE_KEY_NAME = "creation-date";

    private static final String X_COORDINATE_VALUE = "x";
    private static final String Y_COORDINATE_VALUE = "y";
    private static final String Z_COORDINATE_VALUE = "z";

    /**
     * Used to determine either there's already a lock on the chest or not.
     */
    private static final int APPROVABLE_LOCK_AMOUNT = 1;
//</editor-fold>

    /**
     * Creates a new lock in the database collection.
     *
     * @param player Player who triggered the command.
     * @param block Block that a player is locking.
     */
    public static void createNewLock(@NotNull Player player, @NotNull Block block) {
        final Document lock = new Document(PLAYER_UUID_KEY_NAME, player.getUniqueId().toString())
                .append(BLOCK_TYPE_KEY_NAME, BLOCK_TYPE_VALUE_NAME)
                .append(BLOCK_LOCATION_KEY_NAME, getBlockLocationAsDocument(block))
                .append(CREATION_DATE_KEY_NAME, new Date());
        McTelegramChat.getMongoCollection().insertOne(lock);

        McTelegramChat.getCreatedLocks().remove(player);
        player.closeInventory();
    }

    /**
     * Determines a player by whom this chest was locked and returns him.
     *
     * @param chest Chest another player interacted with.
     *
     * @return Instance of the player who locked the chest another player tried to open.
     */
    public static Player getPlayerWhoLocked(@NotNull Block chest) {
        final Document filter = getFilter(chest);

        final Document uuidDocument = McTelegramChat.getMongoCollection().find(filter).first();
        Objects.requireNonNull(uuidDocument);
        final String uuidString = uuidDocument.getString(PLAYER_UUID_KEY_NAME);
        final UUID playerUUID = UUID.fromString(uuidString);

        return Bukkit.getPlayer(playerUUID);
    }

    /**
     * Determines either the passed chest is locked by someone or not.
     *
     * @param chest Chest a player tried to open.
     *
     * @return {@code true} if the chest passed as a parameter is already locked by someone, {@code false} otherwise.
     */
    public static boolean isChestLocked(@NotNull Block chest) {
        final Document filter = getFilter(chest);
        return McTelegramChat.getMongoCollection().countDocuments(filter) == APPROVABLE_LOCK_AMOUNT;
    }

    /**
     * Creates and returns a filter represented as a {@link Document} with the chest location.
     *
     * @param chest Chest itself.
     *
     * @return New {@link Document} with the chest coordinates.
     */
    private static @NotNull Document getFilter(@NotNull Block chest) {
        final int x = chest.getX();
        final int y = chest.getY();
        final int z = chest.getZ();
        return new Document(
                BLOCK_LOCATION_KEY_NAME,
                new Document(X_COORDINATE_VALUE, x).append(Y_COORDINATE_VALUE, y).append(Z_COORDINATE_VALUE, z));
    }

    /**
     * Creates a new {@link Document} with the coordinates of the passed block.
     *
     * @param block Block which location will be returned.
     *
     * @return New lock with the location of the block that was passed as a parameter.
     */
    private static Document getBlockLocationAsDocument(@NotNull Block block) {
        return new Document(X_COORDINATE_VALUE, block.getX())
                .append(Y_COORDINATE_VALUE, block.getY())
                .append(Z_COORDINATE_VALUE, block.getZ());
    }
}
