package org.amirov.mctelegramchat.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.utility.GodUndyingTotemUtils;
import org.amirov.mctelegramchat.utility.HologramAmethystShardUtils;
import org.amirov.mctelegramchat.utility.LethalTntUtils;
import org.amirov.mctelegramchat.utility.buttons.FeedBreadButton;
import org.amirov.mctelegramchat.utility.buttons.FlyElytraButton;
import org.amirov.mctelegramchat.utility.buttons.OpenArmoryShieldButton;
import org.amirov.mctelegramchat.utility.buttons.SpawnArmorStandButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Opens the main menu inventory with items that trigger certain events.
 */
public final class MainMenuGUI {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int INVENTORY_SIZE = 27;
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Main Menu", NamedTextColor.DARK_GRAY);
//</editor-fold>

    /**
     * Displays the gui where a player by selecting certain items can trigger certain events.
     *
     * @param player Player who triggered the event.
     */
    public static void openMainMenuGUI(@NotNull Player player) {
        final Inventory mainMenuGUI = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

        final ItemStack lethalTnt = LethalTntUtils.getLethalTnt();
        final ItemStack customArmorStand = SpawnArmorStandButton.getSpawnArmorStand();
        final ItemStack hungerBread = FeedBreadButton.getFeedBread();
        final ItemStack flyElytra = FlyElytraButton.getFlyElytra();
        final ItemStack openArmoryShield = OpenArmoryShieldButton.getOpenArmoryShield();
        final ItemStack godUndyingTotem = GodUndyingTotemUtils.getGodUndyingTotem();
        final ItemStack hologramAmethystShard = HologramAmethystShardUtils.getHologramAmethystShard();

        final ItemStack[] menu_items = {lethalTnt, customArmorStand, hungerBread, flyElytra, openArmoryShield,
                godUndyingTotem, hologramAmethystShard};
        mainMenuGUI.setContents(menu_items);

        player.openInventory(mainMenuGUI);
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getMainMenuName() { return INVENTORY_NAME; }
//</editor-fold>
}
