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

/**
 * Helper class for the {@code lock} command.
 * <p>
 * Opens a conformation gui where a player decides if he really wants to lock the chest or not.
 */
public final class LockConfirmationGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Lock Chest?", NamedTextColor.DARK_AQUA);

    private static final Material YES_BUTTON_MATERIAL = Material.TOTEM_OF_UNDYING;
    private static final Material NO_BUTTON_MATERIAL = Material.BARRIER;

    private static final TextComponent YES_NAME = Component.text(
            "Yes", NamedTextColor.GREEN);
    private static final TextComponent NO_NAME = Component.text(
            "No", NamedTextColor.DARK_RED);
//</editor-fold>

    /**
     * Opens an inventory with a confirmation menu, either a player wants to lock the chest or not.
     *
     * @param player Player who triggered the event.
     */
    public static void openLockConfirmationGUI(@NotNull Player player) {
        Inventory lockConfirmationGUI = Bukkit.createInventory(
                player,
                ConfirmationGUIConstants.INVENTORY_SIZE.getValue(),
                INVENTORY_NAME);
        final ItemStack yes = getYesButton();
        final ItemStack no = getNoButton();
        lockConfirmationGUI.setItem(ConfirmationGUIConstants.YES_ITEM_SLOT_INDEX.getValue(), yes);
        lockConfirmationGUI.setItem(ConfirmationGUIConstants.NO_ITEM_SLOT_INDEX.getValue(), no);

        ConfirmationGUIConstants.fillEmptySlots(lockConfirmationGUI);

        player.openInventory(lockConfirmationGUI);
    }

    /**
     * Works as a getter.
     * <p>
     * Creates and returns an item that represents the 'no' answer.
     *
     * @return Item representing player's confirmation.
     */
    private static @NotNull ItemStack getNoButton() {
        final ItemStack no = new ItemStack(NO_BUTTON_MATERIAL, ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta noMeta = no.getItemMeta();
        noMeta.displayName(NO_NAME);
        no.setItemMeta(noMeta);
        return no;
    }

    /**
     * Works as a getter.
     * <p>
     * Creates and returns an item that represents the 'yes' answer.
     *
     * @return Item representing player's confirmation.
     */
    private static @NotNull ItemStack getYesButton() {
        final ItemStack yes = new ItemStack(YES_BUTTON_MATERIAL, ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.displayName(YES_NAME);
        yes.setItemMeta(yesMeta);
        return yes;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getLockConfirmationName() { return INVENTORY_NAME; }

    public static Material getYesButtonMaterial() { return YES_BUTTON_MATERIAL; }

    public static Material getNoButtonMaterial() { return NO_BUTTON_MATERIAL; }
//</editor-fold>
}
