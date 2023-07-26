package org.amirov.mctelegramchat.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Opens an inventory where a player confirms either he really wants to delete selected lock or not.
 */
public final class LockDeleteConfirmationGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Confirm: Delete Lock?", NamedTextColor.RED);

    private static final Material YES_BUTTON_MATERIAL = Material.EMERALD;
    private static final Material NO_BUTTON_MATERIAL = Material.BARRIER;

    private static final TextComponent YES_NAME = Component.text(
            "Yes", NamedTextColor.GREEN);
    private static final TextComponent NO_NAME = Component.text(
            "No", NamedTextColor.DARK_RED);
//</editor-fold>

    /**
     * Opens a menu where a player confirms, either he deletes this selected lock or not.
     *
     * @param player Player who opened the menu.
     */
    public static void openLockDeleteConfirmationGUI(@NotNull Player player) {
        final Inventory lockDeleteConfirmationGUI = Bukkit.createInventory(
                player,
                ConfirmationGUIConstants.INVENTORY_SIZE.getValue(),
                INVENTORY_NAME);

        final ItemStack yesBtn = getYesButton();
        final ItemStack noBtn = getNoButton();

        lockDeleteConfirmationGUI.setItem(ConfirmationGUIConstants.YES_ITEM_SLOT_INDEX.getValue(), yesBtn);
        lockDeleteConfirmationGUI.setItem(ConfirmationGUIConstants.NO_ITEM_SLOT_INDEX.getValue(), noBtn);

        ConfirmationGUIConstants.fillEmptySlots(lockDeleteConfirmationGUI);
        player.openInventory(lockDeleteConfirmationGUI);
    }

    /**
     * Creates and returns a "no" button for this confirmation.
     *
     * @return {@link ItemStack} object of "no" button.
     */
    private static @NotNull ItemStack getNoButton() {
        final ItemStack noBtn = new ItemStack(NO_BUTTON_MATERIAL, ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta btnMeta = noBtn.getItemMeta();
        btnMeta.displayName(NO_NAME);
        noBtn.setItemMeta(btnMeta);
        return noBtn;
    }

    /**
     * Creates and returns a "yes" button for this confirmation.
     *
     * @return {@link ItemStack} object of "yes" button.
     */
    private static @NotNull ItemStack getYesButton() {
        final ItemStack yesBtn = new ItemStack(YES_BUTTON_MATERIAL, ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta btnMeta = yesBtn.getItemMeta();
        btnMeta.displayName(YES_NAME);
        yesBtn.setItemMeta(btnMeta);
        return yesBtn;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getInventoryName() { return INVENTORY_NAME; }

    public static Material getYesButtonMaterial() { return YES_BUTTON_MATERIAL; }

    public static Material getNoButtonMaterial() { return NO_BUTTON_MATERIAL; }
//</editor-fold>
}
