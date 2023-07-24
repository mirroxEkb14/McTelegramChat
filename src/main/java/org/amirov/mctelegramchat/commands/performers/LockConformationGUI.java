package org.amirov.mctelegramchat.commands.performers;

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
 * Helper class for the {@code lock} command.
 * <p>
 * Opens a conformation gui where a player decides if he really wants to lock the chest or not.
 */
public final class LockConformationGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 9;
    private static final TextComponent INVENTORY_TITLE = Component.text(
            "Lock Chest?", NamedTextColor.DARK_AQUA);

    private static final Material YES_BUTTON_MATERIAL = Material.TOTEM_OF_UNDYING;
    private static final Material NO_BUTTON_MATERIAL = Material.BARRIER;

    private static final TextComponent YES_NAME = Component.text(
            "Yes", NamedTextColor.GREEN);
    private static final TextComponent NO_NAME = Component.text(
            "No", NamedTextColor.DARK_RED);

    private static final int BUTTON_AMOUNT = 1;

    private static final int YES_ITEM_SLOT_INDEX = 3;
    private static final int NO_ITEM_SLOT_INDEX = 5;
//</editor-fold>

    private static Inventory lockConfirmationGUI;

    public static void openLockConfirmationGUI(@NotNull Player player) {
        lockConfirmationGUI = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_TITLE);
        final ItemStack yes = getYesButton();
        final ItemStack no = getNoButton();
        lockConfirmationGUI.setItem(YES_ITEM_SLOT_INDEX, yes);
        lockConfirmationGUI.setItem(NO_ITEM_SLOT_INDEX, no);
        fillEmptySlots();

        player.openInventory(lockConfirmationGUI);
    }

    /**
     * Fills all the empty slots in this inventory for this conformation gui to look more filled in.
     */
    private static void fillEmptySlots() {
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (lockConfirmationGUI.getItem(i) == null)
                lockConfirmationGUI.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
    }

    /**
     * Works as a getter.
     * <p>
     * Creates and returns an item that represents the 'no' answer.
     *
     * @return Item representing player's confirmation.
     */
    private static @NotNull ItemStack getNoButton() {
        final ItemStack no = new ItemStack(NO_BUTTON_MATERIAL, BUTTON_AMOUNT);
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
        final ItemStack yes = new ItemStack(YES_BUTTON_MATERIAL, BUTTON_AMOUNT);
        final ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.displayName(YES_NAME);
        yes.setItemMeta(yesMeta);
        return yes;
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getInventoryTitle() { return INVENTORY_TITLE; }

    public static Material getYesButtonMaterial() { return YES_BUTTON_MATERIAL; }

    public static Material getNoButtonMaterial() { return NO_BUTTON_MATERIAL; }
//</editor-fold>
}
