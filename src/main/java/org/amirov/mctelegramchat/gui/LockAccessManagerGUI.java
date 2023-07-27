package org.amirov.mctelegramchat.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.gui.enums.ConfirmationGUIConstants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Opens a menu where the player can allow access to another player to his lock chest, remove a player or view him.
 */
public final class LockAccessManagerGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 45;
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Access Manager", NamedTextColor.RED);

    private static final Material REMOVE_BUTTON_MATERIAL = Material.REDSTONE_BLOCK;
    private static final Material VIEW_BUTTON_MATERIAL = Material.PLAYER_HEAD;
    private static final Material ADD_BUTTON_MATERIAL = Material.ENDER_EYE;

    private static final TextComponent REMOVE_NAME = Component.text(
            "Remove Player", NamedTextColor.DARK_RED);
    private static final TextComponent VIEW_NAME = Component.text(
            "View Players", NamedTextColor.AQUA);
    private static final TextComponent ADD_NAME = Component.text(
            "Add Player", NamedTextColor.GOLD);

    private static final ArrayList<TextComponent> removeLore = new ArrayList<>();
    private static final ArrayList<TextComponent> viewLore = new ArrayList<>();

    private static final int REMOVE_BUTTON_INDEX = 13;
    private static final int VIEW_BUTTON_INDEX = 22;
    private static final int ADD_BUTTON_INDEX = 31;
    private static final int CLOSE_BUTTON_INDEX = 44;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static Initialization Blocks">
    static {
        removeLore.add(Component.text(
                "Remove Players From The Lock", NamedTextColor.YELLOW));
        viewLore.add(Component.text(
            "See Who Has Access To The Lock", NamedTextColor.GREEN));
    }
//</editor-fold>

    /**
     * Creates and opens an inventory of the "lock access manager" gui.
     *
     * @param player Player for whom the inventory will be opened.
     */
    public static void openLockAccessManagerGUI(@NotNull Player player) {
        final Inventory lockAccessManagerGUI = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

        final ItemStack removeBtn = getRemovePlayerBtn();
        final ItemStack viewBtn = getViewPlayerBtn();
        final ItemStack addBtn = getAddPlayerBtn();
        final ItemStack closeBtn = ConfirmationGUIConstants.getCloseButton();

        lockAccessManagerGUI.setItem(REMOVE_BUTTON_INDEX, removeBtn);
        lockAccessManagerGUI.setItem(VIEW_BUTTON_INDEX, viewBtn);
        lockAccessManagerGUI.setItem(ADD_BUTTON_INDEX, addBtn);
        lockAccessManagerGUI.setItem(CLOSE_BUTTON_INDEX, closeBtn);

        ConfirmationGUIConstants.fillEmptySlots(lockAccessManagerGUI);
        player.openInventory(lockAccessManagerGUI);
    }

    /**
     * @return {@link ItemStack} object representing an "add" button.
     */
    private static @NotNull ItemStack getAddPlayerBtn() {
        final ItemStack addBtn = new ItemStack(
                ADD_BUTTON_MATERIAL,
                ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta btnMeta = addBtn.getItemMeta();
        btnMeta.displayName(ADD_NAME);
        addBtn.setItemMeta(btnMeta);
        return addBtn;
    }

    /**
     * @return {@link ItemStack} object representing a "view" button.
     */
    private static @NotNull ItemStack getViewPlayerBtn() {
        final ItemStack viewBtn = new ItemStack(
                VIEW_BUTTON_MATERIAL,
                ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta btnMeta = viewBtn.getItemMeta();
        btnMeta.displayName(VIEW_NAME);
        btnMeta.lore(viewLore);
        viewBtn.setItemMeta(btnMeta);
        return viewBtn;
    }

    /**
     * @return {@link ItemStack} object representing a "remove" button.
     */
    private static @NotNull ItemStack getRemovePlayerBtn() {
        final ItemStack removeBtn = new ItemStack(
                REMOVE_BUTTON_MATERIAL,
                ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta btnMeta = removeBtn.getItemMeta();
        btnMeta.displayName(REMOVE_NAME);
        btnMeta.lore(removeLore);
        removeBtn.setItemMeta(btnMeta);
        return removeBtn;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getLockAccessManagerName() { return INVENTORY_NAME; }

    public static Material getRemoveButtonMaterial() { return REMOVE_BUTTON_MATERIAL; }

    public static Material getViewButtonMaterial() { return VIEW_BUTTON_MATERIAL; }

    public static Material getAddButtonMaterial() { return ADD_BUTTON_MATERIAL; }
//</editor-fold>
}
