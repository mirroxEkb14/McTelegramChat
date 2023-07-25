package org.amirov.mctelegramchat.commands.performers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.logging.Loggers;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Opens gui after a player clicked on some of his locks (chests) inside the lock list menu.
 */
public final class LockManagerGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 9;
    private static final TextComponent INVENTORY_TITLE = Component.text(
            "Lock Manager", NamedTextColor.DARK_AQUA);

    private static final Material MANAGE_ACCESS_BUTTON_MATERIAL = Material.ARMOR_STAND;
    private static final Material DELETE_LOCK_BUTTON_MATERIAL = Material.WITHER_ROSE;
    private static final Material INFO_BUTTON_MATERIAL = Material.BOOK;
    private static final Material CLOSE_BUTTON_MATERIAL = Material.BARRIER;

    private static final TextComponent MANAGE_ACCESS_NAME = Component.text(
            "Access Manager", NamedTextColor.YELLOW);
    private static final TextComponent DELETE_LOCK_NAME = Component.text(
            "Delete Lock", NamedTextColor.DARK_RED);
    private static final TextComponent INFO_LOCK_NAME = Component.text(
            "Lock Information", NamedTextColor.GREEN);
    private static final TextComponent CLOSE_LOCK_NAME = Component.text(
            "Close", NamedTextColor.DARK_RED);

    private static final ArrayList<TextComponent> manageAccessLore = new ArrayList<>();
    private static final ArrayList<TextComponent> deleteLockLore = new ArrayList<>();
    private static final ArrayList<TextComponent> closeLore = new ArrayList<>();

    private static final int BUTTON_AMOUNT = 1;

    private static final int MANAGE_ACCESS_BUTTON_INDEX = 0;
    private static final int DELETE_BUTTON_INDEX = 1;
    private static final int INFO_BUTTON_INDEX = 7;
    private static final int CLOSE_BUTTON_INDEX = 8;

    /**
     * Represents an index where the lock id is locked in the item lore.
     */
    private static final int LOCK_ID_INDEX = 7;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    static {
        manageAccessLore.add(Component.text(
                "Manage who has access to this Lock", NamedTextColor.GREEN));

        deleteLockLore.add(Component.text(
                "Deleting this lock will ", NamedTextColor.GREEN));
        deleteLockLore.add(Component.text(
                "make your chest totally unprotected", NamedTextColor.GREEN));

        closeLore.add(Component.text(
                "Go back to the Lock List", NamedTextColor.GREEN));
    }
//</editor-fold>

    private static Inventory lockManagerGUI;

    /**
     * Creates an inventory with the items representing actions that can be made with the locks.
     *
     * @param player Player for whom the gui will be opened.
     * @param document Document with the lock info containing the unique id of this lock in the DB.
     */
    public static void openLockManagerGUI(@NotNull Player player, @NotNull Document document) {
        lockManagerGUI = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_TITLE);

        final ItemStack manageAccessBtn = getManageAccessButton();
        final ItemStack deleteBtn = getDeleteButton();
        final ItemStack infoBtn = getInfoButton(document);
        final ItemStack closeBtn = getCloseButton();

        lockManagerGUI.setItem(MANAGE_ACCESS_BUTTON_INDEX, manageAccessBtn);
        lockManagerGUI.setItem(DELETE_BUTTON_INDEX, deleteBtn);
        lockManagerGUI.setItem(INFO_BUTTON_INDEX, infoBtn);
        lockManagerGUI.setItem(CLOSE_BUTTON_INDEX, closeBtn);

        player.openInventory(lockManagerGUI);
    }

    /**
     * Getter.
     *
     * @return {@link ItemStack} object of a "manage access" button.
     */
    private static @NotNull ItemStack getManageAccessButton() {
        final ItemStack manageAccessButton = new ItemStack(MANAGE_ACCESS_BUTTON_MATERIAL, BUTTON_AMOUNT);
        final ItemMeta btnMeta = manageAccessButton.getItemMeta();
        btnMeta.displayName(MANAGE_ACCESS_NAME);
        btnMeta.lore(manageAccessLore);
        manageAccessButton.setItemMeta(btnMeta);
        return manageAccessButton;
    }

    /**
     * Getter.
     *
     * @return {@link ItemStack} object of a "delete" button.
     */
    private static @NotNull ItemStack getDeleteButton() {
        final ItemStack deleteButton = new ItemStack(DELETE_LOCK_BUTTON_MATERIAL, BUTTON_AMOUNT);
        final ItemMeta btnMeta = deleteButton.getItemMeta();
        btnMeta.displayName(DELETE_LOCK_NAME);
        btnMeta.lore(deleteLockLore);
        deleteButton.setItemMeta(btnMeta);
        return deleteButton;
    }


    /**
     * Getter.
     *
     * @param document {@link Document} representing a lock with the necessary lore item.
     *
     * @return {@link ItemStack} object of an "info" button.
     */
    private static @NotNull ItemStack getInfoButton(@NotNull Document document) {
        final ItemStack infoButton = new ItemStack(INFO_BUTTON_MATERIAL, BUTTON_AMOUNT);
        final ItemMeta btnMeta = infoButton.getItemMeta();
        btnMeta.displayName(INFO_LOCK_NAME);

        final ArrayList<TextComponent> infoLockLore = LockListGUI.getLockLore(document);
        btnMeta.lore(infoLockLore);

        infoButton.setItemMeta(btnMeta);
        return infoButton;
    }

    /**
     * Getter.
     *
     * @return {@link ItemStack} object of a "close" button.
     */
    private static @NotNull ItemStack getCloseButton() {
        final ItemStack closeButton = new ItemStack(CLOSE_BUTTON_MATERIAL, BUTTON_AMOUNT);
        final ItemMeta btnMeta = closeButton.getItemMeta();
        btnMeta.displayName(CLOSE_LOCK_NAME);
        btnMeta.lore(closeLore);
        closeButton.setItemMeta(btnMeta);
        return closeButton;
    }

    /**
     * Gets the id of the lock.
     *
     * @param itemSlotId Slot id of the item that was clicked on.
     *
     * @return Id of the lock from the DB.
     */
    public static @NotNull String getLockId(int itemSlotId) {
        final ItemStack item = LockListGUI.getLockListGUI().getItem(itemSlotId);
        Objects.requireNonNull(item);
        final List<Component> lore = item.getItemMeta().lore();
        Objects.requireNonNull(lore);
        final String textComponentImpl = lore.get(LOCK_ID_INDEX).toString();
        return getExtractedId(textComponentImpl);
    }

    /**
     * In this project for a colored item lore {@link TextComponent} is used. Here it is needed to extract id that is
     * a part of this {@link TextComponent} object. When the {@code getLockId} method extracts the string with this id
     * from the lore of the object, it is a {@link TextComponent} object, then it is converted to a string. Now, as a
     * string, it has the following likeness:
     * <p>
     * TextComponentImpl{content="64bf909298473f3929bd971e",
     * style=StyleImpl{obfuscated=not_set, bold=not_set, strikethrough=not_set,
     * underlined=not_set, italic=not_set, color=null, clickEvent=null,
     * hoverEvent=null, insertion=null, font=null}, children=[]}
     * <p>
     * This method extracts the necessary 24-digit id from the string using regular expressions and returns it.
     *
     * @param textComponentImpl {@link TextComponent}-like string with the lock id.
     *
     * @return Id as a {@link String}.
     */
    @Contract(pure = true)
    private static @NotNull String getExtractedId(String textComponentImpl) {
        final Pattern pattern = Pattern.compile("[A-aZ-z0-9]{24}");
        Matcher matcher = pattern.matcher(textComponentImpl);
        return matcher.group();
    }
}
