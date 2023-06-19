package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.utility.GodUndyingTotemUtils;
import org.amirov.mctelegramchat.utility.HologramAmethystShardUtils;
import org.amirov.mctelegramchat.utility.LethalTntUtils;
import org.amirov.mctelegramchat.utility.buttons.OpenArmoryShieldButton;
import org.amirov.mctelegramchat.utility.buttons.SpawnArmorStandButton;
import org.amirov.mctelegramchat.utility.buttons.FeedBreadButton;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Opens the main inventory with the items that trigger certain events.
 * <p>
 * Singleton class.
 */
public final class MenuInventoryCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int INVENTORY_SIZE = 27;
    private static final TextComponent INVENTORY_NAME = Component.text(
            "Main Menu", NamedTextColor.DARK_GRAY);
//</editor-fold>

    private static MenuInventoryCommand instance;

//<editor-fold default-state="collapsed" desc="Constructor">
    public static MenuInventoryCommand getInstance() {
        if (instance == null)
            return new MenuInventoryCommand();

        return instance;
    }
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        onCommand(sender);
        return true;
    }

    /**
     * .Opens the inventory representing the main menu.
     * <p>
     * This logic was taken out of the main {@code onCommand()} method due to be able to call the method outside this
     * class without creating multiple instances and passing unnecessary arguments.
     *
     * @param sender Player who triggered the command.
     */
    public void onCommand(@NotNull CommandSender sender) {
        if (sender instanceof Player player) {
            final Inventory gui = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

            final ItemStack lethalTnt = LethalTntUtils.getLethalTnt();
            final ItemStack customArmorStand = SpawnArmorStandButton.getSpawnArmorStand();
            final ItemStack hungerBread = FeedBreadButton.getFeedBread();
            final ItemStack openArmoryShield = OpenArmoryShieldButton.getOpenArmoryShield();
            final ItemStack godUndyingTotem = GodUndyingTotemUtils.getGodUndyingTotem();
            final ItemStack hologramAmethystShard = HologramAmethystShardUtils.getHologramAmethystShard();

            final ItemStack[] menu_items = {lethalTnt, customArmorStand, hungerBread, openArmoryShield, godUndyingTotem,
                    hologramAmethystShard};
            gui.setContents(menu_items);

            player.openInventory(gui);
        } else if (sender instanceof ConsoleCommandSender) {
            Loggers.printWarningLog(LoggingMessage.MENU_INVENTORY_CMD_WARNING.getMessage());
        } else if (sender instanceof BlockCommandSender) {
            Loggers.printWarningLog(LoggingMessage.MENU_INVENTORY_COMMAND_BLOCK_WARNING.getMessage());
        }
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getMenuInventoryName() { return INVENTORY_NAME; }
//</editor-fold>
}
