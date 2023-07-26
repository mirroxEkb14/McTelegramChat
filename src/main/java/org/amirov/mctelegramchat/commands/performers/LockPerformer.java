package org.amirov.mctelegramchat.commands.performers;

import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.properties.LockCommandDBProperties;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Manages everything directly related to lock management: creating, deleting, editing locks.
 */
public final class LockPerformer {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    /**
     * Used to determine either there's already a lock on the chest or not.
     */
    private static final int APPROVABLE_LOCK_AMOUNT = 1;

    private static final String LOCK_UNIQUE_ID_NAME = "_id";
//</editor-fold>

    /**
     * Creates a new lock in the database collection.
     *
     * @param player Player who triggered the command.
     * @param block Block that a player is locking.
     */
    public static void createNewLock(@NotNull Player player, @NotNull Block block) {
        final Document lock = new Document(LockCommandDBProperties.PLAYER_UUID_KEY_NAME.getKey(), player.getUniqueId().toString())
                .append(LockCommandDBProperties.BLOCK_TYPE_KEY_NAME.getKey(), LockCommandDBProperties.BLOCK_TYPE_VALUE_NAME.getKey())
                .append(LockCommandDBProperties.BLOCK_LOCATION_KEY_NAME.getKey(), getBlockLocationAsDocument(block))
                .append(LockCommandDBProperties.CREATION_DATE_KEY_NAME.getKey(), new Date())
                .append(LockCommandDBProperties.ACCESS_KEY_NAME.getKey(), new ArrayList<String>());
        McTelegramChat.getMongoCollection().insertOne(lock);

        McTelegramChat.getCreatedLocks().remove(player);
        player.closeInventory();
    }

    /**
     * Delete selected lock from the DB.
     *
     * @param chest Lock itself.
     */
    public static void deleteLock(@NotNull Block chest) {
        final Document filter = getFilter(chest);
        McTelegramChat.getMongoCollection().deleteOne(filter);
    }


    /**
     * Delete selected lock from the DB by its id.
     *
     * @param lockId Lock id.
     */
    public static void deleteLock(String lockId) {
        final Document lock = getLockById(lockId);
        McTelegramChat.getMongoCollection().deleteOne(lock);
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
        final String uuidString = uuidDocument.getString(LockCommandDBProperties.PLAYER_UUID_KEY_NAME.getKey());
        final UUID playerUUID = UUID.fromString(uuidString);

        return Bukkit.getPlayer(playerUUID);
    }

    /**
     * Finds and returns the lock by its id in the DB.
     *
     * @param id Lock id.
     *
     * @return {@link Document} representing this lock.
     */
    public static Document getLockById(String id) {
        final Document filter = new Document(new Document(LOCK_UNIQUE_ID_NAME, new ObjectId(id)));
        return McTelegramChat.getMongoCollection().find(filter).first();
    }

    /**
     * Adds a new player to the "access" section and updates the collection in the DB.
     *
     * @param lockId Id of the lock.
     * @param playerToAdd Player who should be added.
     */
    public static void addOrRemovePlayerToLock(String lockId,
                                               @NotNull Player playerToAdd,
                                               boolean toAdd) {
        final Document lock = getLockById(lockId);

        final ArrayList<String> accessList = (ArrayList<String>)
                lock.get(LockCommandDBProperties.ACCESS_KEY_NAME.getKey());

        final String uniqueIdString = playerToAdd.getUniqueId().toString();
        if (toAdd) accessList.add(uniqueIdString);
        else accessList.remove(uniqueIdString);

        final Document newDoc = new Document(LockCommandDBProperties.ACCESS_KEY_NAME.getKey(), accessList);
        final Document newDoc2 = new Document(LockCommandDBProperties.getSaveCommand(), newDoc);

        final Document filter = new Document(new Document(
                LOCK_UNIQUE_ID_NAME,
                new ObjectId(String.valueOf(lock.getObjectId(LOCK_UNIQUE_ID_NAME)))));
        McTelegramChat.getMongoCollection().updateOne(filter, newDoc2);
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
                LockCommandDBProperties.BLOCK_LOCATION_KEY_NAME.getKey(),
                new Document(LockCommandDBProperties.X_COORDINATE_VALUE.getKey(), x)
                        .append(LockCommandDBProperties.Y_COORDINATE_VALUE.getKey(), y)
                        .append(LockCommandDBProperties.Z_COORDINATE_VALUE.getKey(), z));
    }

    /**
     * Creates a new {@link Document} with the coordinates of the passed block.
     *
     * @param block Block which location will be returned.
     *
     * @return New lock with the location of the block that was passed as a parameter.
     */
    private static Document getBlockLocationAsDocument(@NotNull Block block) {
        return new Document(LockCommandDBProperties.X_COORDINATE_VALUE.getKey(), block.getX())
                .append(LockCommandDBProperties.Y_COORDINATE_VALUE.getKey(), block.getY())
                .append(LockCommandDBProperties.Z_COORDINATE_VALUE.getKey(), block.getZ());
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static String getLockUniqueIdName() { return LOCK_UNIQUE_ID_NAME; }
//</editor-fold>
}
