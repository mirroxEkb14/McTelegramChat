package org.amirov.mctelegramchat.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.commands.performers.LockPerformer;
import org.amirov.mctelegramchat.properties.LockCommandDBProperties;
import org.amirov.mctelegramchat.handlers.LockListHandler;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Opens gui with all the player's locked chests.
 */
public final class LockListGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 54;
    private static final TextComponent INVENTORY_TITLE = Component.text(
            "Your locks", NamedTextColor.DARK_RED);

    private static final int LOCKED_CHEST_AMOUNT = 1;
    private static final String LOCK_NAME_DOCUMENT_KEY = "type";
    private static final String LOCK_NAME_ADDITIONAL_WORD = " Lock";

    private static final String DELIMITER_REPRESENTATIVE = "---------------";
    private static final String LOCATION_REPRESENTATIVE = "Location:";
    private static final String X_COORDINATE_REPRESENTATIVE = " x: ";
    private static final String Y_COORDINATE_REPRESENTATIVE = " y: ";
    private static final String Z_COORDINATE_REPRESENTATIVE = " z: ";
    private static final String DATE_REPRESENTATIVE = "Date created: ";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Public Static Constants">
    /**
     * Holds the {@link Material} of the current block that represents a lock.
     * <p>
     * Used inside the {@link LockListHandler}.
     */
    public static Material currentLockMaterial;
//</editor-fold>

    private static Inventory lockListGUI;

    /**
     * Creates a filter by which the method find all the player's locks in the DB and uses them to display in gui.
     *
     * @param player Owner of the locks.
     */
    public static void openLockListGUI(@NotNull Player player) {
        lockListGUI = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_TITLE);

        final String playerUUID = player.getUniqueId().toString();
        final Document filter = new Document(LockCommandDBProperties.PLAYER_UUID_KEY_NAME.getKey(), playerUUID);
        McTelegramChat.getMongoCollection().find(filter).forEach(document -> {
            final ItemStack lock = getLockItem(document);
            lockListGUI.addItem(lock);
        });
        fillEmptySlots();
        player.openInventory(lockListGUI);
    }

    /**
     * Fills all the empty slots in this inventory for this conformation gui to look more filled in.
     */
    private static void fillEmptySlots() {
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (lockListGUI.getItem(i) == null)
                lockListGUI.setItem(i, new ItemStack(Material.CYAN_STAINED_GLASS_PANE));
        }
    }

    /**
     * Creates and return a lock from the DB as an {@link ItemStack} representing a chest.
     *
     * @param document Information from the DB.
     *
     * @return Chest with the information about the lock.
     */
    private static @NotNull ItemStack getLockItem(@NotNull Document document) {
        currentLockMaterial = Material.valueOf(document.getString(LOCK_NAME_DOCUMENT_KEY));
        final ItemStack lock = new ItemStack(
                currentLockMaterial,
                LOCKED_CHEST_AMOUNT);
        ItemMeta lockMeta = lock.getItemMeta();
        lockMeta.displayName(Component.text(
                document.getString(LOCK_NAME_DOCUMENT_KEY) + LOCK_NAME_ADDITIONAL_WORD, NamedTextColor.GREEN));
        final ArrayList<TextComponent> lockLore = getLockLore(document);
        lockMeta.lore(lockLore);
        lock.setItemMeta(lockMeta);
        return lock;
    }

    /**
     * Sets the information from the DB to the item lore.
     *
     * @param document Information from the DB about this lock.
     */
    public static @NotNull ArrayList<TextComponent> getLockLore(@NotNull Document document) {
        final ArrayList<TextComponent> lockLore = new ArrayList<>();
        final Document location = (Document) document.get(LockCommandDBProperties.BLOCK_LOCATION_KEY_NAME.getKey());

        lockLore.add(Component.text(DELIMITER_REPRESENTATIVE, NamedTextColor.GOLD));
        lockLore.add(Component.text(LOCATION_REPRESENTATIVE, NamedTextColor.YELLOW));
        lockLore.add(Component.text(
                        X_COORDINATE_REPRESENTATIVE, NamedTextColor.AQUA)
                .append(Component.text(location.getInteger(LockCommandDBProperties.X_COORDINATE_VALUE.getKey()))));
        lockLore.add(Component.text(
                        Y_COORDINATE_REPRESENTATIVE, NamedTextColor.AQUA)
                .append(Component.text(location.getInteger(LockCommandDBProperties.Y_COORDINATE_VALUE.getKey()))));
        lockLore.add(Component.text(
                        Z_COORDINATE_REPRESENTATIVE, NamedTextColor.AQUA)
                .append(Component.text(location.getInteger(LockCommandDBProperties.Z_COORDINATE_VALUE.getKey()))));
        lockLore.add(Component.text(DATE_REPRESENTATIVE)
                .append(Component.text(document.getDate(LockCommandDBProperties.CREATION_DATE_KEY_NAME.getKey()).toString())));
        lockLore.add(Component.text(DELIMITER_REPRESENTATIVE, NamedTextColor.GOLD));

        // add a unique lock id to be able to identify this lock later in the code
        lockLore.add(Component.text(
                document.getObjectId(LockPerformer.getLockUniqueIdName()).toString()));

        return lockLore;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getInventoryTitle() { return INVENTORY_TITLE; }

    public static Inventory getLockListGUI() { return lockListGUI; }
//</editor-fold>
}
