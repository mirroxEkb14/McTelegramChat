package org.amirov.mctelegramchat.listeners.performers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.utility.*;
import org.amirov.mctelegramchat.utility.buttons.BackWoolButton;
import org.amirov.mctelegramchat.utility.buttons.OpenArmoryShieldButton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Performs the opening of the armory inventory.
 */
public final class ArmoryInventoryPerformer {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int INVENTORY_SIZE = 36;
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Armory", NamedTextColor.DARK_GRAY);

    private static final int STEEL_SWORD_INDEX = 0;
    private static final int SILVER_SWORD_INDEX = 2;
    private static final int MILVA_BOW_INDEX = 4;
    private static final int URSINE_CROSSBOW_INDEX = 7;
    private static final int TELEPORT_BOW_INDEX = 18;
    private static final int LIGHTNING_CROSSBOW_INDEX = 21;

    private static final int MILVA_BOW_ARROW_INDEX = 5;
    private static final int URSINE_CROSSBOW_ARROW_INDEX = 8;
    private static final int TELEPORT_BOW_ARROW_INDEX = 19;
    private static final int LIGHTNING_CROSSBOW_ARROW_INDEX = 22;
    private static final int BACK_WOOL_INDEX = 27;
//</editor-fold>

    /**
     * Opens an inventory with the unique items (armory).
     *
     * @param player Player who clicked on the {@link OpenArmoryShieldButton} button.
     */
    public static void openArmoryInventory(@NotNull Player player) {
        final Inventory armory = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

        setArmoryItems(armory);
        player.openInventory(armory);
    }

    /**
     * Sets the items inside this inventory by indexes.
     *
     * @param armory Inventory itself.
     */
    private static void setArmoryItems(@NotNull Inventory armory) {
        armory.setItem(STEEL_SWORD_INDEX, SteelSwordUtils.getIronSword());

        armory.setItem(SILVER_SWORD_INDEX, SilverSwordUtils.getNetheriteSword());

        armory.setItem(MILVA_BOW_INDEX, MilvaBowUtils.getMilvaBow());
        armory.setItem(MILVA_BOW_ARROW_INDEX, new ItemStack(Material.ARROW));

        armory.setItem(URSINE_CROSSBOW_INDEX, UrsineCrossbowUtils.getUrsineCrossbow());
        armory.setItem(URSINE_CROSSBOW_ARROW_INDEX, new ItemStack(Material.ARROW));

        armory.setItem(TELEPORT_BOW_INDEX, TeleportBowUtils.getTeleportBow());
        armory.setItem(TELEPORT_BOW_ARROW_INDEX, new ItemStack(Material.ARROW));

        armory.setItem(LIGHTNING_CROSSBOW_INDEX, LightningCrossbowUtils.getLightningCrossbow());
        armory.setItem(LIGHTNING_CROSSBOW_ARROW_INDEX, new ItemStack(Material.ARROW));

        armory.setItem(BACK_WOOL_INDEX, BackWoolButton.getBackWool());
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getArmoryInventoryName() { return INVENTORY_NAME; }
//</editor-fold>
}
